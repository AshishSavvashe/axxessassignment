package demo.com.axxessasignment.repository

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.google.gson.Gson
import demo.com.axxessasignment.constant.ApiConstants
import demo.com.axxessasignment.database.AppDatabase
import demo.com.axxessasignment.database.dao.ImageCommentsDao
import demo.com.axxessasignment.network.MainActivityPresenter
import demo.com.axxessasignment.network.MainContract
import demo.com.axxessasignment.responsemodel.Image
import demo.com.axxessasignment.responsemodel.ImageComments
import demo.com.axxessasignment.responsemodel.ImageShapes

class ImageDataRepository constructor(val application: Application) : MainContract.View {

    override fun initView() {}

     var listLiveData: MutableLiveData<List<Image>>
     var presenter: MainActivityPresenter? = null
     var imagesShapeDao: ImageCommentsDao


    init {
        val database = AppDatabase.getDatabase(application)
        imagesShapeDao = database!!.imageShapeDao()
        listLiveData = MutableLiveData<List<Image>>()
        presenter = MainActivityPresenter(this)
    }

    //Save  Comment on local DataBase
    fun insert(data: ImageComments) {
        InsertAsyncTask(imagesShapeDao).execute(data)
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: ImageCommentsDao) :
        AsyncTask<ImageComments, Void, Void>() {

        override fun doInBackground(vararg params: ImageComments): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    //getComment from local

    fun getCommentListByImageId(projectId: String): LiveData<List<ImageComments>> {
        return imagesShapeDao.getCommentListByImageId(projectId)
    }

    fun getDataFromNetwork(
        searchText: String,
        context: Context?
        ) {
        presenter!!.onClick(
            ApiConstants.GET_IMAGES,
            arrayOf("Client-ID 137cda6b5008a7c", searchText),
            context!!,
            true
        )
    }

    override fun setViewData(data: String, view: ApiConstants) {
        when (view) {
            ApiConstants.GET_IMAGES -> {
                val response = Gson().fromJson(data, ImageShapes::class.java)
                if(response.success){
                    val list = mutableListOf<Image>()
                    response.data.forEach {parent ->
                        if(parent.images!=null){
                            list.addAll(parent.images.map { child -> Image(child.id,parent.title,child.link) })
                        }else{
                       //     Toast.makeText(application, application.getString(R.string.datanotfound), Toast.LENGTH_SHORT).show()
                        }
                    }
                    listLiveData.postValue(list)
                }
            }
        }
    }
}
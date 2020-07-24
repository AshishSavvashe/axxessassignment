package demo.com.axxessasignment.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import demo.com.axxessasignment.repository.ImageDataRepository
import demo.com.axxessasignment.responsemodel.Image
import demo.com.axxessasignment.responsemodel.ImageComments

class ImagesDataViewModel constructor (application: Application) : AndroidViewModel(application) {

    val mRepository = ImageDataRepository(application)
    var allImageShapes: LiveData<List<Image>>?=null

    init {
        allImageShapes = mRepository.listLiveData!!
    }

     fun getDataFromNetwork(
        searchText: String,
        context: Context?
    ) {
        mRepository.getDataFromNetwork(searchText,context)
    }

    fun insert(imageComments: ImageComments) {
        mRepository.insert(imageComments)
    }

    fun getCommentListByImageId(imageId: String): LiveData<List<ImageComments>> {
        return mRepository.getCommentListByImageId(imageId)
    }
}
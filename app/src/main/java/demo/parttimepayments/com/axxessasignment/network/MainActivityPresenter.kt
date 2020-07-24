package demo.parttimepayments.com.axxessasignment.network

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.JsonObject
import demo.parttimepayments.com.axxessasignment.R
import demo.parttimepayments.com.axxessasignment.constant.ApiConstants
import demo.parttimepayments.com.axxessasignment.view.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Suppress("CAST_NEVER_SUCCEEDS")
class MainActivityPresenter(private val mView: MainContract.View) : MainContract.Presenter,BaseFragment() {
    private val model: MainContract.Model? = null
    private var progressDialog: ProgressDialog? = null

    init {
        initPresenter()
    }

    private fun initPresenter() {
        mView.initView()
    }

    override fun onClick(
        caseConstants: ApiConstants,
        para: Array<String>,
        context: Context,
        showProgressBar: Boolean?
    ) {
        if (showProgressBar!!) {
            initAndShowProgressBar(context)
        }

        val retrofit = ApiClient.client
        val requestInterface = retrofit.create(ApiInterface::class.java)
        val accessTokenCall: Call<JsonObject>
        when (caseConstants) {

            ApiConstants.GET_IMAGES -> {
                accessTokenCall = requestInterface.getImages(para[0],para[1])
                callApiWithAccessToken(accessTokenCall, context, ApiConstants.GET_IMAGES)
            }
        }
    }

    private fun initAndShowProgressBar(context: Context) {
        try {
            if (progressDialog != null) {
                progressDialog!!.dismiss()
            }
            progressDialog = null
            progressDialog = ProgressDialog(context)
            progressDialog!!.setMessage(context.getString(R.string.str_please_wait))
            progressDialog!!.isIndeterminate = true
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        } catch (e: Exception) {
            Log.e(TAG, "initAndShowProgressBar: $e")
        }

    }

    private fun callApiWithAccessToken(accessTokenCall: Call<JsonObject>, context: Context, view: ApiConstants) {
        try {
            if (isNetworkConnected(context)) {
                accessTokenCall.enqueue(object : Callback<JsonObject> {
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        if (progressDialog != null && progressDialog!!.isShowing)
                            try {
                                if (progressDialog!!.isShowing)
                                    progressDialog!!.dismiss()
                            } catch (e: Exception) {
                                Log.e(TAG, "onResponse: $e")
                            }

                        if (response.body()!!.toString().isEmpty()) {
                            Toast.makeText(context, getString(R.string.datanotfound), Toast.LENGTH_SHORT).show()
                        }else{
                            mView.setViewData((response.body()!!.toString()), view)
                        }
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        try {
                            if (progressDialog != null && progressDialog!!.isShowing)
                                progressDialog!!.dismiss()
                            Toast.makeText(context, getString(R.string.api_error), Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Log.e(TAG, "onFailure: $e")
                        }
                    }
                })
            } else {
                if (progressDialog!!.isShowing)
                    try {
                        progressDialog!!.dismiss()
                    } catch (e: Exception) {
                        Log.e(TAG, "onResponse: $e")
                    }

                Toast.makeText(context, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            try {
                if (progressDialog!!.isShowing)
                    progressDialog!!.dismiss()
            } catch (e1: Exception) {
                Log.e(TAG, "callApiWithAccessToken: $e1")
            }
        }
    }
}
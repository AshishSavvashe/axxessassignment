package demo.com.axxessasignment.network

import android.content.Context
import demo.com.axxessasignment.constant.ApiConstants

interface MainContract {

    interface View {
        fun initView()
        fun setViewData(data: String, view: ApiConstants)
    }

    // set data to pojo class
    interface Model {
        fun getData(jsonObject: String): String
    }

    interface Presenter {
        fun onClick(caseConstants: ApiConstants, parameters: Array<String>, context: Context, showProgressBar: Boolean?)
    }
}
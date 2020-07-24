package demo.parttimepayments.com.axxessasignment.network

import android.content.Context
import android.view.View
import demo.parttimepayments.com.axxessasignment.constant.ApiConstants

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
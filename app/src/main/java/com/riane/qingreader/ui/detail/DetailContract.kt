package com.riane.qingreader.ui.detail

import com.riane.qingreader.data.network.reponse.ResultBean
import com.riane.qingreader.ui.base.BaseContract
import java.util.*

/**
 * Created by xiaobozheng on 10/11/2017.
 */
interface DetailContract{
    interface View: BaseContract.BaseView{
        fun showLike()
        fun showUnLike()
    }

    interface Presenter: BaseContract.BasePresenter<Any>{
        fun queryIsLIke(id: String)
        fun add(result: ResultBean)
        fun cancel(id: String)
    }
}
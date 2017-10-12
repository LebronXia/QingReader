package com.riane.qingreader.ui.collection.gankcollection

import com.riane.qingreader.data.network.reponse.ResultBean
import com.riane.qingreader.ui.base.BaseContract

/**
 * Created by xiaobozheng on 10/12/2017.
 */
interface GankColContract{
    interface View: BaseContract.BaseView{
        fun showGankCollections(gankCollections: MutableList<ResultBean>)
    }

    interface Presenter: BaseContract.BasePresenter<Any>{
        fun getGankCollections(offset: Int)
    }
}
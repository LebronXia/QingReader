package com.riane.qingreader.ui.detail

import com.riane.qingreader.data.ReaderRepository
import com.riane.qingreader.data.network.reponse.ResultBean
import com.riane.qingreader.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by xiaobozheng on 10/11/2017.
 */
class DetailPresenter @Inject constructor( val readerRepository: ReaderRepository
    , val detailView: DetailContract.View): BasePresenter(), DetailContract.Presenter{

    override fun detachView() {
    }

    override fun queryIsLIke(id: String) {
        if (readerRepository.getIsCollection(id)){
            detailView.showLike()
        } else {
            detailView.showUnLike()
        }
    }

    override fun add(result: ResultBean) {
        readerRepository.addConnection(result)
        detailView.showLike()
    }

    override fun cancel(id: String) {
        readerRepository.cancelCollection(id)
        detailView.showUnLike()
    }
}
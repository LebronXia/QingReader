package com.riane.qingreader.ui.collection.gankcollection

import com.riane.qingreader.data.ReaderRepository
import com.riane.qingreader.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by xiaobozheng on 10/12/2017.
 */
class GankColPresenter @Inject constructor(val readerRepository: ReaderRepository
    , val gankColView: GankColContract.View): BasePresenter(), GankColContract.Presenter{

    override fun getGankCollections(offset: Int) {
        addSubscrebe(readerRepository.queryForList(offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result ->
                    gankColView.showGankCollections(result)
                }, {
                    error ->
                    gankColView.showError()
                })
        )
    }
}
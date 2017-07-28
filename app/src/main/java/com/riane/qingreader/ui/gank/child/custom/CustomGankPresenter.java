package com.riane.qingreader.ui.gank.child.custom;

import com.riane.qingreader.data.ReaderRepository;
import com.riane.qingreader.data.network.reponse.GankIoDataBean;
import com.riane.qingreader.ui.base.BaseContract;
import com.riane.qingreader.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Riane on 2017/7/14.
 */

public class CustomGankPresenter extends BasePresenter implements CustomGankContract.Presenter{

    private ReaderRepository mReaderRepository;
    private CustomGankContract.View mView;

    @Inject
    CustomGankPresenter(ReaderRepository readerRepository, CustomGankContract.View customGankView){
        mReaderRepository = readerRepository;
        mView = customGankView;

    }
    @Override
    public void getGankCustomData(String id, int page, int pre_page) {
        mReaderRepository.getGankIoData(id, page, pre_page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GankIoDataBean>() {
                    @Override
                    public void accept(GankIoDataBean gankIoDataBean) throws Exception {
                        mView.showGankCustomData(gankIoDataBean);
                    }
                });
    }
}

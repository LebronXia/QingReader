package com.riane.qingreader.ui.movie;

import com.riane.qingreader.data.ReaderRepository;
import com.riane.qingreader.data.network.reponse.HotMovieBean;
import com.riane.qingreader.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaobozheng on 8/16/2017.
 */

public class MoviePresenter extends BasePresenter implements MovieContract.Presenter{

    private ReaderRepository mReaderRepository;
    private MovieContract.View mView;

    @Inject
    MoviePresenter(ReaderRepository readerRepository , MovieContract.View moviewView){
        this.mReaderRepository = readerRepository;
        this.mView = moviewView;
    }

    @Override
    public void getLiveMovie() {
        addSubscrebe(mReaderRepository.getLiveFilm()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<HotMovieBean>() {
            @Override
            public void accept(HotMovieBean hotMovieBean) throws Exception {
                mView.showLiveMovieData(hotMovieBean.getSubjects());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.showError();
            }
        }));
    }
}

package com.riane.qingreader.ui.movie.MovieDetail;

import com.riane.qingreader.data.ReaderRepository;
import com.riane.qingreader.data.network.reponse.MovieDetailBean;
import com.riane.qingreader.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaobozheng on 8/18/2017.
 */

public class MovieDetailPresenter extends BasePresenter implements MovieDetailContract.Presenter{
    private ReaderRepository mReaderRepository;
    private MovieDetailContract.View mView;

    @Inject
    MovieDetailPresenter(ReaderRepository readerRepository, MovieDetailContract.View movieDetailView){
        this.mReaderRepository = readerRepository;
        mView = movieDetailView;
    }

    @Override
    public void getMovieDetail(String id) {
        addSubscrebe(mReaderRepository.getMovieDetail(id)
                .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieDetailBean>() {
                    @Override
                    public void accept(MovieDetailBean movieDetailBean) throws Exception {
                        mView.showMovieDetailData(movieDetailBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError();
                    }
                })
        );
    }

}

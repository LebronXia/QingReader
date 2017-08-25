package com.riane.qingreader.data;

import com.riane.qingreader.data.local.DbHelper;
import com.riane.qingreader.data.network.ApiHelper;
import com.riane.qingreader.data.network.reponse.GankIoDataBean;
import com.riane.qingreader.data.network.reponse.GankIoDayBean;
import com.riane.qingreader.data.network.reponse.HotMovieBean;
import com.riane.qingreader.data.network.reponse.MovieDetailBean;
import com.riane.qingreader.data.network.reponse.ThemeResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by Riane on 2017/7/12.
 */

@Singleton
public class ReaderRepository implements ReaderDataSource{

    private ApiHelper mReaderRemoteDataSource;
    private DbHelper mReaderLocalDataSource;

    @Inject
    public ReaderRepository(ApiHelper readerRemoteDataSource, DbHelper readerLocalDataSource){
        this.mReaderRemoteDataSource = readerRemoteDataSource;
        this.mReaderLocalDataSource = readerLocalDataSource;
    }

    @Override
    public Observable<GankIoDayBean> getGankIoDay(int year, int month, int day) {
        return mReaderRemoteDataSource.getGankIoDay(year, month, day);
    }

    @Override
    public Observable<GankIoDataBean> getGankIoData(String id, int page, int pre_page) {
        return mReaderRemoteDataSource.getGankIoData(id, page, pre_page);
    }

    @Override
    public void addSearchHistory(String content) {
        mReaderLocalDataSource.addSearchHistory(content);
    }

    @Override
    public Observable<List<String>> querySearchHistory() {
        return mReaderLocalDataSource.querySearchHistory();
    }

    @Override
    public void deleteSearchHistory() {
        mReaderLocalDataSource.deleteSearchHistory();
    }

    @Override
    public Observable<ThemeResponse> getSearchContent(String content, String type, int page) {
        return mReaderRemoteDataSource.getSearchContent(content, type, page);
    }

    @Override
    public Observable<HotMovieBean> getLiveFilm() {
        return mReaderRemoteDataSource.getLiveFilm();
    }

    @Override
    public Observable<HotMovieBean> getTop250(int start, int count) {
        return mReaderRemoteDataSource.getTop250(start, count);
    }

    @Override
    public Observable<MovieDetailBean> getMovieDetail(String id) {
        return mReaderRemoteDataSource.getMovieDetail(id);
    }
}

package com.riane.qingreader.data;

import android.widget.RemoteViews;

import com.riane.qingreader.data.local.DbHelper;
import com.riane.qingreader.data.network.ApiHelper;
import com.riane.qingreader.data.network.reponse.GankIoDataBean;
import com.riane.qingreader.data.network.reponse.GankIoDayBean;

import org.xml.sax.helpers.XMLReaderAdapter;

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
        return null;
    }

    @Override
    public void deleteSearchHistory() {

    }
}

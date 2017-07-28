package com.riane.qingreader.data;

import android.widget.RemoteViews;

import com.riane.qingreader.data.network.reponse.GankIoDataBean;
import com.riane.qingreader.data.network.reponse.GankIoDayBean;

import org.xml.sax.helpers.XMLReaderAdapter;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by Riane on 2017/7/12.
 */

@Singleton
public class ReaderRepository implements ReaderDataSource{

    private ReaderDataSource mReaderRemoteDataSource;

    @Inject
    public ReaderRepository(ReaderDataSource readerRemoteDataSource){
        this.mReaderRemoteDataSource = readerRemoteDataSource;
    }

    @Override
    public Observable<GankIoDayBean> getGankIoDay(int year, int month, int day) {
        return mReaderRemoteDataSource.getGankIoDay(year, month, day);
    }

    @Override
    public Observable<GankIoDataBean> getGankIoData(String id, int page, int pre_page) {
        return mReaderRemoteDataSource.getGankIoData(id, page, pre_page);
    }
}

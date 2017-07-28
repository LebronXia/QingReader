package com.riane.qingreader.data;

import com.riane.qingreader.data.network.reponse.GankIoDataBean;
import com.riane.qingreader.data.network.reponse.GankIoDayBean;

import io.reactivex.Observable;

/**
 * Created by Riane on 2017/7/12.
 */

public interface ReaderDataSource {

    //获取每日数据
    Observable<GankIoDayBean> getGankIoDay(int year, int month, int day);

    //获取某种类型数据
    Observable<GankIoDataBean> getGankIoData( String id, int page, int pre_page);



}

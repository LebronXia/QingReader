package com.riane.qingreader.data.network;

import com.riane.qingreader.data.network.reponse.GankIoDataBean;
import com.riane.qingreader.data.network.reponse.GankIoDayBean;
import com.riane.qingreader.data.network.reponse.HotMovieBean;
import com.riane.qingreader.data.network.reponse.MovieDetailBean;
import com.riane.qingreader.data.network.reponse.ThemeResponse;

import io.reactivex.Observable;

/**
 * Created by Riane on 2017/7/31.
 */

public interface ApiHelper {

    //获取每日数据
    Observable<GankIoDayBean> getGankIoDay(int year, int month, int day);

    //获取某种类型数据
    Observable<GankIoDataBean> getGankIoData(String id, int page, int pre_page);

    //获取搜索后的结果
    Observable<ThemeResponse> getSearchContent(String content, String type, int page);

    //获取正在热映的电影
    Observable<HotMovieBean> getLiveFilm();

    //获取top250的电影
    Observable<HotMovieBean>  getTop250(int start, int count);

    //获取电影详情
    Observable<MovieDetailBean> getMovieDetail(String id);
}

package com.riane.qingreader.data.network;

import com.riane.qingreader.data.network.reponse.HotMovieBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Riane on 2017/7/10.
 */

public interface DoubanApiService {
    String API_DOUBAN = "https://api.douban.com/";

    /**
     *获取正在热映的电影
     * @return
     */
    @GET("v2/movie/in_theaters")
    Observable<HotMovieBean> getLiveFilm();

    /**
     * 获取top250
     * @return
     */
    @GET("v2/movie/top250")
    Observable<HotMovieBean> getTop250();

    //todo 获取电影详情
   // Observable<>
}

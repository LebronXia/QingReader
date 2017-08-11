package com.riane.qingreader.data.network;

import com.riane.qingreader.Contants;
import com.riane.qingreader.data.network.reponse.GankIoDataBean;
import com.riane.qingreader.data.network.reponse.GankIoDayBean;
import com.riane.qingreader.data.network.reponse.ThemeResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Riane on 2017/7/10.
 */

public interface GankApiService {

    String API_GANKIO = "https://gank.io/api/";

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     * eg: http://gank.io/api/data/Android/10/1
     */
    @GET("data/{type}/{pre_page}/{page}")
    Observable<GankIoDataBean> getGankIoData(@Path("type") String id, @Path("page") int page, @Path("pre_page") int pre_page);

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     * eg:http://gank.io/api/day/2015/08/06
     */
    @GET("day/{year}/{month}/{day}")
    Observable<GankIoDayBean> getGankIoDay(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    //获取搜索后的结果
    @GET("search/query/{content}/category/{type}/count/" + Contants.PAGECOUNT + "/page/{page}")
    Observable<ThemeResponse> getSearchContent(@Path("content")String Content, @Path("type")String type, @Path("page") int page);
}

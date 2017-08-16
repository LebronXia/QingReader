package com.riane.qingreader.data.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.riane.qingreader.BuildConfig;
import com.riane.qingreader.QingReaderApplication;
import com.riane.qingreader.data.network.reponse.GankIoDataBean;
import com.riane.qingreader.data.network.reponse.GankIoDayBean;
import com.riane.qingreader.data.network.reponse.HotMovieBean;
import com.riane.qingreader.data.network.reponse.ThemeResponse;
import com.riane.qingreader.util.NetUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Riane on 2017/7/12.
 */

public class ReaderRemoteDataRepository implements ApiHelper{

    public static Retrofit mGankRetrofit;
    public static Retrofit mDoubanRetrofit;
    private static OkHttpClient sOkHttpClient;
    private static DoubanApiService mDoubanApiService;
    private static GankApiService mGankApiService;

    public static void init(){
        if (mGankRetrofit == null){
            mGankRetrofit = new Retrofit.Builder()
                    .baseUrl(GankApiService.API_GANKIO)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
           // mGankApiService = mGankRetrofit.create(GankApiService.class);
        }

        mGankApiService = mGankRetrofit.create(GankApiService.class);

        if (mDoubanRetrofit == null){
            mDoubanRetrofit = new Retrofit.Builder()
                    .baseUrl(DoubanApiService.API_DOUBAN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }

        mDoubanApiService = mDoubanRetrofit.create(DoubanApiService.class);
    }

    public static OkHttpClient getOkHttpClient(){
        if (sOkHttpClient == null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG){
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
            }
            //cache url
            File httpCacheDirectory = new File(QingReaderApplication.getAppContext().getExternalCacheDir(), "responses");
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(httpCacheDirectory, cacheSize);
            builder.cache(cache);
            builder.addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
            sOkHttpClient = builder.build();
        }
        return sOkHttpClient;
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     */
    static Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(365, TimeUnit.DAYS);
            CacheControl cacheControl = cacheBuilder.build();

            Request request = chain.request();
            if (!NetUtil.isNetworkAvailable(QingReaderApplication.getAppContext())) {
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();

            }
            //超时
            Response originalResponse = chain.proceed(request);
            if (NetUtil.isNetworkAvailable(QingReaderApplication.getAppContext())) {
                int maxAge = 0; // read from cache
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-xcached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    @Override
    public Observable<GankIoDayBean> getGankIoDay(int year, int month, int day) {
        return mGankApiService.getGankIoDay(year, month, day);
    }

    @Override
    public Observable<GankIoDataBean> getGankIoData(String id, int page, int pre_page) {
        return mGankApiService.getGankIoData(id, page, pre_page);
    }

    @Override
    public Observable<ThemeResponse> getSearchContent(String content, String type, int page) {
        return mGankApiService.getSearchContent(content, type, page);
    }

    public ReaderRemoteDataRepository() {
        super();
    }

    @Override
    public Observable<HotMovieBean> getLiveFilm() {
        return mDoubanApiService.getLiveFilm();
    }

    @Override
    public Observable<HotMovieBean> getTop250() {
        return mDoubanApiService.getTop250();
    }
}

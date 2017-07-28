package com.riane.qingreader;

import android.app.Application;
import android.content.Context;

import com.riane.qingreader.data.DaggerReaderRepositoryComponent;
import com.riane.qingreader.data.ReaderRepositoryComponent;
import com.riane.qingreader.data.network.ReaderRemoteDataRepository;
import com.riane.qingreader.di.module.ApplicationModule;


/**
 * Created by Riane on 2017/7/10.
 */

public class QingReaderApplication extends Application{

    private static Context mContext;
    private ReaderRepositoryComponent mReaderRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mReaderRepositoryComponent = DaggerReaderRepositoryComponent.builder()
                .applicationModule(new ApplicationModule((getApplicationContext())))
                .build();
        ReaderRemoteDataRepository.init();
    }

    public ReaderRepositoryComponent getReaderRepositoryComponent(){
        return mReaderRepositoryComponent;
    }

    public static Context getAppContext(){
        return mContext;
    }
}

package com.riane.qingreader.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Riane on 2017/7/12.
 */

@Module
public class ApplicationModule {

    private Context mContext;

    public ApplicationModule(Context context){
        mContext = context;
    }

    @Provides
    Context provideContext(){
        return mContext;
    }
}

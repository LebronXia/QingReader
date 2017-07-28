package com.riane.qingreader.ui.gank.child.custom;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Riane on 2017/7/14.
 */

@Module
public class CustomGankPresenterModule {

    private CustomGankContract.View mView;

    public CustomGankPresenterModule(CustomGankContract.View view){
        mView = view;
    }

    @Provides
    CustomGankContract.View providerCustomGankContractView(){
        return mView;
    }
}

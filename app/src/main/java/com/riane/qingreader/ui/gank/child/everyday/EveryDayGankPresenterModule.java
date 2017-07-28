package com.riane.qingreader.ui.gank.child.everyday;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Riane on 2017/7/13.
 */
@Module
public class EveryDayGankPresenterModule {

    private EveryDayGankContract.View mView;

    public EveryDayGankPresenterModule(EveryDayGankContract.View view){
        mView = view;
    }

    @Provides
    EveryDayGankContract.View provideEveryDayContractView(){
        return mView;
    }
}

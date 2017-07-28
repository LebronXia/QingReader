package com.riane.qingreader.ui.search;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Riane on 2017/7/25.
 */

@Module
public class SearchPresenterModule {

    private SearchContract.View mView;

    @Provides
    SearchContract.View provideSearchContractView(){
        return mView;
    }
}

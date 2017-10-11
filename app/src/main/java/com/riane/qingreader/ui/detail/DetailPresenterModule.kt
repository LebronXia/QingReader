package com.riane.qingreader.ui.detail

import dagger.Module
import dagger.Provides

/**
 * Created by xiaobozheng on 10/11/2017.
 */
@Module
class DetailPresenterModule constructor(view: DetailContract.View){
    var mView: DetailContract.View = view

    @Provides
    fun providerDetailContractView(): DetailContract.View{
        return mView
    }
}
package com.riane.qingreader.ui.collection.gankcollection

import dagger.Module
import dagger.Provides

/**
 * Created by xiaobozheng on 10/12/2017.
 */
@Module
class GankColPresenterModule constructor(val view: GankColContract.View){
    var mView: GankColContract.View = view

    @Provides
    fun providerGankColContractView(): GankColContract.View{
        return  mView
    }
}
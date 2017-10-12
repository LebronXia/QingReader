package com.riane.qingreader.ui.collection.gankcollection

import com.riane.qingreader.data.ReaderRepositoryComponent
import com.riane.qingreader.di.scope.FragmentScoped
import dagger.Component

/**
 * Created by xiaobozheng on 10/12/2017.
 */
@FragmentScoped
@Component(dependencies = arrayOf(ReaderRepositoryComponent:: class), modules = arrayOf(GankColPresenterModule:: class))
interface GankColComponent{
    fun inject(gankColFragment: GankColFragment)
}

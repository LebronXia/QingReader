package com.riane.qingreader.ui.detail

import com.riane.qingreader.data.ReaderRepositoryComponent
import com.riane.qingreader.di.scope.FragmentScoped
import dagger.Component

/**
 * Created by xiaobozheng on 10/11/2017.
 */
@FragmentScoped
@Component(dependencies = arrayOf(ReaderRepositoryComponent:: class), modules = arrayOf(DetailPresenterModule:: class))
interface DetailComponent{
        fun inject(detailActivity: DetailActivity)
}
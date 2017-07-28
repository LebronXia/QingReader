package com.riane.qingreader.data;

import com.riane.qingreader.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Riane on 2017/7/13.
 */

@Singleton
@Component(modules = {RepositoryModule.class, ApplicationModule.class})
public interface ReaderRepositoryComponent {
    //向下传递方法
    ReaderRepository getReaderRepository();
}

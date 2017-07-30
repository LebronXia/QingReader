package com.riane.qingreader.data;

import com.riane.qingreader.data.local.DbHelper;
import com.riane.qingreader.data.local.ReaderLocalDataRepository;
import com.riane.qingreader.data.network.ApiHelper;
import com.riane.qingreader.data.network.ReaderRemoteDataRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Riane on 2017/7/12.
 */
@Module
public class RepositoryModule {

//    @Singleton
//    @Provides
//    ReaderDataSource providerReaderDataSource(ReaderRepository readerRepository){
//        return readerRepository;
//    }

    @Singleton
    @Provides
    ApiHelper providerReaderRemoteDataSource(){
        return new ReaderRemoteDataRepository();
    }

    @Singleton
    @Provides
    DbHelper providerReaderLocalDataSource(ReaderLocalDataRepository readerLocalDataRepository){
        return readerLocalDataRepository;
    }


}

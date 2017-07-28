package com.riane.qingreader.data;

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
    ReaderDataSource providerReaderRemoteDataSource(){
        return new ReaderRemoteDataRepository();
    }


}

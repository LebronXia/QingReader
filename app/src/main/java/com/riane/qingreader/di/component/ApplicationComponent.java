package com.riane.qingreader.di.component;

import android.content.Context;

import com.riane.qingreader.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Riane on 2017/7/12.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context getContext();
}

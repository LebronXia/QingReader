package com.riane.qingreader.ui.gank.child.custom;

import com.riane.qingreader.data.ReaderRepositoryComponent;
import com.riane.qingreader.di.scope.FragmentScoped;
import com.riane.qingreader.ui.gank.child.AndroidFragment;
import com.riane.qingreader.ui.gank.child.CustomFragment;
import com.riane.qingreader.ui.gank.child.WelfareFragment;
import com.riane.qingreader.ui.gank.child.everyday.EveryDayGankPresenterModule;

import dagger.Component;

/**
 * Created by Riane on 2017/7/14.
 */
@FragmentScoped
@Component(dependencies = ReaderRepositoryComponent.class, modules = CustomGankPresenterModule.class)
public interface CustomGankComponent {

    void inject(CustomFragment customFragment);

    void inject(AndroidFragment androidFragment);

    void inject(WelfareFragment welfareFragment);
}

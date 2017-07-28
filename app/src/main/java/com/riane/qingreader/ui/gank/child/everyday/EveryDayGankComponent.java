package com.riane.qingreader.ui.gank.child.everyday;

import com.riane.qingreader.data.ReaderRepositoryComponent;
import com.riane.qingreader.di.scope.FragmentScoped;
import com.riane.qingreader.ui.gank.child.EveryDayFragment;

import dagger.Component;

/**
 * Created by Riane on 2017/7/13.
 */

@FragmentScoped
@Component(dependencies = ReaderRepositoryComponent.class, modules = EveryDayGankPresenterModule.class)
public interface EveryDayGankComponent {
    void inject(EveryDayFragment everyDayFragment);
}

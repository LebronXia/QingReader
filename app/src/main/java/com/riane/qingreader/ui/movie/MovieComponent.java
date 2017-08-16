package com.riane.qingreader.ui.movie;

import com.riane.qingreader.data.ReaderRepositoryComponent;
import com.riane.qingreader.di.scope.FragmentScoped;

import dagger.Component;

/**
 * Created by xiaobozheng on 8/16/2017.
 */

@FragmentScoped
@Component(dependencies = ReaderRepositoryComponent.class, modules = MoviePresenterModule.class)
public interface MovieComponent {

    void inject(MovieFragment movieFragment);
}

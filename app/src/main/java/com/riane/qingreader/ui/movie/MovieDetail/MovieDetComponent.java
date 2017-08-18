package com.riane.qingreader.ui.movie.MovieDetail;

import com.riane.qingreader.data.ReaderRepositoryComponent;
import com.riane.qingreader.di.scope.FragmentScoped;

import dagger.Component;

/**
 * Created by xiaobozheng on 8/18/2017.
 */
@FragmentScoped
@Component(dependencies = ReaderRepositoryComponent.class, modules = MovieDetModule.class)
public interface MovieDetComponent {

    void inject(MovieDetailActivity movieDetailActivity);
}

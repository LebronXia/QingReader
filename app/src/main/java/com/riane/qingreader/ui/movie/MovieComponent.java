package com.riane.qingreader.ui.movie;

import com.riane.qingreader.data.ReaderRepositoryComponent;
import com.riane.qingreader.di.scope.FragmentScoped;
import com.riane.qingreader.ui.movie.HotMovie.HotMovieFragment;
import com.riane.qingreader.ui.movie.Top250Movie.Top250Fragment;

import dagger.Component;

/**
 * Created by xiaobozheng on 8/16/2017.
 */

@FragmentScoped
@Component(dependencies = ReaderRepositoryComponent.class, modules = MoviePresenterModule.class)
public interface MovieComponent {

    void inject(MovieFragment movieFragment);

    void inject(HotMovieFragment hotMovieFragment);

    void inject(Top250Fragment top250Fragment);
}

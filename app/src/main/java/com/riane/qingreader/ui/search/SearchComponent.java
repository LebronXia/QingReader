package com.riane.qingreader.ui.search;

import com.riane.qingreader.data.ReaderRepositoryComponent;
import com.riane.qingreader.di.scope.FragmentScoped;

import dagger.Component;

/**
 * Created by Riane on 2017/7/25.
 */

@FragmentScoped
@Component(dependencies = ReaderRepositoryComponent.class, modules = SearchPresenterModule.class)
public interface SearchComponent {

    void inject(SearchActivity searchActivity);

    void inject(SearchHotWordFragment searchHotWordFragment);


}

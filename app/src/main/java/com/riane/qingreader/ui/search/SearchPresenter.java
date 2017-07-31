package com.riane.qingreader.ui.search;

import com.riane.qingreader.data.ReaderRepository;
import com.riane.qingreader.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Riane on 2017/7/30.
 */

public class SearchPresenter extends BasePresenter implements SearchContract.Presenter{

    private ReaderRepository mReaderRepository;
    private SearchContract.View mView;

    @Inject
    SearchPresenter(ReaderRepository readerRepository, SearchContract.View searchView){
        mReaderRepository = readerRepository;
        mView = searchView;

    }

    @Override
    public void insertHistory(String content) {
        mReaderRepository.addSearchHistory(content);
    }

    @Override
    public void loadSearchHistory() {

    }

    @Override
    public void deleteAll() {

    }
}

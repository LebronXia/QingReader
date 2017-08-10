package com.riane.qingreader.ui.search;

import com.riane.qingreader.data.ReaderRepository;
import com.riane.qingreader.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
        addSubscrebe(mReaderRepository.querySearchHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        mView.showHistory(strings);
                    }
                }));
    }

    @Override
    public void deleteAll() {
        mReaderRepository.deleteSearchHistory();
    }

    @Override
    public void loadData(String content, String type, String page) {

    }
}

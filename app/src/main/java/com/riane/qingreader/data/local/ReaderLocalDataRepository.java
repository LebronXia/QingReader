package com.riane.qingreader.data.local;

import com.riane.qingreader.data.local.gen.DaoMaster;
import com.riane.qingreader.data.local.gen.DaoSession;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Riane on 2017/7/30.
 */

public class ReaderLocalDataRepository implements DbHelper{

    private final DaoSession mDaoSession;

    @Inject
    public ReaderLocalDataRepository(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }

    @Override
    public void addSearchHistory(String content) {
        mDaoSession.getSearchHistoryDao().insert(new SearchHistory(null, content));
    }

    @Override
    public Observable<List<String>> querySearchHistory() {
        return null;
    }

    @Override
    public void deleteSearchHistory() {

    }
}

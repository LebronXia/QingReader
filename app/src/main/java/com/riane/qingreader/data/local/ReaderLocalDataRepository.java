package com.riane.qingreader.data.local;

import android.database.Cursor;

import com.riane.qingreader.data.local.gen.DaoMaster;
import com.riane.qingreader.data.local.gen.DaoSession;
import com.riane.qingreader.data.local.gen.SearchHistoryDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Riane on 2017/7/30.
 */

public class ReaderLocalDataRepository implements DbHelper{

    //查询数据库语句
    private final static String SQL_DISTINCT_ENAME = "SELECT DISTINCT " +
            SearchHistoryDao.Properties.SearchContent.columnName + " FROM " + SearchHistoryDao.TABLENAME;
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

        //在子线程中异步加载数据
        return Observable.fromCallable(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                ArrayList<String> result = new ArrayList<String>();
                Cursor c = mDaoSession.getDatabase().rawQuery(SQL_DISTINCT_ENAME, null);
                try {
                    if (c.moveToFirst()){
                        do {
                            result.add(c.getString(0));
                        } while(c.moveToNext());
                    }
                } finally {
                    c.close();
                }
                return result;
            }
        });
    }

    @Override
    public void deleteSearchHistory() {
        //删除数据库
        mDaoSession.getSearchHistoryDao().deleteAll();
    }
}

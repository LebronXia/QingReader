package com.riane.qingreader.data.local;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Riane on 2017/7/30.
 */

public interface DbHelper {

    void addSearchHistory(String content);

    Observable<List<String>> querySearchHistory();

    void deleteSearchHistory();
}

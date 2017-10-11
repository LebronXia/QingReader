package com.riane.qingreader.data.local;

import com.riane.qingreader.data.network.reponse.ResultBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Riane on 2017/7/30.
 */

public interface DbHelper {

    void addSearchHistory(String content);

    Observable<List<String>> querySearchHistory();

    void deleteSearchHistory();

    //文章收藏
    Boolean getIsCollection(String id);

    void addConnection(ResultBean resultBean);

    void cancelCollection(String id);

    Observable<List<ResultBean>> queryForList(int offset);
}

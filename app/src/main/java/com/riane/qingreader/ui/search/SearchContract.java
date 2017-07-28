package com.riane.qingreader.ui.search;

import com.riane.qingreader.ui.base.BaseContract;

import java.util.List;

/**
 * Created by Riane on 2017/7/25.
 */

public interface SearchContract {

    interface View extends BaseContract.BaseView{
        void showHistory(List<String> results);
    }

    interface Presenter extends BaseContract.BasePresenter{
        //添加进数据库
        void insertHistory(String content);
        //查询数据库
        void loadSearchHistory();
        //删除全部
        void deleteAll();
    }
}

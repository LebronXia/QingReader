package com.riane.qingreader.ui.base;

/**
 * Created by Riane on 2017/7/5.
 */

public interface BaseContract {
    interface BasePresenter<T>{
        void detachView();
    }

    interface BaseView{
        void showError();
    }
}

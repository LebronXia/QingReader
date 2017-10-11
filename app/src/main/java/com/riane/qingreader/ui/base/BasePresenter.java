package com.riane.qingreader.ui.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by Riane on 2017/7/5.
 */

public class BasePresenter implements BaseContract.BasePresenter{

    protected CompositeDisposable mCompositeDisposable;

    protected void unSubscribe(){
        if (mCompositeDisposable != null){
            mCompositeDisposable.dispose();
        }
    }

    protected void addSubscrebe(Disposable subscribe){
        if (mCompositeDisposable == null){
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscribe);
    }


    @Override
    public void detachView() {
        unSubscribe();
    }
}

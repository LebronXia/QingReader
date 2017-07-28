package com.riane.qingreader.ui.gank.child.custom;

import com.riane.qingreader.data.network.reponse.GankIoDataBean;
import com.riane.qingreader.data.network.reponse.GankIoDayBean;
import com.riane.qingreader.ui.base.BaseContract;

/**
 * Created by Riane on 2017/7/14.
 */

public interface CustomGankContract {

    interface View extends BaseContract.BaseView{
        void showGankCustomData(GankIoDataBean gankIoDataBean);
    }

    interface Presenter extends BaseContract.BasePresenter{
        void getGankCustomData(String id, int page, int pre_pag);
    }
}

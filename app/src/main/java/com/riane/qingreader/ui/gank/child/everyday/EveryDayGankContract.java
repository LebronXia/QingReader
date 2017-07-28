package com.riane.qingreader.ui.gank.child.everyday;

import com.riane.qingreader.data.network.reponse.AndroidBean;
import com.riane.qingreader.data.network.reponse.GankIoDayBean;
import com.riane.qingreader.ui.base.BaseContract;

import java.util.List;

/**
 * Created by Riane on 2017/7/13.
 */

public interface EveryDayGankContract {

    interface View extends BaseContract.BaseView{
        void showGankEveryDayData(List<List<AndroidBean>> lists);
    }

    interface Presenter extends BaseContract.BasePresenter{
        void getGankIoDay(int year, int month, int day);
    }
}

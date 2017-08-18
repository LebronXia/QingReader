package com.riane.qingreader.ui.movie.MovieDetail;

import com.riane.qingreader.data.network.reponse.MovieDetailBean;
import com.riane.qingreader.ui.base.BaseContract;

/**
 * Created by xiaobozheng on 8/18/2017.
 */

public interface MovieDetailContract {

    interface View extends BaseContract.BaseView{
        void showMovieDetailData(MovieDetailBean movieDetailBean);
    }

    interface Presenter extends BaseContract.BasePresenter{
        void getMovieDetail(String id);
    }
}

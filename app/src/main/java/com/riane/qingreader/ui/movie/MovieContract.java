package com.riane.qingreader.ui.movie;

import com.riane.qingreader.data.network.reponse.film.Subject;
import com.riane.qingreader.ui.base.BaseContract;

import java.util.List;

/**
 * Created by xiaobozheng on 8/16/2017.
 */

public interface MovieContract {

    interface View extends BaseContract.BaseView{
        void showLiveMovieData(List<Subject> subjects);

        void showTop250MovieData(List<Subject> mTopSubjects);
    }

    interface Presenter extends BaseContract.BasePresenter{
        void getLiveMovie();

        void getTop250Movie(int start, int count);
    }

}

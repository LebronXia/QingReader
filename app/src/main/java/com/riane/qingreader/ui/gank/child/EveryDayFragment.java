package com.riane.qingreader.ui.gank.child;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.riane.qingreader.QingReaderApplication;
import com.riane.qingreader.R;
import com.riane.qingreader.data.ReaderRepository;
import com.riane.qingreader.data.ReaderRepositoryComponent;
import com.riane.qingreader.data.network.reponse.AndroidBean;
import com.riane.qingreader.data.network.reponse.GankIoDayBean;
import com.riane.qingreader.ui.adapter.EveryDayAdapter;
import com.riane.qingreader.ui.base.BaseFragment;
import com.riane.qingreader.ui.gank.child.everyday.DaggerEveryDayGankComponent;
import com.riane.qingreader.ui.gank.child.everyday.EveryDayGankContract;
import com.riane.qingreader.ui.gank.child.everyday.EveryDayGankPresenter;
import com.riane.qingreader.ui.gank.child.everyday.EveryDayGankPresenterModule;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Riane on 2017/7/12.
 */

public class EveryDayFragment extends BaseFragment implements EveryDayGankContract.View{

    private static final int DAY_OF_MILLISECOND = 24*60*60*1000;
    @BindView(R.id.xrv_everyday)
    XRecyclerView mxRvEveryday;
    private EveryDayAdapter mEveryDayAdapter;
    private int mPage = 1;
    private View mHeaderView;
    private View mFootView;
    private Date mDate;
    //没有数据的时候，往前倒退
    private Date mLastDate;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Inject
    EveryDayGankPresenter mEveryDayGankPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_everyday;
    }

    @Override
    protected void initInjector() {
        DaggerEveryDayGankComponent.builder()
                .everyDayGankPresenterModule(new EveryDayGankPresenterModule(this))
                .readerRepositoryComponent(((QingReaderApplication)getActivity().getApplication())
                        .getReaderRepositoryComponent()).build()
                .inject(this);
    }

    @Override
    protected void initView() {
        mDate = new Date();

    }

    @Override
    protected void initDatas() {
        mxRvEveryday.setPullRefreshEnabled(false);
        mxRvEveryday.setLoadingMoreEnabled(false);
        if (mHeaderView == null){
            mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.header_item_everyday, null, false);
            mxRvEveryday.addHeaderView(mHeaderView);
        }
        if (mFootView == null){

        }

        mEveryDayAdapter = new EveryDayAdapter();
        mxRvEveryday.setLayoutManager(new LinearLayoutManager(getActivity()));
        mxRvEveryday.setAdapter(mEveryDayAdapter);
        mxRvEveryday.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
            }

            @Override
            public void onLoadMore() {

            }
        });

        loadData();
    }

    @Override
    protected void loadData() {
        mDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(mDate);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        if(!isDataInitiated || !isViewInitiated || !mIsVisible){
            return;
        }
        loadEveryDayData();
    }

    public void loadEveryDayData(){
        mEveryDayGankPresenter.getGankIoDay(mYear, mMonth, mDay);
    }

    /**
     * 没有请求导数据，则请求前一天数据
     */
    private void requestBeforeData(){
      //  mDate = new Date();
        mDate.setTime(mDate.getTime() - DAY_OF_MILLISECOND);
        Calendar c = Calendar.getInstance();
        c.setTime(mDate);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        loadEveryDayData();
    }

    @Override
    protected void onRefresh() {

    }

    @Override
    public void showGankEveryDayData(List<List<AndroidBean>> lists) {
       if (lists.isEmpty()){
           requestBeforeData();
       } else {
           mEveryDayAdapter.addAll(lists);
           mxRvEveryday.refreshComplete();
           isDataInitiated = false;
       }
    }

    @Override
    public void showError() {

    }
}

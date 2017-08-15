package com.riane.qingreader.ui.gank.child;

import android.support.v7.widget.StaggeredGridLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.riane.qingreader.Contants;
import com.riane.qingreader.QingReaderApplication;
import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.GankIoDataBean;
import com.riane.qingreader.ui.adapter.WelfareAdapter;
import com.riane.qingreader.ui.base.BaseFragment;
import com.riane.qingreader.ui.gank.child.custom.CustomGankContract;
import com.riane.qingreader.ui.gank.child.custom.CustomGankPresenter;
import com.riane.qingreader.ui.gank.child.custom.CustomGankPresenterModule;
import com.riane.qingreader.ui.gank.child.custom.DaggerCustomGankComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Riane on 2017/7/12.
 */

public class WelfareFragment extends BaseFragment implements CustomGankContract.View{

    @BindView(R.id.rv_gank_welfare)
    XRecyclerView mRvGankWelfare;
    private WelfareAdapter mWelfareAdapter;
    private List<GankIoDataBean.ResultBean> resultBeanList = new ArrayList<>();
    private int mPage = 1;
    private boolean isRefresh;

    @Inject
    CustomGankPresenter mCustomGankPresenter;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_welfare;
    }

    @Override
    protected void initInjector() {
        DaggerCustomGankComponent.builder()
                .customGankPresenterModule(new CustomGankPresenterModule(this))
                .readerRepositoryComponent(((QingReaderApplication)getActivity().getApplication())
                        .getReaderRepositoryComponent()).build()
                .inject(this);

    }

    @Override
    protected void initView() {
        mWelfareAdapter = new WelfareAdapter(getActivity(), R.layout.item_welfare, resultBeanList);
        mRvGankWelfare.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRvGankWelfare.setAdapter(mWelfareAdapter);
        mRvGankWelfare.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                isRefresh = true;
                loadWelfareData();
            }

            @Override
            public void onLoadMore() {
                mPage ++;
                loadWelfareData();
            }
        });

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void onRefresh() {

    }

    @Override
    protected void loadData() {
        if(!isDataInitiated || !isViewInitiated || !mIsVisible){
            return;
        }
        loadWelfareData();
    }

    private void loadWelfareData() {
        mCustomGankPresenter.getGankCustomData("福利" , mPage, Contants.per_page);
    }

    @Override
    public void showGankCustomData(GankIoDataBean gankIoDataBean) {
        if (isRefresh){
            //mWelfareAdapter.clear();
            mRvGankWelfare.refreshComplete();
            resultBeanList.clear();
            resultBeanList.addAll(gankIoDataBean.getResults());
            mWelfareAdapter.notifyDataSetChanged();
            isRefresh = false;
        } else {
            if (gankIoDataBean != null && gankIoDataBean.getResults() != null && gankIoDataBean.getResults().size() > 0){
                mRvGankWelfare.refreshComplete();
                resultBeanList.addAll(gankIoDataBean.getResults());
                mWelfareAdapter.notifyDataSetChanged();
            } else {
                mRvGankWelfare.setNoMore(true);
            }
        }

    }

    @Override
    public void showError() {

    }
}

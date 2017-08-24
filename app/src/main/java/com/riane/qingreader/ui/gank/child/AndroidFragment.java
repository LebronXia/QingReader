package com.riane.qingreader.ui.gank.child;

import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.riane.qingreader.Contants;
import com.riane.qingreader.QingReaderApplication;
import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.GankIoDataBean;
import com.riane.qingreader.ui.adapter.AndoridAdapter;
import com.riane.qingreader.ui.base.BaseFragment;
import com.riane.qingreader.ui.gank.child.custom.CustomGankContract;
import com.riane.qingreader.ui.gank.child.custom.CustomGankPresenter;
import com.riane.qingreader.ui.gank.child.custom.CustomGankPresenterModule;
import com.riane.qingreader.ui.gank.child.custom.DaggerCustomGankComponent;
import com.riane.qingreader.view.StateLayout;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Riane on 2017/7/12.
 */

public class AndroidFragment extends BaseFragment implements CustomGankContract.View, StateLayout.OnReloadListener{

    @BindView(R.id.xrv_android)
    XRecyclerView mAndroidXRecycleView;
    private AndoridAdapter mAndoridAdapter;
    private int mPage = 1;

    @Inject
    CustomGankPresenter mCustomGankPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_android;
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

    }

    @Override
    protected void initDatas() {
        stateLayout.showLoadingView();
        isViewInitiated = true;
        mAndoridAdapter = new AndoridAdapter();
        mAndroidXRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAndroidXRecycleView.setAdapter(mAndoridAdapter);
        mAndroidXRecycleView.setLoadingListener(new XRecyclerView.LoadingListener(){
            @Override
            public void onRefresh() {
                mPage = 1;
                loadAndroidData();
            }

            @Override
            public void onLoadMore() {
                mPage ++;
                loadAndroidData();
            }
        });
    }

    @Override
    protected void loadData() {
        if(!isDataInitiated || !isViewInitiated || !mIsVisible){
            return;
        }
        loadAndroidData();
    }

    private void loadAndroidData(){
        mCustomGankPresenter.getGankCustomData("Android", mPage, Contants.per_page);
    }

    @Override
    public void showGankCustomData(GankIoDataBean gankIoDataBean) {
        if (gankIoDataBean.getResults().size() <= 0){
            stateLayout.showEmptyView();
        } else {
            stateLayout.showSuccessView();
            if (mPage == 1){
                if (gankIoDataBean != null && gankIoDataBean.getResults() != null && gankIoDataBean.getResults().size() > 0){
                    //给RecycleVIew填充数据
                    setAdapter(gankIoDataBean);
                }
            } else {
                if (gankIoDataBean != null && gankIoDataBean.getResults() != null && gankIoDataBean.getResults().size() > 0){
                    mAndroidXRecycleView.refreshComplete();
                    mAndoridAdapter.addAll(gankIoDataBean.getResults());
                }

            }
        }

    }

    @Override
    public void onRefresh() {
        loadAndroidData();
    }

    public void setAdapter(GankIoDataBean gankIoDataBean) {
        mAndoridAdapter.clear();
        mAndoridAdapter.addAll(gankIoDataBean.getResults());
        mAndroidXRecycleView.refreshComplete();
        isDataInitiated = false;
    }

    @Override
    public void showError() {
        //加载失败展示错误的view
        stateLayout.showErrorView();
    }

}

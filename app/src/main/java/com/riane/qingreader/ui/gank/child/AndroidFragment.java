package com.riane.qingreader.ui.gank.child;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;

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
import com.riane.qingreader.util.RxBus;
import com.riane.qingreader.view.StateLayout;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * Created by Riane on 2017/7/12.
 */

public class AndroidFragment extends BaseFragment implements CustomGankContract.View, StateLayout.OnReloadListener{

    @BindView(R.id.xrv_android)
    XRecyclerView mAndroidXRecycleView;
    private Flowable<Boolean> observable;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        observable = RxBus.getInstance().register(Boolean.class);
        observable.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                refreshUI();
            }
        });
    }

    private void refreshUI() {
        TypedValue backgroundcolor_item = new TypedValue();
        TypedValue textColor = new TypedValue();
        TypedValue backgroundcolor = new TypedValue();

        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(R.attr.backgroundcolor_item, backgroundcolor_item, true);
        theme.resolveAttribute(R.attr.textcolor, textColor, true);
        theme.resolveAttribute(R.attr.backgroundcolor, backgroundcolor, true);
        Resources resources = getResources();

        int childCount = mAndroidXRecycleView.getChildCount();
        for (int childIndex = 1; childIndex < childCount; childIndex ++){
//            ViewGroup childView = (ViewGroup) mAndroidXRecycleView.getChildAt(childIndex);
//            LinearLayout ll = (LinearLayout) childView.findViewById(R.id.ll_android_top);
//            ll.setBackgroundResource(backgroundcolor_item.resourceId);
//            TextView title = (TextView) childView.findViewById(R.id.tv_android_des);
//            title.setTextColor(resources.getColor(textColor.resourceId));
        }

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

    @Override
    public void onDetach() {
        super.onDetach();
        RxBus.getInstance().unregisterAll();
    }
}

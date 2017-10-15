package com.riane.qingreader.ui.gank.child;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;
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
import com.riane.qingreader.util.SPUtils;
import com.riane.qingreader.view.StateLayout;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Riane on 2017/7/12.
 */

public class CustomFragment extends BaseFragment implements CustomGankContract.View, StateLayout.OnReloadListener{

    private String mType = "all";
    private int mPage = 1;
    private View mHeaderView;

    @BindView(R.id.xrv_gank_custom)
    XRecyclerView mXRVGankCustom;
    private AndoridAdapter mAndoridAdapter;
    @Inject
    CustomGankPresenter mCustomGankPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_custom;
    }

    @Override
    protected void initInjector() {
        DaggerCustomGankComponent.builder()
                .customGankPresenterModule(new CustomGankPresenterModule(this))
                .readerRepositoryComponent(((QingReaderApplication)getActivity().getApplication())
                        .getReaderRepositoryComponent()).build()
                .inject(this);
    }

    protected void loadData(){
        if(!isDataInitiated || !isViewInitiated || !mIsVisible){
            return;
        }
        loadCustomData();
    }

    private void loadCustomData() {
        mCustomGankPresenter.getGankCustomData(mType, mPage, Contants.per_page);
    }

    @Override
    public void onRefresh() {
        loadCustomData();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void refreshUI() {
        TypedValue backgroundcolor_item = new TypedValue(); //item的背景颜色
        TypedValue textColor = new TypedValue();  //字体颜色
        TypedValue backgroundcolor = new TypedValue();   //背景颜色

        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(R.attr.backgroundcolor_item, backgroundcolor_item, true);
        theme.resolveAttribute(R.attr.textcolor, textColor, true);
        theme.resolveAttribute(R.attr.backgroundcolor, backgroundcolor, true);
        Resources resources = getResources();
        mXRVGankCustom.setBackgroundResource(backgroundcolor_item.resourceId);


        int childCount = mXRVGankCustom.getChildCount();
        if (childCount > 2){
            for (int childIndex = 2; childIndex < childCount; childIndex ++){
                ViewGroup headView = (ViewGroup) mXRVGankCustom.getChildAt(1);
                headView.setBackgroundResource(backgroundcolor_item.resourceId);
                TextView headText = (TextView) headView.findViewById(R.id.tv_select_name);
                headText.setTextColor(resources.getColor(textColor.resourceId));
                LinearLayout llChooseCatalogue = (LinearLayout) headView.findViewById(R.id.ll_choose_catalogue);
                llChooseCatalogue.setBackgroundResource(backgroundcolor.resourceId);
                TextView tvChooseCatalogue = (TextView) llChooseCatalogue.findViewById(R.id.tv_choose_catalogue);
                tvChooseCatalogue.setTextColor(resources.getColor(textColor.resourceId));

                if (childIndex > 2){
                    ViewGroup childView = (ViewGroup) mXRVGankCustom.getChildAt(childIndex);
                    childView.setBackgroundResource(backgroundcolor_item.resourceId);
                    TextView title = (TextView) childView.findViewById(R.id.tv_android_des);
                    title.setTextColor(resources.getColor(textColor.resourceId));
                }

            }
        }


    }

    @Override
    protected void initDatas() {
        stateLayout.showLoadingView();
        isViewInitiated = true;
        mAndoridAdapter = new AndoridAdapter(getActivity());
        mXRVGankCustom.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mXRVGankCustom.setLayoutManager(new VegaLayoutManager());
        mXRVGankCustom.setAdapter(mAndoridAdapter);
        mXRVGankCustom.setLoadingListener(new XRecyclerView.LoadingListener(){
            @Override
            public void onRefresh() {
                mPage = 1;
                loadCustomData();
            }

            @Override
            public void onLoadMore() {
                mPage ++;
                loadCustomData();
            }
        });
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
                    mXRVGankCustom.refreshComplete();
                    mAndoridAdapter.addAll(gankIoDataBean.getResults());
                }
            }
        }


    }

    public void setAdapter(GankIoDataBean gankIoDataBean) {
        if (mHeaderView == null){
            mHeaderView = View.inflate(getActivity(), R.layout.header_item_gank_custom, null);
            mXRVGankCustom.addHeaderView(mHeaderView);
        }

        initAdapterHeader(mHeaderView);

        mAndoridAdapter.clear();
        mAndoridAdapter.setType(true);

        mAndoridAdapter.addAll(gankIoDataBean.getResults());
        mXRVGankCustom.refreshComplete();
        isDataInitiated = false;
    }

    private void initAdapterHeader(View mHeaderView) {

        View view = mHeaderView.findViewById(R.id.ll_choose_catalogue);
        final TextView mTvSelectName = (TextView) mHeaderView.findViewById(R.id.tv_select_name);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BottomSheet.Builder(getActivity()).title("选择分类").sheet(R.menu.gank_bottomsheet).listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            //福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
                            case R.id.gank_all:
                                if (!isSelect("全部")){
                                    changeContentName(mTvSelectName, "all");
                                }
                                break;
                            case R.id.gank_ios:
                                if (!isSelect("iOS")){
                                    changeContentName(mTvSelectName, "iOS");
                                }
                                break;
                            case R.id.gank_app:
                                if (!isSelect("App")){
                                    changeContentName(mTvSelectName, "App");
                                }
                                break;
                            case R.id.gank_qianduan:
                                if (!isSelect("前端")){
                                    changeContentName(mTvSelectName, "前端");
                                }
                                break;
                            case R.id.gank_movie:
                                if (!isSelect("休息视频")){
                                    changeContentName(mTvSelectName, "休息视频");
                                }
                                break;
                            case R.id.gank_source:
                                if (!isSelect("拓展资源")){
                                    changeContentName(mTvSelectName, "拓展资源");
                                }
                                break;
                        }
                    }
                }).show();
            }
        });

    }

    public boolean isSelect(String selectName){
        if (selectName.equals(SPUtils.getString(Contants.KEY_GANK_SELECT, "全部"))){
            return true;
        }
        return false;
    }

    /**
     * gank中改变分类
     * @param tvSelectName
     * @param selectName
     */
    private void changeContentName(TextView tvSelectName, String selectName) {
       // TextView mtvName = (TextView) mHeaderView.findViewById(R.id.tv_select_name);
        if (selectName.equals("all")){
            selectName = "全部";
        }
        tvSelectName.setText(selectName);
        SPUtils.putString(Contants.KEY_GANK_SELECT, selectName);
        mAndoridAdapter.clear();
        mPage = 1;
        mType = selectName;
        loadCustomData();
    }

    @Override
    public void showError() {
        //加载失败展示错误的view
        stateLayout.showErrorView();
    }
}

package com.riane.qingreader.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.riane.qingreader.view.StateLayout;

import butterknife.ButterKnife;

/**
 * Created by Riane on 2017/7/4.
 */

public abstract class BaseFragment extends Fragment{
    public Context mContext;
    //fragment是否显示了
    protected boolean mIsVisible = false;
    protected boolean isViewInitiated = false; //控件是否初始化完成
    protected boolean isDataInitiated = true; //数据第一次加载
    protected View parentView;
    private LinearLayout mLlRootLayout;
    //protected LayoutInflater mInflater;
    protected StateLayout stateLayout;

    public abstract int getLayoutResId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLlRootLayout = new LinearLayout(getActivity());
        parentView = inflater.inflate(getLayoutResId(), container, false);
        stateLayout = new StateLayout(getActivity());
        mLlRootLayout.addView(stateLayout);
        stateLayout.bindSuccessView(parentView);
        return mLlRootLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        initInjector();
        initView();
    }

    protected abstract void initInjector();

    protected abstract void initView();

    protected abstract void initDatas();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    /**
     * 在这里实现Fragment数据的缓加载
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onIsvisible();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDatas();
    }

    protected void onIsvisible(){
    }

    protected void onVisible(){
        loadData();
    }

    /**
     * 显示时加载数据,需要这样的使用
     * 注意声明 isPrepared，先初始化
     * 生命周期会先执行 setUserVisibleHint 再执行onActivityCreated
     * 在 onActivityCreated 之后第一次显示加载数据，只加载一次
     */
    protected void loadData(){};


//    /**
//     * 加载事变后点击后的操作
//     */
//    protected void onRefresh(){};
}

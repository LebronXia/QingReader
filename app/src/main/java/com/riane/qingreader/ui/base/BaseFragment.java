package com.riane.qingreader.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Riane on 2017/7/4.
 */

public abstract class BaseFragment extends Fragment{
    public Context mContext;
    //fragment是否显示了
    protected boolean mIsVisible = false;
    protected boolean isViewInitiated; //控件是否初始化完成
    protected boolean isDataInitiated = true; //数据第一次加载
    protected View parentView;
    protected LayoutInflater mInflater;

    public abstract int getLayoutResId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(getLayoutResId(), container, false);

        return parentView;
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
        isViewInitiated = true;
        initDatas();
    }

    protected void onIsvisible(){
    }

    protected void onVisible(){
        loadData();
    }

    /**
     * 显示的时候加载数据
     */
    protected abstract void loadData();


    /**
     * 加载事变后点击后的操作
     */
    protected abstract void onRefresh();
}

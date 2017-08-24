package com.riane.qingreader.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.riane.qingreader.R;

/**
 * Created by xiaobozheng on 8/24/2017.
 */

public class StateLayout extends FrameLayout {

    private View loadingView;
    private View errorView;
    private View successView;
    private View emptyView;
    // 动画
    private AnimationDrawable mAnimationDrawable;

    public StateLayout(@NonNull Context context) {
        this(context, null);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 添加4个子View  加载成功，加载汇总， 加载没数据， 加载失败
     */
    private void initView() {
        loadingView = View.inflate(getContext(), R.layout.loading_layout, null);
        addView(loadingView);

        errorView = View.inflate(getContext(), R.layout.error_layout, null);
        LinearLayout mLlError = (LinearLayout) errorView.findViewById(R.id.ll_error_refresh);
        mLlError.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               //先显示LoadingView
                showLoadingView();
                if (listener != null){
                    listener.onRefresh();
                }
            }
        });
        addView(errorView);
        //3.添加空白的view
        emptyView = View.inflate(getContext(), R.layout.empty_layout, null);
        addView(emptyView);
        //4.加载成功的View在各界面是不同的，所以提供一个方法bindsucessview动态添加
        //一开始隐藏所有的View
        hideAll();

    }

    /**
     * 添加一个成功的View进来
     */
    public void bindSuccessView(View view){
        successView = view;
        if(successView!=null){
            successView.setVisibility(View.INVISIBLE);//隐藏successView
            //将它添加进来
            addView(successView);
        }
    }

    public void showSuccessView(){
        //先隐藏其他的
        hideAll();
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }

        if(successView!=null){
            successView.setVisibility(View.VISIBLE);
        }
    }
    public void showEmptyView(){
        //先隐藏其他的
        hideAll();

        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }

        emptyView.setVisibility(View.VISIBLE);
    }
    public void showErrorView(){
        //先隐藏其他的
        hideAll();

        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }

        errorView.setVisibility(View.VISIBLE);
    }
    public void showLoadingView(){
        //先隐藏其他的
        hideAll();
        loadingView.setVisibility(View.VISIBLE);
        ImageView img = (ImageView) loadingView.findViewById(R.id.img_progress);

        //加载动画
        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
        // 默认进入页面就开启动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
    }

    /**
     * 隐藏所有的View
     */
    public void hideAll(){
        //设置各界面不可见，同时让他们不重新layout，要用的时候直接show就行了
        loadingView.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.INVISIBLE);
        emptyView.setVisibility(View.INVISIBLE);
        if(successView!=null){
            successView.setVisibility(View.INVISIBLE);
        }
    }

    private OnReloadListener listener;

    public void setOnReloadListener(OnReloadListener listener){
        this.listener = listener;
    }

    public interface OnReloadListener{
        /**
         * 当重新加载的按钮被点击的时候调用
         */
        void onRefresh();
    }


}

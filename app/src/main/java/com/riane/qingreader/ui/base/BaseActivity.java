package com.riane.qingreader.ui.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.riane.qingreader.Contants;
import com.riane.qingreader.R;
import com.riane.qingreader.util.RxBus;
import com.riane.qingreader.util.SPUtils;

import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * Created by Riane on 2017/7/4.
 */

public abstract class BaseActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    public Context mContext;
    private Flowable<Boolean> observable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        initTheme();
        setContentView(getLayoutId());
        mContext = this;
        ButterKnife.bind(this);
        mToolbar = ButterKnife.findById(this, R.id.toolbar_common);
        //mToolbar.setTitle("");
       // setTheme(R.style.NightTheme);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }

        observable = RxBus.getInstance().register(Boolean.class);
        observable.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                showAnimation();
                refreshUI();
                refreshStatusBar();
            }
        });

        initInjector();
        initViews();
        initData();
    }

    private void refreshStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            TypedValue statusBarColor = new TypedValue();
            Resources.Theme theme = getTheme();
            theme.resolveAttribute(R.attr.colorPrimaryDark, statusBarColor, true);

            Resources resources = getResources();
            getWindow().setStatusBarColor(resources.getColor(statusBarColor.resourceId));
        }
    }

    protected void refreshToolbar(Toolbar toolbar){
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.toolbarColor, typedValue, true);
        toolbar.setBackgroundColor(getResources().getColor(typedValue.resourceId));
    }

    protected void initTheme(){
        boolean isDayMode = SPUtils.getBoolean(Contants.KEY_MODE_NIGHT, true);
        if (isDayMode){
            setTheme(R.style.DayTheme);
        } else {
            setTheme(R.style.NightTheme);
        }
    }

    private void showAnimation(){
        final View decorView = getWindow().getDecorView();
        Bitmap cacheBitmap = getCacheBitmapFromView(decorView);
        if (decorView instanceof ViewGroup && cacheBitmap != null) {
            final View view = new View(this);
            view.setBackgroundDrawable(new BitmapDrawable(getResources(), cacheBitmap));
            ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) decorView).addView(view, layoutParam);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            objectAnimator.setDuration(300);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((ViewGroup) decorView).removeView(view);
                }
            });
            objectAnimator.start();
        }

    }

    /**
     * 获取一个 View 的缓存视图
     * 用于将View中的内容转换成Bitmap
     *
     * @param view
     * @return
     */
    private Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        //设置图片的缓存
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }


    protected abstract int getLayoutId();

    protected abstract void initInjector();

    protected abstract void initViews();

    protected abstract void initData();

    protected abstract void refreshUI();

}

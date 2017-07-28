package com.riane.qingreader.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.riane.qingreader.R;

import butterknife.ButterKnife;

/**
 * Created by Riane on 2017/7/4.
 */

public abstract class BaseActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        mContext = this;
        ButterKnife.bind(this);
        mToolbar = ButterKnife.findById(this, R.id.toolbar_common);
        //mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }

        initInjector();
        initViews();
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void initInjector();

    protected abstract void initViews();

    protected abstract void initData();

}

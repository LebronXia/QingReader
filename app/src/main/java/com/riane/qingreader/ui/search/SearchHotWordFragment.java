package com.riane.qingreader.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.riane.qingreader.R;
import com.riane.qingreader.ui.base.BaseEnum;
import com.riane.qingreader.ui.base.BaseFragment;
import com.riane.qingreader.util.DensityUtil;

import butterknife.BindView;

/**
 * Created by Riane on 2017/7/28.
 */

public class SearchHotWordFragment extends BaseFragment{

    @BindView(R.id.fbl_search_type)
    FlexboxLayout mFblSearchType;
    @BindView(R.id.fb_search_history)
    FlexboxLayout mFblSearchHistory;
    @BindView(R.id.tv_search_clear)
    TextView mTvSearchclear;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_search_hotword;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        initFleboxType();
    }

    private void initFleboxType() {
        mFblSearchType.setFlexWrap(FlexWrap.WRAP);
        for (BaseEnum nameEnum : BaseEnum.values()){
            final TextView tv_bg = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.view_search_type
            ,mFblSearchType, false);

            mFblSearchType.addView(tv_bg);
            FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) tv_bg.getLayoutParams();
            layoutParams.setMargins(DensityUtil.dip2px(getActivity(), 10),
                    DensityUtil.dip2px(getActivity(), 5), 0, DensityUtil.dip2px(getActivity(), 5));
            tv_bg.setText(nameEnum.getValue());
            mFblSearchType.getChildAt(0).setSelected(true);
            tv_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < mFblSearchType.getChildCount(); i++){
                        mFblSearchType.getChildAt(i).setSelected(false);
                    }
                    tv_bg.setSelected(true);
                }
            });
        }
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onRefresh() {

    }
}

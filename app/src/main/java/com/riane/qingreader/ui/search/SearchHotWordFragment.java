package com.riane.qingreader.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.riane.qingreader.QingReaderApplication;
import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.Result;
import com.riane.qingreader.ui.event.SelectContentEvent;
import com.riane.qingreader.ui.event.SelectTypeEvent;
import com.riane.qingreader.ui.base.BaseEnum;
import com.riane.qingreader.ui.base.BaseFragment;
import com.riane.qingreader.util.DensityUtil;
import com.riane.qingreader.util.RxBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Riane on 2017/7/28.
 */

public class SearchHotWordFragment extends BaseFragment implements SearchContract.View{

    @BindView(R.id.fbl_search_type)
    FlexboxLayout mFblSearchType;
    @BindView(R.id.fb_search_history)
    FlexboxLayout mFblSearchHistory;

    @Inject
    SearchPresenter searchPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_search_hotword;
    }

    @Override
    protected void initInjector() {
        DaggerSearchComponent.builder()
                .searchPresenterModule(new SearchPresenterModule(this))
                .readerRepositoryComponent(((QingReaderApplication)getActivity().getApplication())
                        .getReaderRepositoryComponent()).build()
                .inject(this);
    }

    @Override
    protected void initView() {
        initFleboxType();
    }

    @Override
    protected void refreshUI() {

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
                    SelectTypeEvent selectTypeEvent = new SelectTypeEvent();
                    selectTypeEvent.setSelectedType(tv_bg.getText().toString());
                    RxBus.getInstance().post(selectTypeEvent);
                    tv_bg.setSelected(true);
                }
            });
        }
    }

    @Override
    protected void initDatas() {
        searchPresenter.loadSearchHistory();
    }

    @OnClick(R.id.tv_search_clear)
    public void onClick(){
        Toast.makeText(getContext(), "点击清空" , Toast.LENGTH_SHORT).show();
        searchPresenter.deleteAll();
        searchPresenter.loadSearchHistory();
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showHistory(List<String> results) {
            mFblSearchHistory.setFlexWrap(FlexWrap.WRAP);
            mFblSearchHistory.removeAllViews();
            for (String content : results){
                final TextView tv_history = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.view_search_type, mFblSearchHistory, false);
                mFblSearchHistory.addView(tv_history);
                tv_history.setText(content);
                FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) tv_history.getLayoutParams();
                layoutParams.setMargins(DensityUtil.dip2px(getActivity(), 10),
                        DensityUtil.dip2px(getActivity(), 5), 0, DensityUtil.dip2px(getActivity(), 5));
                tv_history.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //searchPresenter.loadData(tv_history.getText().toString(), );
                       // listener.onSearch(tv_his.getText().toString());
                        SelectContentEvent selectContentEvent = new SelectContentEvent();
                        selectContentEvent.setSelectContent(tv_history.getText().toString());
                        RxBus.getInstance().post(selectContentEvent);
                    }
                });
            }
    }

    @Override
    public void showSearchList(List<Result> results) {

    }
}

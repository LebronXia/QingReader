package com.riane.qingreader.ui.gank.child;

import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.riane.qingreader.QingReaderApplication;
import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.AndroidBean;
import com.riane.qingreader.ui.adapter.EveryDayAdapter;
import com.riane.qingreader.ui.base.BaseFragment;
import com.riane.qingreader.ui.gank.child.everyday.DaggerEveryDayGankComponent;
import com.riane.qingreader.ui.gank.child.everyday.EveryDayGankContract;
import com.riane.qingreader.ui.gank.child.everyday.EveryDayGankPresenter;
import com.riane.qingreader.ui.gank.child.everyday.EveryDayGankPresenterModule;
import com.riane.qingreader.view.StateLayout;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Riane on 2017/7/12.
 */

public class EveryDayFragment extends BaseFragment implements EveryDayGankContract.View, StateLayout.OnReloadListener{

    private static final int DAY_OF_MILLISECOND = 24*60*60*1000;
    @BindView(R.id.xrv_everyday)
    XRecyclerView mxRvEveryday;
    private EveryDayAdapter mEveryDayAdapter;
    private int mPage = 1;
    private View mHeaderView;
    private View mFootView;
    private Date mDate;
    //没有数据的时候，往前倒退
    private Date mLastDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    private LinearLayoutManager mLinearLayoutManager;

    @Inject
    EveryDayGankPresenter mEveryDayGankPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_everyday;
    }

    @Override
    protected void initInjector() {
        DaggerEveryDayGankComponent.builder()
                .everyDayGankPresenterModule(new EveryDayGankPresenterModule(this))
                .readerRepositoryComponent(((QingReaderApplication)getActivity().getApplication())
                        .getReaderRepositoryComponent()).build()
                .inject(this);
    }

    @Override
    protected void initView() {
        mDate = new Date();

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

        mxRvEveryday.setBackgroundColor(backgroundcolor.resourceId);
        int childCount = mxRvEveryday.getChildCount();
        //int childCount = 10;
        if (childCount > 2){
            //监听RecycleView在顶部
            if (mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0){
                ViewGroup headView = (ViewGroup) mxRvEveryday.getChildAt(1);
                LinearLayout llHeadView = (LinearLayout) headView.findViewById(R.id.ll_headerView);
                llHeadView.setBackgroundResource(backgroundcolor_item.resourceId);
                TextView tvheadChild1 = (TextView) headView.findViewById(R.id.tv_headview_child_1);
                tvheadChild1.setTextColor(resources.getColor(textColor.resourceId));
                TextView tvheadChild2 = (TextView) headView.findViewById(R.id.tv_headview_child_2);
                tvheadChild2.setTextColor(resources.getColor(textColor.resourceId));
                TextView tvheadChild3 = (TextView) headView.findViewById(R.id.tv_headview_child_3);
                tvheadChild3.setTextColor(resources.getColor(textColor.resourceId));
            }

            for (int childIndex = 2; childIndex < childCount; childIndex ++){
                ViewGroup childView = (ViewGroup) mxRvEveryday.getChildAt(childIndex);
                int itemType = mEveryDayAdapter.getItemViewType(childIndex - 2);
                switch (itemType){
                    case 0:
                        RelativeLayout rlEveryDayTitle = (RelativeLayout) childView.findViewById(R.id.rl_everyday_title);
                        rlEveryDayTitle.setBackgroundResource(backgroundcolor_item.resourceId);
                        break;
                    case 1:
                        LinearLayout llEverydayOne = (LinearLayout) childView.findViewById(R.id.ll_one_photo);
                        TextView tvEverydayOne = (TextView) childView.findViewById(R.id.tv_one_photo_title);
                        llEverydayOne.setBackgroundResource(backgroundcolor_item.resourceId);
                        tvEverydayOne.setTextColor(resources.getColor(textColor.resourceId));
                        break;
                    case 2:
                        LinearLayout llEverydayTwo= (LinearLayout) childView.findViewById(R.id.ll_two_photo);
                        TextView tvTwoPhotoOne = (TextView) childView.findViewById(R.id.tv_two_photo_one);
                        TextView tvTwoPhotoTwo = (TextView) childView.findViewById(R.id.tv_two_photo_two);
                        llEverydayTwo.setBackgroundResource(backgroundcolor_item.resourceId);
                        tvTwoPhotoOne.setTextColor(resources.getColor(textColor.resourceId));
                        tvTwoPhotoTwo.setTextColor(resources.getColor(textColor.resourceId));
                        break;
                    case 3:
                        LinearLayout llEverydayThree= (LinearLayout) childView.findViewById(R.id.ll_three_photo);
                        TextView tvThreePhotoOne = (TextView) childView.findViewById(R.id.tv_three_photo_one);
                        TextView tvThreePhotoTwo = (TextView) childView.findViewById(R.id.tv_three_photo_two);
                        TextView tvThreePhotoThree = (TextView) childView.findViewById(R.id.tv_three_photo_three);
                        llEverydayThree.setBackgroundResource(backgroundcolor_item.resourceId);
                        tvThreePhotoOne.setTextColor(resources.getColor(textColor.resourceId));
                        tvThreePhotoTwo.setTextColor(resources.getColor(textColor.resourceId));
                        tvThreePhotoThree.setTextColor(resources.getColor(textColor.resourceId));
                        break;
                }
            }

        }

    }

    @Override
    protected void initDatas() {
        stateLayout.showLoadingView();
        isViewInitiated = true;
        mxRvEveryday.setPullRefreshEnabled(false);
        mxRvEveryday.setLoadingMoreEnabled(false);
        if (mHeaderView == null){
            mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.header_item_everyday, null, false);
            mxRvEveryday.addHeaderView(mHeaderView);
        }
        if (mFootView == null){

        }

        mEveryDayAdapter = new EveryDayAdapter();
        mLinearLayoutManager= new LinearLayoutManager(getActivity());
        mxRvEveryday.setLayoutManager(mLinearLayoutManager);
        mxRvEveryday.setAdapter(mEveryDayAdapter);
        mxRvEveryday.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
            }

            @Override
            public void onLoadMore() {

            }
        });

        loadData();
    }

    @Override
    protected void loadData() {
        mDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(mDate);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        if(!isDataInitiated || !isViewInitiated || !mIsVisible){
            return;
        }
        loadEveryDayData();
    }

    public void loadEveryDayData(){
        mEveryDayGankPresenter.getGankIoDay(mYear, mMonth, mDay);
    }

    /**
     * 没有请求导数据，则请求前一天数据
     */
    private void requestBeforeData(){
      //  mDate = new Date();
        mDate.setTime(mDate.getTime() - DAY_OF_MILLISECOND);
        Calendar c = Calendar.getInstance();
        c.setTime(mDate);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        loadEveryDayData();
    }

    public void onRefresh() {
        loadEveryDayData();
    }

    @Override
    public void showGankEveryDayData(List<List<AndroidBean>> lists) {
       if (lists.isEmpty()){
           requestBeforeData();
       } else {
           stateLayout.showSuccessView();
           mEveryDayAdapter.addAll(lists);
           mxRvEveryday.refreshComplete();
           isDataInitiated = false;
       }
    }

    @Override
    public void showError() {
        //加载失败展示错误的view
        stateLayout.showErrorView();
    }
}

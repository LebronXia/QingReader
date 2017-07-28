package com.riane.qingreader.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.riane.qingreader.QingReaderApplication;
import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.AndroidBean;
import com.riane.qingreader.data.network.reponse.GankIoDataBean;
import com.riane.qingreader.ui.base.BaseContract;
import com.riane.qingreader.util.ImgLoadUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Riane on 2017/7/19.
 */

public class EveryDayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private static final int TYPE_THREE = 3;

    private List<List<AndroidBean>> lists = new ArrayList<>();

    public EveryDayAdapter(){

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case TYPE_TITLE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_everyday_title, parent, false);
                holder = new TitleViewHolder(view);
                break;
            case TYPE_ONE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_everyday_one, parent, false);
                holder = new OnePhotoViewHolder(view);
                break;
            case TYPE_TWO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_everyday_two, parent, false);
                holder = new TwoPhotoViewHolder(view);
                break;
            case TYPE_THREE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_everyday_three, parent, false);
                holder = new ThreePhotoViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof TitleViewHolder){
            final TitleViewHolder mTitleViewHolder = (TitleViewHolder) holder;
            String title = lists.get(position).get(0).getType_title();
            mTitleViewHolder.mTvTitleType.setText(title);

            if ("Android".equals(title)){
                mTitleViewHolder.mIvTitleType.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.home_title_android));
            } else if ("福利".equals(title)){
                mTitleViewHolder.mIvTitleType.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.home_title_meizi));
            } else if("iOS".equals(title)){
                mTitleViewHolder.mIvTitleType.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.home_title_ios));
            } else if ("休息视频".equals(title)){
                mTitleViewHolder.mIvTitleType.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.home_title_movie));
            } else if ("拓展资源".equals(title)){
                mTitleViewHolder.mIvTitleType.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.home_title_source));
            } else if ("瞎推荐".equals(title)){
                mTitleViewHolder.mIvTitleType.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.home_title_xia));
            } else if ("前端".equals(title)){
                mTitleViewHolder.mIvTitleType.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.home_title_qian));
            } else if ("App".equals(title)){
                mTitleViewHolder.mIvTitleType.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.home_title_app));
            }
        } else if (holder instanceof OnePhotoViewHolder){
            final OnePhotoViewHolder mOnePhotoViewHolder = (OnePhotoViewHolder) holder;
            if ("福利".equals(lists.get(position).get(0).getType())){
                mOnePhotoViewHolder.mTvOnePhotoTitle.setVisibility(View.GONE);
                mOnePhotoViewHolder.mIvOnePhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(mOnePhotoViewHolder.mIvOnePhoto.getContext())
                        .load(lists.get(position).get(0).getUrl())
                        .crossFade(1500)
                        .placeholder(R.mipmap.img_two_bi_one)
                        .into(mOnePhotoViewHolder.mIvOnePhoto);
            } else {
                mOnePhotoViewHolder.mTvOnePhotoTitle.setVisibility(View.VISIBLE);
                mOnePhotoViewHolder.mTvOnePhotoTitle.setText(lists.get(position).get(0).getDesc());
                ImgLoadUtil.displayRandom(1,lists.get(position).get(0).getImage_url(), mOnePhotoViewHolder.mIvOnePhoto);
            }
        } else if (holder instanceof TwoPhotoViewHolder){
            final TwoPhotoViewHolder mTwoPhotoViewHolder = (TwoPhotoViewHolder) holder;
            mTwoPhotoViewHolder.mTvTwoPhotoOne.setText(lists.get(position).get(0).getDesc());
            ImgLoadUtil.displayRandom(2,lists.get(position).get(0).getImage_url(), mTwoPhotoViewHolder.mIvTwoPhotoOne);

            mTwoPhotoViewHolder.mTvTwoPhotoTwo.setText(lists.get(position).get(1).getDesc());
            ImgLoadUtil.displayRandom(2, lists.get(position).get(1).getImage_url(), mTwoPhotoViewHolder.mIvTwoPhotoTwo);
        } else if (holder instanceof ThreePhotoViewHolder){
            final ThreePhotoViewHolder mThreePhotoViewHolder = (ThreePhotoViewHolder) holder;
            mThreePhotoViewHolder.mTvThreePhotoOne.setText(lists.get(position).get(0).getDesc());
            mThreePhotoViewHolder.mTvThreePhotoTwo.setText(lists.get(position).get(1).getDesc());
            mThreePhotoViewHolder.mTvThreePhotoThree.setText(lists.get(position).get(2).getDesc());

            ImgLoadUtil.displayRandom(3, lists.get(position).get(0).getImage_url(), mThreePhotoViewHolder.mIvThreePhotoOne);
            ImgLoadUtil.displayRandom(3, lists.get(position).get(1).getImage_url(), mThreePhotoViewHolder.mIvThreePhotoTwo);
            ImgLoadUtil.displayRandom(3, lists.get(position).get(2).getImage_url(), mThreePhotoViewHolder.mIvThreePhotoThree);
        }
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 :lists.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (!TextUtils.isEmpty(lists.get(position).get(0).getType_title())){
            return TYPE_TITLE;
        } else if (lists.get(position).size() == 1){
            return TYPE_ONE;
        } else if (lists.get(position).size() == 2){
            return TYPE_TWO;
        } else if (lists.get(position).size() == 3){
            return TYPE_THREE;
        }
        return super.getItemViewType(position);
    }

    private Context getContext(){
        return  QingReaderApplication.getAppContext();
    }

    public void addAll(List<List<AndroidBean>> androidBeanLists){
        lists.addAll(androidBeanLists);
        notifyDataSetChanged();
    }

    public void add(List<AndroidBean> androidBeans){
        lists.add(androidBeans);
        notifyDataSetChanged();
    }

    public void clear(){
        lists.clear();
        notifyDataSetChanged();
    }



    class TitleViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_title_type)
        ImageView mIvTitleType;
        @BindView(R.id.tv_title_type)
        TextView mTvTitleType;
        @BindView(R.id.ll_title_more)
        LinearLayout mLlTitleMore;
        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class OnePhotoViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_one_photo)
        ImageView mIvOnePhoto;
        @BindView(R.id.tv_one_photo_title)
        TextView mTvOnePhotoTitle;

        public OnePhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class TwoPhotoViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_two_photo_one)
        ImageView mIvTwoPhotoOne;
        @BindView(R.id.tv_two_photo_one)
        TextView mTvTwoPhotoOne;
        @BindView(R.id.iv_two_photo_two)
        ImageView mIvTwoPhotoTwo;
        @BindView(R.id.tv_two_photo_two)
        TextView mTvTwoPhotoTwo;
        public TwoPhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ThreePhotoViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_three_photo_one)
        ImageView mIvThreePhotoOne;
        @BindView(R.id.tv_three_photo_one)
        TextView mTvThreePhotoOne;
        @BindView(R.id.iv_three_photo_two)
        ImageView mIvThreePhotoTwo;
        @BindView(R.id.tv_three_photo_two)
        TextView mTvThreePhotoTwo;
        @BindView(R.id.iv_three_photo_three)
        ImageView mIvThreePhotoThree;
        @BindView(R.id.tv_three_photo_three)
        TextView  mTvThreePhotoThree;
        public ThreePhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

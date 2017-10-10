package com.riane.qingreader.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haozhang.lib.SlantedTextView;
import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.GankIoDataBean;
import com.riane.qingreader.ui.detail.DetailActivity;
import com.riane.qingreader.util.ImgLoadUtil;
import com.riane.qingreader.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Riane on 2017/7/14.
 */

public class AndoridAdapter extends RecyclerView.Adapter<AndoridAdapter.AndroidViewHolder>{

    private List<GankIoDataBean.ResultBean> mAndroidBeanList = new ArrayList<>();
    private boolean mIsAll = false;
    private Context mContext;

    public AndoridAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public AndroidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_android, parent, false);
        return new AndroidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AndroidViewHolder holder, int position) {

        final GankIoDataBean.ResultBean result = mAndroidBeanList.get(position);
        if (mIsAll){
            if (mAndroidBeanList.get(position).getType().equals("福利")){
                holder.mIvAllWelfare.setVisibility(View.VISIBLE);
                holder.mLlWelfareOther.setVisibility(View.GONE);
                ImgLoadUtil.displayEspImage(mAndroidBeanList.get(position).getUrl(), holder.mIvAllWelfare);
            } else {
                holder.mIvAllWelfare.setVisibility(View.GONE);
                holder.mLlWelfareOther.setVisibility(View.VISIBLE);
            }
        }

        if (mAndroidBeanList.get(position).getImages()!=null && mAndroidBeanList.get(position).getImages().size() > 0){
            ImgLoadUtil.displayGif(mAndroidBeanList.get(position).getImages().get(0), holder.mIvAndroidPic);
        } else {
            holder.mIvAndroidPic.setVisibility(View.GONE);
        }

        holder.mStvAndroidTag.setText(mAndroidBeanList.get(position).getType());
        switch (mAndroidBeanList.get(position).getType()){
            case "Android":
                holder.mStvAndroidTag.setSlantedBackgroundColor
                        (holder.itemView.getContext().getResources().getColor(R.color.gank_blue));
                break;
            case "福利":
                holder.mStvAndroidTag.setSlantedBackgroundColor
                        (holder.itemView.getContext().getResources().getColor(R.color.gank_yellow));
                break;
            case "iOS":
                holder.mStvAndroidTag.setSlantedBackgroundColor
                        (holder.itemView.getContext().getResources().getColor(R.color.gank_red));
                break;
            case "App":
                holder.mStvAndroidTag.setSlantedBackgroundColor
                        (holder.itemView.getContext().getResources().getColor(R.color.gank_green));
                break;
            case "前端":
                holder.mStvAndroidTag.setSlantedBackgroundColor
                        (holder.itemView.getContext().getResources().getColor(R.color.gank_gray));
                break;
            case "休息视频":
                holder.mStvAndroidTag.setSlantedBackgroundColor
                        (holder.itemView.getContext().getResources().getColor(R.color.gank_zise));
                break;
            case "拓展资源":
                holder.mStvAndroidTag.setSlantedBackgroundColor
                        (holder.itemView.getContext().getResources().getColor(R.color.gank_orenge));
                break;
            case "瞎推荐":
                holder.mStvAndroidTag.setSlantedBackgroundColor
                        (holder.itemView.getContext().getResources().getColor(R.color.gank_hese));
                break;

        }

        holder.mTvAndroidTime.setText(TimeUtil.getTranslateTime(mAndroidBeanList.get(position).getPublishedAt()));
        holder.mTvAndroidWho.setText(mAndroidBeanList.get(position).getWho());
        holder.mTvAndroidDes.setText(mAndroidBeanList.get(position).getDesc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.Companion.getINTENT_DETATL_RESULT(), result);
                mContext.startActivity(intent);
            }
        });

    }

    public void setType(boolean isAll){
        mIsAll = isAll;
    }

    @Override
    public int getItemCount() {
        return mAndroidBeanList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addAll(List<GankIoDataBean.ResultBean> androidBeanList){
        mAndroidBeanList.addAll(androidBeanList);
        notifyDataSetChanged();
    }

    public void add(GankIoDataBean.ResultBean androidBean){
        mAndroidBeanList.add(androidBean);
        notifyDataSetChanged();
    }

    public void clear(){
        mAndroidBeanList.clear();
        notifyDataSetChanged();
    }

    class AndroidViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_android_des)
        TextView mTvAndroidDes;
        @BindView(R.id.tv_android_who)
        TextView mTvAndroidWho;
        @BindView(R.id.tv_android_time)
        TextView mTvAndroidTime;
        @BindView(R.id.iv_android_pic)
        ImageView mIvAndroidPic;
        @BindView(R.id.iv_all_welfare)
        ImageView mIvAllWelfare;
        @BindView(R.id.ll_welfare_other)
        LinearLayout mLlWelfareOther;
        @BindView(R.id.stv_android_tag)
        SlantedTextView mStvAndroidTag;

        public AndroidViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

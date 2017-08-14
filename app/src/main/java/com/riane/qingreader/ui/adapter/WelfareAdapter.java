package com.riane.qingreader.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.GankIoDataBean;
import com.riane.qingreader.util.ImgLoadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozheng on 8/14/2017.
 */

public class WelfareAdapter extends RecyclerView.Adapter<WelfareAdapter.WelfareViewHolder>{

    private Context mContext;
    private List<GankIoDataBean.ResultBean> resultBeanList = new ArrayList<>();

    public WelfareAdapter(Context context, List<GankIoDataBean.ResultBean> resultBeens) {
        super();
        mContext = context;
        resultBeanList = resultBeens;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public WelfareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_welfare, parent, false);
        return new WelfareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WelfareViewHolder holder, int position) {
        ImgLoadUtil.displayEspImage(resultBeanList.get(position).getUrl(), holder.mIvGankWelfare);
    }

    public void addAll(List<GankIoDataBean.ResultBean> androidBeanList){
        resultBeanList.addAll(androidBeanList);
        notifyDataSetChanged();
    }

    public void clear(){
        resultBeanList.clear();
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return resultBeanList.size();
    }

    class WelfareViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_gank_walfare)
        ImageView mIvGankWelfare;

        public WelfareViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

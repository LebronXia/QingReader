package com.riane.qingreader.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.Result;
import com.riane.qingreader.util.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaobozheng on 8/11/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{

    private List<Result> results;
    private OnItemClickListener onItemClickListener;

    public SearchAdapter(List<Result> list) {
        super();
        results = list;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        Result result = results.get(position);
        holder.mTvSearchTitle.setText(result.getDesc());
        holder.mTvSearchAutInfo.setText(result.getType() + " . " + result.getWho() + " . " +
                TimeUtil.getTranslateTime(result.getPublishedAt()));

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_search_title)
        TextView mTvSearchTitle;
        @BindView(R.id.tv_search_authorinfo)
        TextView mTvSearchAutInfo;

        public SearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void addAll(List<Result> resultlist){
        results.addAll(resultlist);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface  OnItemClickListener{
        void OnItemClick(View v, int position);
    }
}

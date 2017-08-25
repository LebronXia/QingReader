package com.riane.qingreader.ui.base.baseAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Riane on 2017/8/15.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder>{
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected OnItemClickListener mOnItemClickListener;


    public CommonAdapter(Context context, int layoutId, List<T> datas)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        //holder.updatePosition(position);
        convert(holder, mDatas.get(position));

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    int position = holder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(view, holder , position);
                }
            }
        });

        holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                if (mOnItemClickListener != null) {
                    int position = holder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(view, holder, position);
                }
                return false;
            }
        });
    }

    public abstract void convert(ViewHolder holder, T t);

    public void addAll(List<T> data){
        this.mDatas.addAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        return mDatas == null ? 0 : mDatas.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, ViewHolder holder, int position);

        boolean onItemLongClick(View view, ViewHolder holder,  int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


}

package com.riane.qingreader.ui.adapter;

import android.content.Context;

import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.film.Subject;
import com.riane.qingreader.ui.base.baseAdapter.CommonAdapter;
import com.riane.qingreader.ui.base.baseAdapter.ViewHolder;

import java.util.List;

/**
 * Created by xiaobozheng on 8/25/2017.
 */

public class Top250MovieAdapter extends CommonAdapter<Subject>{

    public Top250MovieAdapter(Context context, int layoutId, List<Subject> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, Subject subject) {
        holder.setImageUrl(R.id.iv_top_photo, subject.getImages().getLarge());
        holder.setText(R.id.tv_top250_name, subject.getTitle());
        holder.setText(R.id.tv_top250_rate, String.format("评分：%s", subject.getRating().getAverage()));
    }
}

package com.riane.qingreader.ui.adapter;

import android.content.Context;

import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.film.Person;
import com.riane.qingreader.ui.base.baseAdapter.CommonAdapter;
import com.riane.qingreader.ui.base.baseAdapter.ViewHolder;

import java.util.List;

/**
 * Created by xiaobozheng on 8/25/2017.
 */

public class MovieDetailAdapter extends CommonAdapter<Person>{

    public MovieDetailAdapter(Context context, int layoutId, List<Person> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, Person person) {
        holder.setText(R.id.tv_item_detail_personname, person.getName());
        holder.setText(R.id.tv_item_detail_persontype, person.getType());

        holder.setImageUrl(R.id.iv_moviedetail_avatar, person.getAvatars().getLarge());
    }
}

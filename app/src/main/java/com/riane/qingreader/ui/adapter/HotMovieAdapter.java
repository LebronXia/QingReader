package com.riane.qingreader.ui.adapter;

import android.content.Context;

import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.film.Subject;
import com.riane.qingreader.ui.base.baseAdapter.CommonAdapter;
import com.riane.qingreader.ui.base.baseAdapter.ViewHolder;

import java.util.List;

/**
 * Created by xiaobozheng on 8/17/2017.
 */

public class HotMovieAdapter extends CommonAdapter<Subject>{



    public HotMovieAdapter(Context context, int layoutId, List<Subject> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, Subject subject) {
        StringBuilder directors = new StringBuilder();
        StringBuilder casts = new StringBuilder();
        StringBuilder genres = new StringBuilder();
        holder.setImageUrl(R.id.iv_hot_movie_cover, subject.getImages().getMedium());
        holder.setText(R.id.tv_hot_movie_title, subject.getTitle());
        for (int i = 0; i < subject.getDirectors().size(); i++){
            directors.append(subject.getDirectors().get(i).getName());
            if (i != subject.getDirectors().size() - 1){
                directors.append("/");
            }
        }
        for (int i = 0; i < subject.getCasts().size(); i++){
            casts.append(subject.getCasts().get(i).getName());
            if (i != subject.getCasts().size() - 1){
                casts.append("/");
            }
        }

        for (int i = 0; i < subject.getGenres().size(); i ++){
            genres.append(subject.getGenres().get(i));
            if (i != subject.getGenres().size() - 1){
                genres.append("/");
            }
        }


        holder.setText(R.id.tv_hot_movie_director, directors.toString());
        holder.setText(R.id.tv_hot_movie_casts, casts.toString());
        holder.setText(R.id.tv_hot_movie_genres, genres.toString());
        holder.setText(R.id.tv_hot_movie_rating_rate, String.valueOf(subject.getRating().getAverage()));
    }
}

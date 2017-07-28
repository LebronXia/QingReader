package com.riane.qingreader.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.riane.qingreader.R;

/**
 * Created by Riane on 2017/7/15.
 */

public class ImgLoadUtil {

    private static ImgLoadUtil instance;

    private ImgLoadUtil(){

    }

    public static ImgLoadUtil getInstance(){
        if (instance == null){
            instance = new ImgLoadUtil();
        }
        return instance;
    }

    /**
     * 加载妹子图
     * type识别是哪张图
     * @param url
     * @param imageView
     * @param type
     */
    public static void displayEspImage(String url, ImageView imageView){
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade(500)
                .placeholder(R.mipmap.img_default_meizi)
                .error(R.mipmap.img_default_meizi)
                .into(imageView);
    }


    public static void displayGif(String url, ImageView imageView){
        Glide.with(imageView.getContext()).load(url)
                .asBitmap()
                .placeholder(R.mipmap.img_one_bi_one)
                .error(R.mipmap.img_one_bi_one)
                .into(imageView);
    }

    /**
     * 随机显示图片
     * @param imgNumber
     * @param imageUrl
     */
    public static void displayRandom(int imgNumber, String imageUrl, ImageView imageView){
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(getMusicDefaultPic(imgNumber))
                .error(getMusicDefaultPic(imgNumber))
                .crossFade(1500)
                .into(imageView);
    }

    private static int getMusicDefaultPic(int imgNumber) {
        switch (imgNumber) {
            case 1:
                return R.mipmap.img_two_bi_one;
            case 2:
                return R.mipmap.img_four_bi_three;
            case 3:
                return R.mipmap.img_one_bi_one;
        }
        return R.mipmap.img_four_bi_three;
    }



}

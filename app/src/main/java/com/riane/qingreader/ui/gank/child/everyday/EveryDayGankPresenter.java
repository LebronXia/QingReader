package com.riane.qingreader.ui.gank.child.everyday;

import android.text.TextUtils;

import com.riane.qingreader.data.ReaderRepository;
import com.riane.qingreader.data.network.reponse.AndroidBean;
import com.riane.qingreader.data.network.reponse.GankIoDayBean;
import com.riane.qingreader.ui.base.BaseContract;
import com.riane.qingreader.ui.base.BasePresenter;
import com.riane.qingreader.util.ConstantsImageUrl;
import com.riane.qingreader.util.SPUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Riane on 2017/7/13.
 */

public class EveryDayGankPresenter extends BasePresenter implements EveryDayGankContract.Presenter{

    private ReaderRepository mReaderRepository;

    private EveryDayGankContract.View mEveryDayView;
    private static final String HOME_ONE = "home_one";
    private static final String HOME_TWO = "home_two";
    private static final String HOME_SIX = "home_six";

    @Inject
    EveryDayGankPresenter(ReaderRepository readerRepository, EveryDayGankContract.View everyDayView){
        mReaderRepository = readerRepository;
        mEveryDayView = everyDayView;
    }

    @Override
    public void getGankIoDay(int year, int month, int day) {
        Disposable subscribe = mReaderRepository.getGankIoDay(year, month, day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<GankIoDayBean, Observable<List<List<AndroidBean>>>>() {
                    @Override
                    public Observable<List<List<AndroidBean>>> apply(GankIoDayBean gankIoDayBean) throws Exception {

                        List<List<AndroidBean>> lists = new ArrayList<List<AndroidBean>>();
                        GankIoDayBean.ResultsBean resultsBean = gankIoDayBean.getResults();

                        if (resultsBean.getAndroid() != null && resultsBean.getAndroid().size() > 0){
                                addUrlList(lists, resultsBean.getAndroid(), "Android");
                        }
                        if (resultsBean.getWelfare() != null && resultsBean.getWelfare().size() > 0){
                            addUrlList(lists, resultsBean.getWelfare(), "福利");
                        }
                        if (resultsBean.getiOS() != null && resultsBean.getiOS().size() > 0){
                            addUrlList(lists, resultsBean.getiOS(), "iOS");
                        }
                        if (resultsBean.getRestMovie() != null && resultsBean.getRestMovie().size() > 0){
                            addUrlList(lists, resultsBean.getRestMovie(), "休息视频");
                        }
                        if (resultsBean.getResource() != null && resultsBean.getResource().size() > 0){
                            addUrlList(lists, resultsBean.getResource(), "拓展资源");
                        }
                        if (resultsBean.getRecommend() != null && resultsBean.getRecommend().size() > 0){
                            addUrlList(lists, resultsBean.getRecommend(), "瞎推荐");
                        }
                        if (resultsBean.getFront() != null && resultsBean.getFront().size() > 0){
                            addUrlList(lists, resultsBean.getFront(), "前端");
                        }
                        if (resultsBean.getApp() != null && resultsBean.getApp().size() > 0){
                            addUrlList(lists, resultsBean.getApp(), "App");
                        }
                        return Observable.just(lists);
                    }
                })
                .subscribe(new Consumer<List<List<AndroidBean>>>() {
                    @Override
                    public void accept(List<List<AndroidBean>> lists) throws Exception {
                        mEveryDayView.showGankEveryDayData(lists);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mEveryDayView.showError();
                    }
                });
        addSubscrebe(subscribe);
    }

    public void addUrlList(List<List<AndroidBean>> lists, List<AndroidBean> androidBeanList, String type){
        AndroidBean androidBean = new AndroidBean();

        androidBean.setType_title(type);
        ArrayList<AndroidBean> list = new ArrayList<>();
        list.add(androidBean);
        lists.add(list);

        int androidSize = androidBeanList.size();

        if (androidSize > 0 && androidSize < 4){

            lists.add(addUrlList(androidBeanList, androidSize));
        } else if (androidSize >= 4){

            ArrayList<AndroidBean> list1 = new ArrayList<>();
            ArrayList<AndroidBean> list2 = new ArrayList<>();

            for (int i = 0; i < androidSize; i++) {
                if (i < 3) {
                    //图片大于3张，给其创建存放图片的AndroidBean
                    list1.add(getAndroidBean(androidBeanList, i, androidSize));
                } else if (i < 6) {
                    //图片小于6张，给其创建存放图片的AndroidBean
                    list2.add(getAndroidBean(androidBeanList, i, androidSize));
                }
            }
            lists.add(list1);
            lists.add(list2);
        }
//        for (int i = 0; i < androidBeanList.size(); i++){
//            if (androidBeanList.size() < 3){
//                androidBean.setDesc(androidBeanList.get(i).getDesc());
//                androidBean.setUrl(androidBeanList.get(i).getUrl());
//
//            }
//
//        }
    }

    private AndroidBean getAndroidBean(List<AndroidBean> arrayList, int i, int androidSize) {

        AndroidBean androidBean = new AndroidBean();
        // 标题
        androidBean.setDesc(arrayList.get(i).getDesc());
        // 类型
        androidBean.setType(arrayList.get(i).getType());
        // 跳转链接
        androidBean.setUrl(arrayList.get(i).getUrl());
        // 随机图的url
        if (i < 3) {
            androidBean.setImage_url(ConstantsImageUrl.HOME_SIX_URLS[getRandom(3)]);//三小图
        } else if (androidSize == 4) {
            androidBean.setImage_url(ConstantsImageUrl.HOME_ONE_URLS[getRandom(1)]);//一图
        } else if (androidSize == 5) {
            androidBean.setImage_url(ConstantsImageUrl.HOME_TWO_URLS[getRandom(2)]);//两图
        } else if (androidSize >= 6) {
            androidBean.setImage_url(ConstantsImageUrl.HOME_SIX_URLS[getRandom(3)]);//三小图
        }
        return androidBean;
    }

    private List<AndroidBean> addUrlList(List<AndroidBean> arrayList, int androidSize) {
        List<AndroidBean> tempList = new ArrayList<>();
        for (int i = 0; i < androidSize; i++) {
            AndroidBean androidBean = new AndroidBean();
            // 标题
            androidBean.setDesc(arrayList.get(i).getDesc());
            // 类型
            androidBean.setType(arrayList.get(i).getType());
            // 跳转链接
            androidBean.setUrl(arrayList.get(i).getUrl());
//            DebugUtil.error("---androidSize:  " + androidSize);
            // 随机图的url
            if (androidSize == 1) {
                androidBean.setImage_url(ConstantsImageUrl.HOME_ONE_URLS[getRandom(1)]);//一图
            } else if (androidSize == 2) {
                androidBean.setImage_url(ConstantsImageUrl.HOME_TWO_URLS[getRandom(2)]);//两图
            } else if (androidSize == 3) {
                androidBean.setImage_url(ConstantsImageUrl.HOME_SIX_URLS[getRandom(3)]);//三图
            }
            tempList.add(androidBean);
        }
        return tempList;
    }

    /**
     * 取不同的随机图，在每次网络请求时重置
     */
    private int getRandom(int type) {
        String saveWhere = null;
        int urlLength = 0;
        if (type == 1) {
            saveWhere = HOME_ONE;
            urlLength = ConstantsImageUrl.HOME_ONE_URLS.length;
        } else if (type == 2) {
            saveWhere = HOME_TWO;
            urlLength = ConstantsImageUrl.HOME_TWO_URLS.length;
        } else if (type == 3) {
            saveWhere = HOME_SIX;
            urlLength = ConstantsImageUrl.HOME_SIX_URLS.length;
        }

        //获取存放的方式
        String home_six = SPUtils.getString(saveWhere, "");
        if (!TextUtils.isEmpty(home_six)) {
            // 已取到的值
            String[] split = home_six.split(",");
            //获得home_six的值
            Random random = new Random();
            for (int j = 0; j < urlLength; j++) {
                //随机取这个String[]里的图片
                int randomInt = random.nextInt(urlLength);
                //初始化isUser
                boolean isUse = false;

                for (String aSplit : split) {
                    //判断截取到的数据是否为空和随机到的图片是否使用过
                    if (!TextUtils.isEmpty(aSplit) && String.valueOf(randomInt).equals(aSplit)) {
                        isUse = true;
                        break;
                    }
                }
                if (!isUse) {
                    //没有使用过 就拼接起来
                    StringBuilder sb = new StringBuilder(home_six);
                    sb.insert(0, randomInt + ",");
                    SPUtils.putString(saveWhere, sb.toString());
                    return randomInt;
                }
            }

        } else {
            Random random = new Random();
            int randomInt = random.nextInt(urlLength);
            SPUtils.putString(saveWhere, randomInt + ",");
            return randomInt;
        }
        return 0;
    }

}

package com.riane.qingreader.data;

import com.riane.qingreader.data.local.DbHelper;
import com.riane.qingreader.data.network.ApiHelper;
import com.riane.qingreader.data.network.reponse.GankIoDataBean;
import com.riane.qingreader.data.network.reponse.GankIoDayBean;

import io.reactivex.Observable;

/**
 * Created by Riane on 2017/7/12.
 */

public interface ReaderDataSource extends DbHelper, ApiHelper{

}

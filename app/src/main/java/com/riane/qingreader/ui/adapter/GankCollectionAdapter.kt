package com.riane.qingreader.ui.adapter

import android.content.Context
import android.view.View
import com.riane.qingreader.R
import com.riane.qingreader.data.network.reponse.ResultBean
import com.riane.qingreader.ui.base.baseAdapter.CommonAdapter
import com.riane.qingreader.ui.base.baseAdapter.ViewHolder
import com.riane.qingreader.util.TimeUtil

/**
 * Created by xiaobozheng on 10/12/2017.
 */
class GankCollectionAdapter constructor(var context: Context, var layoutId: Int, var datas: List<ResultBean>): CommonAdapter<ResultBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder?, result: ResultBean?) {
        holder?.setText(R.id.tv_android_des, result?.desc)
        holder?.setText(R.id.tv_android_time, TimeUtil.getTranslateTime(result?.publishedAt))
        holder?.setText(R.id.tv_android_who, result?.who)
        holder?.getView<View>(R.id.iv_android_pic)?.visibility = View.GONE
    }
}
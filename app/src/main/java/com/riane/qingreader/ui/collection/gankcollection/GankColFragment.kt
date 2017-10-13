package com.riane.qingreader.ui.collection.gankcollection

import android.support.v7.widget.LinearLayoutManager
import butterknife.BindView
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.riane.qingreader.QingReaderApplication
import com.riane.qingreader.R
import com.riane.qingreader.data.network.reponse.ResultBean
import com.riane.qingreader.ui.adapter.GankCollectionAdapter
import com.riane.qingreader.ui.base.BaseFragment
import javax.inject.Inject

/**
 * Created by xiaobozheng on 10/12/2017.
 */
class GankColFragment: BaseFragment(), GankColContract.View{

    @BindView(R.id.xrv_gank_collection)
    lateinit var xrvGankCollection: XRecyclerView
    @Inject
    lateinit var mPresenter: GankColPresenter
    lateinit var mAdapter: GankCollectionAdapter
    var offset =  1
    var results: MutableList<ResultBean> = ArrayList()

    override fun getLayoutResId(): Int {
        return R.layout.fragment_gank_collection
    }

    override fun initInjector() {
        DaggerGankColComponent.builder()
                .gankColPresenterModule(GankColPresenterModule(this))
                .readerRepositoryComponent((activity.application as QingReaderApplication).readerRepositoryComponent)
                .build()
                .inject(this)
    }

    override fun initView() {

    }

    override fun refreshUI() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadData() {
        if (!isDataInitiated || !isViewInitiated || !mIsVisible) {
            return
        }
        loadGankCollectionData()
    }

    fun loadGankCollectionData(){
        mPresenter.getGankCollections(offset)
    }

    override fun initDatas() {
        stateLayout.showLoadingView()
        isViewInitiated = true
        mAdapter = GankCollectionAdapter(activity, R.layout.item_android, results)
        xrvGankCollection.adapter = mAdapter
        xrvGankCollection.layoutManager = LinearLayoutManager(activity)
        xrvGankCollection.setLoadingListener( object : XRecyclerView.LoadingListener{
            override fun onLoadMore() {
                offset ++
                loadGankCollectionData()
            }

            override fun onRefresh() {
                offset = 1
                loadGankCollectionData()
            }
        })
        loadGankCollectionData()
    }

    fun setAdapter( results: MutableList<ResultBean>) {
        mAdapter.clear()
        mAdapter.addAll(results)
        xrvGankCollection.refreshComplete()
        isDataInitiated = false
    }
    override fun showError() {
        //加载失败展示错误的view
        stateLayout.showErrorView()
    }

    override fun showGankCollections(gankCollections: MutableList<ResultBean>) {
        if (gankCollections.size <= 0) {
            stateLayout.showEmptyView()
        } else {
            stateLayout.showSuccessView()
            if (offset == 1) {
                if (gankCollections!= null && gankCollections.size > 0) {
                    //给RecycleVIew填充数据
                    setAdapter(gankCollections)
                }
            } else {
                if (gankCollections!= null && gankCollections.size > 0) {
                    xrvGankCollection.refreshComplete()
                    mAdapter.addAll(gankCollections)
                }
            }
        }
    }
}

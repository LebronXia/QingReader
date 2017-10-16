package com.riane.qingreader.ui.detail

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.ProgressBar
import butterknife.BindView
import com.riane.qingreader.QingReaderApplication
import com.riane.qingreader.R
import com.riane.qingreader.data.network.reponse.GankIoDataBean
import com.riane.qingreader.data.network.reponse.ResultBean
import com.riane.qingreader.ui.base.BaseActivity
import javax.inject.Inject


/**
 * Created by xiaobozheng on 10/9/2017.
 */
class DetailActivity : BaseActivity(),DetailContract.View{
    //http://blog.csdn.net/carson_ho/article/details/52693322
    //静态的写法
    companion object{
        public val INTENT_DETATL_RESULT= "detail_result"
    }

    @BindView(R.id.common_toolbar)
    lateinit var toolbar : Toolbar
//  @BindView(R.id.scrollview)
//  lateinit var svDetail : ScrollView
    @BindView(R.id.wv_detail)
    lateinit var wvDetail: WebView
    @BindView(R.id.pb_detail)
    lateinit  var pbDetail: ProgressBar
    lateinit var webUrl: String

    var result: GankIoDataBean.ResultBean? = null
    @Inject lateinit var mPresenter: DetailPresenter

    override fun getLayoutId(): Int {
        return R.layout.activity_detail
    }

    lateinit var detailId: String
    var isLike: Boolean = false

    override fun initInjector() {
        DaggerDetailComponent.builder()
                .detailPresenterModule(DetailPresenterModule(this))
                .readerRepositoryComponent((this.application as QingReaderApplication).readerRepositoryComponent)
                .build()
                .inject(this)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initViews() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        result = intent.extras.getSerializable(INTENT_DETATL_RESULT) as? GankIoDataBean.ResultBean
        webUrl = result?.url!!
        detailId = result?._id!!
        toolbar.inflateMenu(R.menu.menu_detail)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        pbDetail.visibility = View.VISIBLE
        initListener()
        initWebView()
       // mPresenter.queryIsLIke(detailId)
    }

    fun initListener(){
        toolbar.setOnMenuItemClickListener(object: Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item?.itemId){
                    R.id.item_like -> {
                        if(isLike){
                            mPresenter.cancel(detailId)
                            Snackbar.make(toolbar, "已取消收藏", Snackbar.LENGTH_SHORT).show()

                        } else {
                            val resultBean: ResultBean = ResultBean()
                            resultBean._id = result?._id
                            resultBean.createdAt = result?.createdAt
                            resultBean.desc = result?.desc
                            resultBean.publishedAt = result?.publishedAt
                            resultBean.source = result?.source
                            resultBean.type = result?.type
                            resultBean.url = result?.url
                            resultBean.used = result!!.isUsed
                            resultBean.who = result?.who
                            mPresenter.add(resultBean)
                            Snackbar.make(toolbar, "已收藏", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
                return false
            }

        })
    }

    fun initWebView(){
        val webSettings = wvDetail?.settings
        //webSettings.setUseWideViewPort(true)
        webSettings?.useWideViewPort = true
        //设置能够解析Javascript
        webSettings?.javaScriptEnabled = true
        webSettings?.loadWithOverviewMode = true
        webSettings?.builtInZoomControls = true
        webSettings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        //设置适应Html5的一些方法
        webSettings?.domStorageEnabled = true
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isAvailable) {
            webSettings?.cacheMode = WebSettings.LOAD_DEFAULT//网络正常时使用默认缓存策略
        } else {
            webSettings?.cacheMode = WebSettings.LOAD_CACHE_ONLY//网络不可用时只使用缓存
        }
        wvDetail?.setWebChromeClient(WebChromeClient())
        wvDetail?.setWebViewClient(object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                pbDetail.visibility = View.VISIBLE
                view?.loadUrl(webUrl)
                return true
            }
            //设置加载前函数
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                toolbar.title = "加载中"
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                pbDetail.visibility = View.GONE
                toolbar.title = result?.desc
            }
        })

        wvDetail.loadUrl(webUrl)
    }

    override fun initData() {
        mPresenter.queryIsLIke(detailId)
    }

    override fun refreshUI() {

    }

    override fun showLike() {
        toolbar.menu.getItem(0).setIcon(R.drawable.ic_star_black_24dp_red)
        isLike = true
    }

    override fun showUnLike() {
        toolbar.menu.getItem(0).setIcon(R.drawable.ic_star_black_24dp)
        isLike = false
    }

    override fun showError() {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onDestroy() {
        if (wvDetail != null){
            wvDetail?.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            wvDetail?.clearHistory()

            (wvDetail?.parent as ViewGroup).removeView(wvDetail)
            wvDetail?.destroy()
            //wvDetail = null
        }
        super.onDestroy()
    }
}

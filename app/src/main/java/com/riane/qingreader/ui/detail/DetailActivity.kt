package com.riane.qingreader.ui.detail

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.Toolbar
import android.webkit.*
import android.widget.ScrollView
import butterknife.BindView
import com.riane.qingreader.R
import com.riane.qingreader.data.network.reponse.GankIoDataBean
import com.riane.qingreader.ui.base.BaseActivity


/**
 * Created by xiaobozheng on 10/9/2017.
 */
class DetailActivity : BaseActivity(){
    //http://blog.csdn.net/carson_ho/article/details/52693322
    //静态的写法
    companion object{
        public val INTENT_DETATL_RESULT= "detail_result"
    }

    @BindView(R.id.common_toolbar)
    lateinit var toolbar : Toolbar
    @BindView(R.id.scrollview)
    lateinit var svDetail : ScrollView
    @BindView(R.id.wv_detail)
    lateinit var wvDetail: WebView
    lateinit var webUrl: String


    var result: GankIoDataBean.ResultBean? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_detail
    }

    override fun initInjector() {

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
        toolbar.title = "aaaa"

        result = intent.extras.getSerializable(INTENT_DETATL_RESULT) as? GankIoDataBean.ResultBean
        webUrl = result?.url!!

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        initWebView()
    }

    fun initWebView(){

        val webSettings = wvDetail.settings
        //webSettings.setUseWideViewPort(true)
        webSettings.useWideViewPort = true
        //设置能够解析Javascript
        webSettings.javaScriptEnabled = true
        webSettings.loadWithOverviewMode = true
        webSettings.builtInZoomControls = true
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        //设置适应Html5的一些方法
        webSettings.domStorageEnabled = true
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isAvailable) {
            webSettings.cacheMode = WebSettings.LOAD_DEFAULT//网络正常时使用默认缓存策略
        } else {
            webSettings.cacheMode = WebSettings.LOAD_CACHE_ONLY//网络不可用时只使用缓存
        }
        wvDetail.setWebChromeClient(WebChromeClient())
        wvDetail.setWebViewClient(object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(webUrl)
                return true
            }
            //设置加载前函数
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        })

        wvDetail.addJavascriptInterface(this, "App")
        wvDetail.loadUrl("https://www.baidu.com/")
    }

    override fun initData() {

    }
}

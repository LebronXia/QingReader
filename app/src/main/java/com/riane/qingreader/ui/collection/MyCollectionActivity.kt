package com.riane.qingreader.ui.collection

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import butterknife.BindView
import com.riane.qingreader.R
import com.riane.qingreader.ui.base.BaseActivity
import com.riane.qingreader.ui.collection.gankcollection.GankColFragment
import com.riane.qingreader.ui.movie.Top250Movie.Top250Fragment

class MyCollectionActivity : BaseActivity() {

    @BindView(R.id.common_toolbar)
    lateinit var toolbar : Toolbar
    @BindView(R.id.tl_collectionlist)
    lateinit var tlCollectionList:TabLayout
    @BindView(R.id.vp_collectionlist)
    lateinit var vpCollectionlist: ViewPager

    lateinit var mTitleList: MutableList<String>
    lateinit var mFragments: MutableList<Fragment>

    override fun getLayoutId(): Int {
        return R.layout.activity_my_collection
    }

    override fun initInjector() {

    }

    override fun initViews() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setTitle("我的收藏")
        toolbar.setNavigationOnClickListener {
            finish()
        }
        initFragmentList()
        vpCollectionlist.adapter = object : FragmentPagerAdapter(supportFragmentManager){
            override fun getItem(position: Int): Fragment = mFragments[position]

            override fun getCount(): Int = mFragments.size

            override fun getPageTitle(position: Int): CharSequence = mTitleList[position]
        }
        vpCollectionlist.adapter.notifyDataSetChanged()
        vpCollectionlist.offscreenPageLimit = 2
        vpCollectionlist.currentItem = 0

        tlCollectionList.tabMode =TabLayout.MODE_FIXED
        tlCollectionList.setupWithViewPager(vpCollectionlist)
    }

    fun initFragmentList(){
        mTitleList = ArrayList()
        mFragments = ArrayList()

        mTitleList.add("技术")
        mTitleList.add("书籍")

        mFragments.add(GankColFragment())
        mFragments.add(Top250Fragment())
    }

    override fun initData() {
    }
}

package com.gaga.gwanandroid

import com.alibaba.android.arouter.facade.annotation.Route
import com.gaga.lib_core.constant.ARouterConfig
import com.gaga.lib_core.utils.showFragment
import com.gaga.lib_mvvm.mvvm.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = ARouterConfig.Url_MainActivity)
class MainActivity : BaseActivity(R.layout.activity_main) {
    //    private val fragmentList = arrayListOf<Fragment>()
//    private val homeFragment by lazy { HomeFragment() }
//    private val meFragment by lazy { MeFragment() }
//    init {
//        fragmentList.run {
//            add(homeFragment)
//            add(meFragment)
//        }
//    }

    override fun initView() {
        showFragment(R.id.fm_seekPile)
    }

    override fun initListener() {
        super.initListener()
        rgNavigation.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {

                R.id.rbSeekPile -> showFragment(R.id.fm_seekPile)

//                R.id.rbDiscover -> showFragment(R.id.fm_discover)
//
//                R.id.rbCharge -> showFragment(R.id.fm_charge)
//
//                R.id.rbOrders -> showFragment(R.id.fm_orders)

                R.id.rbMine -> showFragment(R.id.fm_mine)
            }
        }
    }

}
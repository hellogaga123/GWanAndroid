package com.gaga.gwanandroid.splash

import com.alibaba.android.arouter.launcher.ARouter
import com.gaga.gwanandroid.R
import com.gaga.lib_core.constant.ARouterConfig
import com.gaga.lib_core.constant.Config
import com.gaga.lib_core.utils.MMKVUtil
import com.gaga.lib_mvvm.mvvm.BaseActivity

/**
 * @Author Gaga
 * @Date 2020/8/19 16:49
 * @Description
 */
class SplashActivity : BaseActivity(R.layout.activity_splash) {
    override fun initView() {
        ARouter.getInstance().build(ARouterConfig.Url_MainActivity).navigation()
//        when (MMKVUtil.decodeBoolean(Config.IS_LOGIN)) {
//            true -> ARouter.getInstance().build(ARouterConfig.Url_MainActivity).navigation()
//            false -> ARouter.getInstance().build(ARouterConfig.Url_LoginActivity).navigation()
//        }
    }
}
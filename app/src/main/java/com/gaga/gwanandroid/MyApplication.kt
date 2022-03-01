package com.gaga.gwanandroid

import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.gaga.lib_core.bean.UserCache
import com.gaga.lib_core.constant.Config
import com.gaga.lib_core.utils.MMKVUtil
import com.gaga.lib_mvvm.BaseApplication
import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


/**
 * @Author Gaga
 * @Date 2020/8/17 10:47
 * @Description MyApplication
 */
class MyApplication : BaseApplication() {

    companion object {
//        private var isDebugARouter: Boolean = false
        //        lateinit var CURRENT_USER: User

    }

    override fun onCreate() {
        super.onCreate()

        //初始化BuildConfig
        initBuildConfig()
        //开启Koin，这里需要将所有需要注解生成的对象添加进来
        initKoin()
        //初始化Arouter
        initARouter()
        //初始化MMKV
        initMMKV()

        val userJson = MMKVUtil.decodeString(Config.USER)
        Gson().fromJson(userJson, UserCache.javaClass)

    }

    private fun initMMKV() {
        MMKV.initialize(this)
    }

    private fun initBuildConfig() {
        Config.baseUrl = BuildConfig.BASE_URL
//        APIConfig.baseUrl = ApiService.BASE_URL
        Config.flavor = BuildConfig.FLAVOR
        Config.referUrl = BuildConfig.REFER_URL
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    private fun initKoin() {
        startKoin {
            //给Koin框架添加ApplicationContext
            androidContext(this@MyApplication)
            /**
            这里设置Koin的日志打印
            Koin提供了三种实现:
            AndroidLogger:使用Android的Log.e/i/d()打印日志
            PrintLogger:使用System.err/out打印日志
            EmptyLogger:不打印日志，默认就是该实现
             */
            androidLogger(Level.NONE)
            /**
            设置Koin配置文件，需要放在assets文件夹中
            默认名称为：koin.propreties
            可以快速获取配置文件中的内容，文件名可以修改，但是需要在这里保持一致
            [getKoin().getProperty<String>("name")]
             */
//            androidFileProperties("koin.properties")
            modules(appModule)
        }
    }
}
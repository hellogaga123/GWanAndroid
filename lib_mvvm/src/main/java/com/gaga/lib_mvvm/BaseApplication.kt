package com.gaga.lib_mvvm

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

/**
 * @Author Gaga
 * @Date 2020/8/14 16:16
 * @Description
 */
open class BaseApplication : Application() {

    companion object {
        var CONTEXT: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
    }
}
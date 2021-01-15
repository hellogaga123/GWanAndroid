package com.gaga.lib_core.constant

import android.os.Build
import com.gaga.lib_core.utils.DeviceUtil

/**
 * @Author Gaga
 * @Date 2020/8/14 16:11
 * @Description
 */
object Config {
    lateinit var baseUrl: String
    lateinit var flavor: String
    lateinit var referUrl: String
    const val apiKey = "2A824A47C39D48F3A1D6D7AE455FCC16" //秘钥
    const val corpCode = "911111111" //企业编码

    /**
     * 请求头AGENT
     */
    val AGENT = ("ecw/"
            + "android/"
            + "1.1.3"
            + "("
            + Build.MANUFACTURER
            + ";"
            + Build.MODEL
            + ";"
            + "os"
            + Build.VERSION.RELEASE
            + ";"
            + DeviceUtil.cpuType
            + ";"
            + "1.1.3"
            + ")".replace(" ".toRegex(), ""))

    const val USER_INFO_CACHE = "userInfoCache"
//    val IS_LOGIN:String="is_login"
    /**
     * sp中的信息字段委托给Preference,属性名就是存储在sp中key值，
     */
    const val IS_LOGIN="is_login"
    const val TOKEN="token"
    const val USER="user"

    //Preferences 的fileName
    const val PreferencesName = "GWanAndroid"
}
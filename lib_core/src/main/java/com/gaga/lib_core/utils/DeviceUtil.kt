package com.gaga.lib_core.utils

import android.os.Build
import android.provider.Settings
import com.gaga.lib_mvvm.BaseApplication
import java.io.IOException
import java.io.RandomAccessFile

/**
 * @package:com.zcsy.business.network.utils
 * @author:zcsy
 * @date:2017-11-27 14:24
 * @description:
 */
object DeviceUtil {
    val cpuString: String
        get() {
            if (Build.CPU_ABI.equals("x86", ignoreCase = true)) {
                return "Intel"
            }
            var strInfo = ""
            var reader: RandomAccessFile? = null
            try {
                val bs = ByteArray(1024)
                reader = RandomAccessFile("/proc/cpuinfo", "r")
                reader.read(bs)
                val ret = String(bs)
                val index = ret.indexOf(0.toChar())
                strInfo = if (index != -1) {
                    ret.substring(0, index)
                } else {
                    ret
                }
                reader.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            return strInfo
        }

    val cpuType: String
        get() {
            val strInfo = cpuString
            var strType: String? = null
            strType = if (strInfo.contains("ARMv5") || strInfo.contains("v5l")) {
                "armv5"
            } else if (strInfo.contains("ARMv6") || strInfo.contains("v6l")) {
                "armv6"
            } else if (strInfo.contains("ARMv7")) {
                "armv7"
            } else if (strInfo.contains("Intel")) {
                "x86"
            } else {
                "unknown"
            }
            return strType
        }
    /**
     * 获取AndroidId
     */
    open fun getAndroidId(): String? {
        val androidID = Settings.Secure.getString(
            BaseApplication.CONTEXT.getContentResolver(),
            Settings.Secure.ANDROID_ID
        )
        val id = androidID + Build.SERIAL
//        LogUtil.d("Android id :$id")
        return id
    }
}
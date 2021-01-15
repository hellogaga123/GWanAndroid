package com.gaga.lib_core.utils

import android.content.Context
import android.util.Log
import com.gaga.lib_core.constant.PreferenceDelegates
import com.gaga.lib_mvvm.BaseApplication
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @Author Gaga
 * @Date 2020/11/11 16:20
 * @Description
 * @param T
 * @property preferenceName String  SP的name
 * @property default T      默认值
 * @property key String     key的值，如果没有传key，默认使用常量值作为key值
 * @property prefs (android.content.SharedPreferences..android.content.SharedPreferences?)
 * @constructor
 */

class Preference<T>(
    private val preferenceName: String,
    private val default: T,
    private val key: String = ""
) :
    ReadWriteProperty<Any?, T> {
    fun test() {}
    fun remove(filed: T, preferenceName: String, key: String) {
        BaseApplication.CONTEXT.getSharedPreferences(
            preferenceName,
            Context.MODE_PRIVATE
        )
            .edit()
            .remove(key)
            .apply()
    }

    companion object {
        //        val USER_INFO_CACHE = PreferenceName("userInfoCache")//用于存储用户信息
        /**
         * 清除SP
         * @param preferenceName String SP的name
         */
        private fun clear(preferenceName: String) {
            BaseApplication.CONTEXT.getSharedPreferences(
                preferenceName,
                Context.MODE_PRIVATE
            )
                .edit()
                .clear()
                .apply()
        }

        /**
         * 删除某Key的值
         * @param preferenceName String SP的name
         */
        fun <T> remove(key: T, preferenceName: String) {

            val ss by PreferenceDelegates()
            Log.e("3333333", "remove: $propertyName" )
//            BaseApplication.CONTEXT.getSharedPreferences(
//                preferenceName,
//                Context.MODE_PRIVATE
//            )
//                .edit()
//                .remove(propertyName)
//                .apply()
        }

        /**
         * 清除
         * @param preferenceName String sp的Name
         */
        operator fun div(preferenceName: String) {
            clear(preferenceName)
        }

        /**
         *
         * @param key String
         */
        operator fun minus(key: String) {

        }

        private lateinit var propertyName: String

    }

//    class SpName(val spFileName: String)


    private val prefs by lazy {
        BaseApplication.CONTEXT.getSharedPreferences(
            preferenceName,
            Context.MODE_PRIVATE
        )
    }


    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        propertyName = property.name
        return findPreference((if (key == "") property.name else key), default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference((if (key == "") property.name else key), value)
    }

    //读取 preferences
    private fun <T> findPreference(key: String, default: T): T = with(prefs) {
        val res: Any? = when (default) {
            is Long -> getLong(key, default)
            is String -> getString(key, default)
            is Int -> getInt(key, default)
            is Boolean -> getBoolean(key, default)
            is Float -> getFloat(key, default)
            else -> throw IllegalArgumentException(
                "This type can't be saved into Preferences"
            )
        }
        res as T
    }

    //写入 preferences
    private fun <U> putPreference(key: String, value: U) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            else -> throw IllegalArgumentException("This type can't be saved into Preferences")
        }.apply()
    }
}
//object PreferenceDelegates {
//    fun <T : Any> preference(context: Context, name: String, default: T) {Preference(context, name, default)}
//}
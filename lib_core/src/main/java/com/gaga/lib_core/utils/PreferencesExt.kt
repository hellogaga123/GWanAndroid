package com.gaga.lib_core.utils

import android.content.Context
import android.content.SharedPreferences
import com.gaga.lib_mvvm.BaseApplication
import com.google.gson.Gson
import kotlin.reflect.KProperty
/**
 * @Author Gaga
 * @Date 2020/11/11 10:02
 * @Description
 */
/**
 * effect : 存储sp文件的名字,写的时候加上用途
 * warning: 一个文件不要存储过多内容,影响效率
 */
object SPFileName {
    @JvmField
    val USER_INFO_CACHE = SpName("userInfoCache")//用于存储用户信息
}

class SpName(val value: String)

/**
 * 属性委托类,可以更方便的使用
 * 只可以获取基本类型
 * 如果key传空字符串,则使用自身的名字为key
 */
class SP<T>(val spName: SpName, private val defaultValue: T, private val key: String = "") {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T =
        spName.readSP(if (key == "") property.name else key, defaultValue)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Any) =
        spName.writeSP((if (key == "") property.name else key) / value)
}
//class SP<T>(private val spName: SpName, private val defaultValue: T, private val key: String = "") {
//    operator fun getValue(thisRef: Any?, property: KProperty<*>): T =
//        spName.readSP(if (key == "") property.name else key, defaultValue)
//
//    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
//        spName.writeSP((if (key == "") property.name else key) / value)
//}

//只可以委托对象
class SPAny<T>(val spName: SpName, val key: String = "") {
    inline operator fun <reified T> getValue(thisRef: Any?, property: KProperty<*>): T? =
        spName.readSPOfAny(if (key == "") property.name else key)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) =
        spName.writeSP((if (key == "") property.name else key) / value)
}
/**
 * creator: lt  2019/7/13--15:40    lt.dygzs@qq.com
 * effect : SharedPreferences工具类
 * warning:
 */

/**
 * 往sp中写入数据,this为fileName
 */
fun SpName.writeSP(bean: Pair<String, *>): SpName {
    BaseApplication.CONTEXT
        .getSharedPreferences(this.value, Context.MODE_PRIVATE)
        .edit()
        .put(bean)
        .apply()
    return this
}

fun SpName.writeSPs(vararg beans: Pair<String, *>): SpName {
    if (beans.isEmpty()) {
        throw RuntimeException("writeSPs没有填写可变参数")
    }
    val editor =
        BaseApplication.CONTEXT.getSharedPreferences(this.value, Context.MODE_PRIVATE).edit()
    beans.map(editor::put)
    editor.apply()
    return this
}

//处理存储类型
private fun SharedPreferences.Editor.put(bean: Pair<String, *>): SharedPreferences.Editor {
    when (bean.second) {
        is Boolean -> this.putBoolean(bean.first, bean.second as Boolean)
        is Int -> this.putInt(bean.first, bean.second as Int)
        is Long -> this.putLong(bean.first, bean.second as Long)
        is Float -> this.putFloat(bean.first, bean.second as Float)
        is String -> this.putString(bean.first, bean.second as String)
        is Number -> this.putString(bean.first, bean.second.toString())
        else -> this.putString(
            bean.first,
            Gson().toJson(bean.second)
        )//toJson()是利用Gosn或者fastJson把对象变为json数据
    }
    return this
}

/**
 * 从sp中读取数据,this为fileName
 * 注意读取数值和boolean的时候最好给个默认值
 */
 fun <T> SpName.readSP(key: String, defaultValue: T): T {
    val preference = BaseApplication.CONTEXT.getSharedPreferences(this.value, Context.MODE_PRIVATE)
    val result: Any? = when (defaultValue) {
        is Boolean -> preference.getBoolean(key, defaultValue)
        is Int -> preference.getInt(key, defaultValue)
        is Long -> preference.getLong(key, defaultValue)
        is Float -> preference.getFloat(key, defaultValue)
        is String -> preference.getString(key, defaultValue)
        is Byte -> preference.getString(key, defaultValue.toString())?.toByte()
        is Short -> preference.getString(key, defaultValue.toString())?.toShort()
        is Double -> preference.getString(key, defaultValue.toString())?.toDouble()
//        is UByte -> preference.getString(key, defaultValue.toString())?.toUByte()
//        is UShort -> preference.getString(key, defaultValue.toString())?.toUShort()
//        is UInt -> preference.getString(key, defaultValue.toString())?.toUInt()
//        is ULong -> preference.getString(key, defaultValue.toString())?.toULong()
        else -> throw RuntimeException("如果读取对象,请使用string类型的默认值,或者使用内联的readSPOfAny方法")
    }
    return result as T
}

/**
 * 从sp中读取对象,this为fileName
 * 注意,如果json读不到则会返回null对象
 */
inline fun <reified T> SpName.readSPOfAny(key: String): T? {
    if (T::class.java == String::class.java
        || T::class.java == Boolean::class.java
        || T::class.java == Number::class.java
    )
        throw RuntimeException("读取sp中的数据时,类型不正确,readSPOfAny方法只能用来读取对象")
//    return this.readSP(key, "").json2Any()
    return Gson().fromJson(this.readSP(key, ""), T::class.java)
}

/**
 * 同时读取多条数据
 * 注意:需要读取同一种类型,若读取不同类型需要分开读取
 *      传入的Bean的key为key value为默认值
 */
inline fun <reified T> SpName.readSPs(vararg beans: Pair<String, T>): Array<T> {
    if (beans.isEmpty()) {
        throw RuntimeException("readSPs没有填写可变参数")
    }
    beans.map {

    }
    return Array(beans.size) {
        this@readSPs.readSP(beans[it].first, beans[it].second)
    }
}

/**
 * 清空该sp文件中的所有内容
 */
fun SpName.clearSP(): SpName {
    BaseApplication.CONTEXT
        .getSharedPreferences(this.value, Context.MODE_PRIVATE)
        .edit()
        .clear()
        .apply()
    return this
}

/**
 * 移除相应key的数据
 */
fun SpName.removeSP(vararg keys: String): SpName {
    if (keys.isEmpty()) {
        throw RuntimeException("removeSP没有填写可变参数")
    }
    val edit = BaseApplication.CONTEXT
        .getSharedPreferences(this.value, Context.MODE_PRIVATE)
        .edit()
    keys.map(edit::remove)
    edit.apply()
    return this
}

/**
 * 快捷创建一个s - t 的Pair 使用方式:s/t
 */
operator fun <T> String.div(value: T): Pair<String, T> = Pair(this, value)
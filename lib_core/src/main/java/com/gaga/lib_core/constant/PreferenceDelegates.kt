package com.gaga.lib_core.constant

import kotlin.reflect.KProperty

/**
 * @Author Gaga
 * @Date 2020/11/11 16:35
 * @Description
 */
class PreferenceDelegates {
    operator fun getValue(thisRef: Any?, property: KProperty<*>):String {
        return property.name
    }
}
package com.gaga.lib_mvvm.mvvm

/**
 * @Author Gaga
 * @Date 2020/8/10 16:05
 * @Description
 */
data class BaseResponse<out T>(val ret: Int, val msg: String, val data: T)
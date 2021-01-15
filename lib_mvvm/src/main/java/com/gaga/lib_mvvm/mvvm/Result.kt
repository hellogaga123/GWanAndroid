package com.gaga.lib_mvvm.mvvm

import java.lang.Exception

/**
 * @Author Gaga
 * @Date 2020/8/10 16:06
 * @Description
 */
sealed class Result<out T : Any?> {
    data class Success<out T : Any?>(val data: T) : Result<T>()
    data class Error(val e: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[e=$e]"
        }
    }
}
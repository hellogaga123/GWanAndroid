package com.gaga.lib_mvvm.mvvm

/**
 * @Author Gaga
 * @Date 2020/8/14 11:45
 * @Description
 */
inline fun <T : Any> Result<T>.checkResult(success: (T) -> Unit, error: (String?) -> Unit) {
    when (this) {
        is Result.Success -> success(data)
        is Result.Error -> error(e.message)
    }
}

inline fun <T : Any> Result<T>.checkSuccess(success: (T) -> Unit) {
    when (this) {
        is Result.Success -> success(data)
    }
}
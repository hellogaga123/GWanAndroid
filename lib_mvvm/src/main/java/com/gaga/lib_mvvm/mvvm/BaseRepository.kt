package com.gaga.lib_mvvm.mvvm

import com.gaga.lib_mvvm.utils.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import java.io.EOFException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

/**
 * @Author Gaga
 * @Date 2020/8/10 16:00
 * @Description
 */
open class BaseRepository() {
    suspend fun <T : Any> apiCall(call: suspend () -> BaseResponse<T>): BaseResponse<T> {
        return call.invoke()
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>): Result<T> {
        return try {
            call()
        } catch (e: Exception) {
            // An exception was thrown when calling the API so we're converting this to an IOException
            errorHandler(e)
        }
    }

    private fun errorHandler(e: Exception): Result.Error {
        LogUtils.e(e.toString())
        return when (e) {
            is HttpException -> {
                Result.Error(IOException(e.message(), e))
            }
            is UnknownHostException -> {
                Result.Error(IOException("无法连接到服务器", e))
            }
            is SocketTimeoutException -> {
                Result.Error(IOException("链接超时", e))
            }
            is ConnectException -> {
                Result.Error(IOException("链接失败", e))
            }
            is SocketException -> {
                Result.Error(IOException("链接关闭", e))
            }
            is EOFException -> {
                Result.Error(IOException("链接关闭", e))
            }
            is IllegalArgumentException -> {
                Result.Error(IOException("参数错误", e))
            }
            is SSLException -> {
                Result.Error(IOException("证书错误", e))
            }
            is NullPointerException -> {
                Result.Error(IOException("数据为空", e))
            }
            else -> {
                Result.Error(IOException("未知错误", e))
            }

        }
    }

    suspend fun <T : Any> executeResponse(
        response: BaseResponse<T>,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): Result<T> {
        return coroutineScope {
            if (response.ret == 0) {
                successBlock?.let {
                    it() }
                Result.Success(response.data)
            } else {
                errorBlock?.let { it() }
                Result.Error(IOException(response.msg))
            }
        }

    }
}
package com.gaga.lib_core.netWorks

import com.gaga.lib_core.constant.Config.apiKey
import com.gaga.lib_mvvm.utils.LogUtils
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.nio.charset.Charset


/**
 * @Author Gaga
 * @Date 2020/6/29 15:22
 * @Description 解密拦截器
 */
class ResponseDecryptInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)

        if (response.isSuccessful) {
            val responseBody = response.body
            if (responseBody != null) {
                /*开始解密*/
                try {
                    val source = responseBody.source()
                    source.request(java.lang.Long.MAX_VALUE)
                    val buffer = source.buffer()
                    var charset = Charset.forName("UTF-8")
                    val contentType = responseBody.contentType()
                    if (contentType != null) {
                        charset = contentType.charset(charset)
                    }
                    val bodyString = buffer.clone().readString(charset)

//                    val oldResponseBody = response.body
//                    val oldResponseBodyStr = oldResponseBody!!.string()
                    val responseData = EncryptUtil.decrypt(bodyString, apiKey)
                    /*将解密后的明文返回*/
                    val newResponseBody = responseData.trim().toResponseBody(contentType)
//                    Log.e("111111111","解密====》${responseData}")
                    LogUtils.e("解密====》${request.url} ---- $responseData")
                    response = response.newBuilder().body(newResponseBody).build()
                } catch (e: Exception) {
                    /*异常说明解密失败 信息被篡改 直接返回即可 */
                    LogUtils.e("解密异常====》${e}")
                    return response
                }
            } else {
                LogUtils.e("解密异常====》响应体为空")
            }
        }
        return response

    }

}
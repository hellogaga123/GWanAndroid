package com.gaga.lib_core.netWorks

import com.gaga.lib_core.constant.Config.apiKey
import com.gaga.lib_core.constant.Config.corpCode
import com.gaga.lib_mvvm.utils.LogUtils
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.HashMap

/**
 * @Author Gaga
 * @Date 2020/6/29 15:39
 * @Description 加密拦截器 并添加公共参数
 */
class RequestEncryptInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var charset = Charset.forName("UTF-8")
        val method = request.method.toLowerCase().trim()

        val url = request.url
        /*本次请求的接口地址*/
        val apiPath = "${url.scheme}://${url.host}:${url.port}${url.encodedPath}".trim()
        /*服务端的接口地址*/
        val serverPath = "${url.scheme}://${url.host}/".trim()
        /*如果请求的不是服务端的接口，不加密*/
//        if (!serverPath.startsWith(ServerConstants.getServerUrl())) {
//            return chain.proceed(request)
//        }

        /*如果请求方式是Get或者Delete，此时请求数据是拼接在请求地址后面的*/
        if (method == "get" || method == "delete") {

//            /*如果有请求数据 则加密*/
//            if (url.encodedQuery != null) {
//                try {
//                    val queryparamNames = request.url.encodedQuery
//                    val encryptqueryparamNames =“这里调用加密的方法，自行修改”
//                    //拼接加密后的url，参数字段自己跟后台商量，这里我用param，后台拿到数据先对param进行解密，解密后的数据就是请求的数据
//                    val newUrl = "$apiPath?param=$encryptqueryparamNames"
//                    //构建新的请求
//                    request = request.newBuilder().url(newUrl).build()
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    return chain.proceed(request)
//                }
//            }
        } else {
            //不是Get和Delete请求时，则请求数据在请求体中
            val requestBody = request.body
            /*判断请求体是否为空  不为空则执行以下操作*/
            if (requestBody != null) {
                val contentType = requestBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(charset)
                    /*如果是二进制上传  则不进行加密*/
                    if (contentType.type.toLowerCase(Locale.getDefault()) == "multipart") {
                        return chain.proceed(request)
                    }
                }
                /*获取请求的数据*/
                try {
                    var rootMap: HashMap<String, Any> = HashMap()
                    val reqTime = System.currentTimeMillis()
                    var newJsonParams = ""
                    if (requestBody is FormBody && requestBody.size != 0) {
                        for (i in 0 until requestBody.size) {
                            rootMap[requestBody.encodedName(i)] = requestBody.encodedValue(i)
                        }

                    } else {
                        //buffer流
                        val buffer = Buffer()
                        requestBody.writeTo(buffer)
                        val requestData = buffer.readUtf8()
                        if (requestData.isNotEmpty()) {
                            rootMap = Gson().fromJson<HashMap<String, Any>>(
                                requestData,
                                HashMap::class.java
                            )
                        }
                    }
//                    if (!rootMap.isNullOrEmpty()) {
                        //原始参数
                        newJsonParams = Gson().toJson(rootMap)
                        val encp = EncryptUtil.encrypt(newJsonParams, apiKey)
                        val istr = apiKey + corpCode + encp + reqTime
                        val encryptData = EncryptUtil.MD5(istr)
                        /*构建新的请求体*/
//                    val newRequestBody = encryptData.toRequestBody(contentType)
                        val newRequestBody = FormBody.Builder()
                            .add("corpCode", corpCode)
                            .add("reqTime", reqTime.toString())
                            .add("params", encp)
                            .add("reqSign", encryptData).build()
                        /*构建新的requestBuilder*/
//                    val builder = request.url.newBuilder()
                        val newRequestBuilder = request.newBuilder()
                        //根据请求方式构建相应的请求
//                    var request=null
                        when (method) {
                            "post" -> request = newRequestBuilder.post(newRequestBody).build()
                            "put" -> request = newRequestBuilder.put(newRequestBody).build()
                        }
//                    request = request.newBuilder()
//                        .method(request.method, null)
//                        .url(builder.build())
//                        .build()
//                    }
                } catch (e: Exception) {
                    LogUtils.e("加密异常====》${e}")
                    return chain.proceed(request)
                }
            }
        }
        return chain.proceed(request)
    }

}
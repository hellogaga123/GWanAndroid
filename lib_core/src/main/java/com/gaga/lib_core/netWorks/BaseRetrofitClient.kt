package com.gaga.lib_core.netWorks

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @Author Gaga
 * @Date 2020/11/5 18:11
 * @Description
 */
abstract class BaseRetrofitClient {
    private val client: OkHttpClient
        get() {
            val builder= OkHttpClient.Builder()
            handleBuilder(builder)
            return builder.build()
        }

    abstract fun handleBuilder(builder: OkHttpClient.Builder)

    fun <S> getService(apiService: Class<S>,baseUrl:String):S{
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(apiService)
    }
}
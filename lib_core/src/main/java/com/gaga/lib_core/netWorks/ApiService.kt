package com.gaga.lib_core.netWorks

import com.gaga.lib_core.bean.PileBean
import com.gaga.lib_core.bean.User
import com.gaga.lib_mvvm.mvvm.BaseResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @Author Gaga
 * @Date 2020/11/5 18:08
 * @Description
 */
interface ApiService {
    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    @FormUrlEncoded
    @POST("common/login")
    suspend fun login(
        @Field("mobile") userName: String,
        @Field("passwd") passWord: String
    ): BaseResponse<User>

    //    @FormUrlEncoded
    @POST("access/getUserInfo")
    suspend fun loadUserInfo(): BaseResponse<User>

    @FormUrlEncoded
    @POST("common/siteList")
    suspend fun loadPile(
        @Field("pageSize") pageSize: String,
        @Field("lat") lat: String,
        @Field("lon") lon: String,
        @Field("city") city: String
    ): BaseResponse<PileBean>
}
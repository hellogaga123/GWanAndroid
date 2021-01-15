package com.gaga.lib_core.netWorks

import android.util.Log
import com.gaga.lib_core.constant.Config
import com.gaga.lib_core.utils.DeviceUtil
import com.gaga.lib_core.utils.MMKVUtil
import com.gaga.lib_mvvm.BaseApplication
import com.gaga.lib_mvvm.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @Author Gaga
 * @Date 2020/11/5 18:10
 * @Description
 */
object RetrofitClient : BaseRetrofitClient() {
//    val service by lazy { getService(ApiService::class.java, Config.baseUrl) }

    //    private val cookieJar by lazy { PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.CONTEXT)) }
    //    private static final int READ_TIMEOUT = 10;//读取超时时间,单位秒
    //    private static final int CONN_TIMEOUT = 10;//连接超时时间,单位秒
    private const val TIME_OUT = 5 //超时时间,单位秒


    override fun handleBuilder(builder: OkHttpClient.Builder) {

        val httpCacheDirectory = File(BaseApplication.CONTEXT.cacheDir, "responses")
        val cacheSize = 10 * 1024 * 1024L // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize)


        val ssl =
            MySSLSocketFactory

        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.BASIC
        }
        builder
            .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
//            .cache(cache)
//            .cookieJar(cookieJar)
            .addInterceptor { chain ->
                var request = chain.request()
//                val token= UserCache.authInfo?.token
                val token = MMKVUtil.decodeString(Config.TOKEN)
                val cookie = "did=" + DeviceUtil.getAndroidId() + ";token=" + token + ";cst=1"
                Log.e("3333333", "token: $token")
//                if (!NetWorkUtils.isNetworkAvailable(BaseApplication.CONTEXT)) {
                request = request.newBuilder()
//                        .cacheControl(CacheControl.FORCE_CACHE) //  FORCE_CACHE: 仅仅使用缓存; FORCE_NETWORK: 仅仅使用网络
//                        .addHeader("Content-Type", "application/json")
                    .addHeader("Cookie", cookie)
                    .addHeader("Referer", Config.referUrl)
                    .addHeader("User-Agent", Config.AGENT)
                    .build()
//                }
                val response = chain.proceed(request)
//                if (!NetWorkUtils.isNetworkAvailable(BaseApplication.CONTEXT)) {
//                    val maxAge = 60 * 60
//                    response.newBuilder()
//                        .removeHeader("Pragma")
//                        .header("Cache-Control", "public, max-age=$maxAge")
//                        .build()
//                } else {
//                    val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
//                    response.newBuilder()
//                        .removeHeader("Pragma")
//                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
//                        .build()
//                }
                response
            }
            .sslSocketFactory(
                MySSLSocketFactory.getSSLContext()!!.socketFactory,
                MySSLSocketFactory.getTrustManager()!!
            )
            .addNetworkInterceptor(logging)
            .addInterceptor(RequestEncryptInterceptor())
            .addInterceptor(ResponseDecryptInterceptor())

    }
}
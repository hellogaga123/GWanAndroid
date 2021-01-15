package com.gaga.lib_core.netWorks

import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import java.net.UnknownHostException
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * @Author Gaga
 * @Date 2020/11/5 18:14
 * @Description
 */
object MySSLSocketFactory: SSLSocketFactory() {
    private val sslContext = SSLContext.getInstance("TLS")
    private var trustManager: TrustManager? = null


    fun getSSLContext(): SSLContext? {
        return sslContext
    }

    fun getTrustManager(): X509TrustManager? {
        return trustManager as X509TrustManager?
    }

    init {
        trustManager = object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(
                x509Certificates: Array<X509Certificate?>?,
                s: String?
            ) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(
                x509Certificates: Array<X509Certificate?>?,
                s: String?
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                //注意这里不能返回null，否则会报错,如下面错误[1]
                return arrayOfNulls(0)
            }
        }
        sslContext.init(null, arrayOf<TrustManager>(trustManager as X509TrustManager), null)
    }


    override fun getDefaultCipherSuites(): Array<String?>? {
        return arrayOfNulls(0)
    }

    override fun getSupportedCipherSuites(): Array<String?>? {
        return arrayOfNulls(0)
    }

    @Throws(IOException::class)
    override fun createSocket(): Socket? {
        return sslContext.socketFactory.createSocket()
    }

    @Throws(IOException::class)
    override fun createSocket(
        socket: Socket?,
        host: String?,
        post: Int,
        autoClose: Boolean
    ): Socket? {
        return sslContext.socketFactory.createSocket(socket, host, post, autoClose)
    }

    @Throws(IOException::class, UnknownHostException::class)
    override fun createSocket(s: String?, i: Int): Socket? {
        return null
    }

    @Throws(IOException::class, UnknownHostException::class)
    override fun createSocket(s: String?, i: Int, inetAddress: InetAddress?, i1: Int): Socket? {
        return null
    }

    @Throws(IOException::class)
    override fun createSocket(inetAddress: InetAddress?, i: Int): Socket? {
        return null
    }

    @Throws(IOException::class)
    override fun createSocket(
        inetAddress: InetAddress?,
        i: Int,
        inetAddress1: InetAddress?,
        i1: Int
    ): Socket? {
        return null
    }
//    /**
//     * 绕过验证
//     * @return
//     */
//    fun createIgnoreVerifySSL(sslVersion: String): SSLSocketFactory {
//        var sc = SSLContext.getInstance(sslVersion);
//        val trustAllCerts: Array<TrustManager> = arrayOf(object : X509TrustManager {
//            @Throws(CertificateException::class)
//            override fun checkClientTrusted(
//                chain: Array<java.security.cert.X509Certificate>, authType: String
//            ) {
//            }
//
//            @Throws(CertificateException::class)
//            override fun checkServerTrusted(
//                chain: Array<java.security.cert.X509Certificate>,
//                authType: String
//            ) {
//            }
//
//            override fun getAcceptedIssuers(): Array<X509Certificate?> {
//                return arrayOfNulls(0)
//            }
//        })
//
//        sc!!.init(null, trustAllCerts, java.security.SecureRandom())
//
//        // Create all-trusting host name verifier
//        val allHostsValid = HostnameVerifier { _, _ -> true }
//
//        return sc.socketFactory;
//    }
}
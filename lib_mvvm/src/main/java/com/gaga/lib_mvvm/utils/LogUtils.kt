package com.gaga.lib_mvvm.utils

import android.util.Log

/**
 * @Author Gaga
 * @Date 2020/7/17 11:39
 * @Description
 */
object LogUtils {
    var className //类名
            : String? = null
    var methodName //方法名
            : String? = null
    var lineNumber //行数
            = 0

    /**
     * 判断是否可以调试
     * @return
     */
    fun isDebuggable(): Boolean {
        return true
    }

    private fun createLog(log: String): String {
        val buffer = StringBuffer()
        buffer.append("================")
        buffer.append(methodName)
        buffer.append("(").append(className).append(":").append(lineNumber)
            .append(")================:")
        buffer.append(log)
        return buffer.toString()
    }

    /**
     * 获取文件名、方法名、所在行数
     * @param sElements
     */
    private fun getMethodNames(sElements: Array<StackTraceElement>) {
        className = sElements[1].fileName
        methodName = sElements[1].methodName
        lineNumber = sElements[1].lineNumber
    }

    fun e(message: String) {
        if (!isDebuggable()) return
        getMethodNames(Throwable().stackTrace)
        Log.e(className, createLog(message))
    }

    fun i(message: String) {
        if (!isDebuggable()) return
        getMethodNames(Throwable().stackTrace)
        Log.i(className, createLog(message))
    }

    fun d(message: String) {
        if (!isDebuggable()) return
        getMethodNames(Throwable().stackTrace)
        Log.d(className, createLog(message))
    }

    fun v(message: String) {
        if (!isDebuggable()) return
        getMethodNames(Throwable().stackTrace)
        Log.v(className, createLog(message))
    }

    fun w(message: String) {
        if (!isDebuggable()) return
        getMethodNames(Throwable().stackTrace)
        Log.w(className, createLog(message))
    }
}
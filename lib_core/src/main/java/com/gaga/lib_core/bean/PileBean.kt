package com.gaga.lib_core.bean

/**
 * @Author Gaga
 * @Date 2020/6/12 16:18
 * @Description
 */
data class PileBean(
    val lists: List<Lists>
)

data class Lists(
    val currentPrice: Double,
    val distance: String,
    val freeQuickNum: Int,
    val freeSlowNum: Int,
    val isOpenTime: Int,
    val latitude: String,
    val location: String,
    val longitude: String,
    val nextPeriod: String,
    val nextPeriodPrice: Double,
    val openTime: String,
    val quickNum: Int,
    val siteId: Int,
    val siteName: String,
    val slowNum: Int,
    val state: Int,
    val tagList: List<Any>
)
package com.gaga.lib_core.bean

/**
 * @Author Gaga
 * @Date 2020/7/17 11:10
 * @Description
 */
object UserCache {
    var authInfo: AuthInfo? = null
    var chargingLimitMoney: ChargingLimitMoney? = null
    var userInfo: UserInfo? = null
    fun setUser(user: User) {
        authInfo = user.authInfo
        userInfo = user.userInfo
        chargingLimitMoney = user.chargingLimitMoney
    }
}
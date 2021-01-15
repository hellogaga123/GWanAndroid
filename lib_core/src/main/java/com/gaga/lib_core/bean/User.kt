package com.gaga.lib_core.bean


/**
 * Created by Lu
 * on 2018/4/5 08:02
 */
data class User(
    var authInfo: AuthInfo,
    var chargingLimitMoney: ChargingLimitMoney,
    var userInfo: UserInfo
)

data class AuthInfo(
    var expire: Long,
    var token: String = ""
)

data class ChargingLimitMoney(
    var limitMoney: Int,
    var warnMoney: Int
)

data class UserInfo(
    var accountId: Int,
    var allowCorpAccounts: Int,
    var carInfo: Int,
    var consumptionPattern: Int,
    var corpCustomId: Int,
    var gigatonThrowdown: Int,
    var headImg: String,
    var id: Int,
    var mobile: String,
    var msgCount: Int,
    var nickname: String,
    var realName: String,
    var thirdNickName: String,
    var totalEnergy: String,
    var userType: Int,
    var wxBind: Int
)
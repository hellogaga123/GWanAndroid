package com.gaga.gwanandroid.login

import com.gaga.lib_core.bean.User
import com.gaga.lib_core.netWorks.ApiService
import com.gaga.lib_mvvm.mvvm.BaseRepository
import com.gaga.lib_mvvm.mvvm.Result
/**
 * @Author Gaga
 * @Date 2020/8/19 16:40
 * @Description
 */
class LoginRepository(private val service: ApiService) : BaseRepository() {
    suspend fun login(userName: String, passWord: String): Result<User> {
        return safeApiCall { requestLogin(userName, passWord) }
    }

    private suspend fun requestLogin(userName: String, passWord: String): Result<User> {
        return executeResponse(service.login(userName, passWord))
    }
}
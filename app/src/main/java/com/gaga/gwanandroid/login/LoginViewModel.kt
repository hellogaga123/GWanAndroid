package com.gaga.gwanandroid.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gaga.lib_core.bean.User
import com.gaga.lib_core.bean.UserCache
import com.gaga.lib_core.constant.Config
import com.gaga.lib_core.utils.MMKVUtil
import com.gaga.lib_mvvm.mvvm.BaseViewModel
import com.gaga.lib_mvvm.mvvm.checkResult
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @Author Gaga
 * @Date 2020/8/19 16:37
 * @Description
 */
class LoginViewModel(private val repository: LoginRepository) : BaseViewModel() {
    //    private val _uiState = MutableLiveData<LoginUiState<User>>()
//    val uiState: LiveData<LoginUiState<User>>
//        get() = _uiState
//    val userName = MutableLiveData<String>()
//    val passWord = MutableLiveData<String>()
    val userName = MutableStateFlow("")
    val passWord = MutableStateFlow("")
//    val str = MutableLiveData<String>()

    val flow = MutableStateFlow(LoginUiState<User>())

    fun loginClick() {
//        if (userName.value != "1") {
//            userName.value = "不等于1"
//        }
        login(userName.value ?: "", passWord.value ?: "")
    }


    fun login(userName: String, passWord: String) {
        launchOnUI {
//            _uiState.value = LoginUiState(isLoading = true)
            flow.emit(LoginUiState(isLoading = true))
//            BaseRepository().safeApiCall(call = {
//                BaseRepository().executeResponse(RetrofitClient.service.login(userName, passWord))
//            }).checkResult({
//                uiState.value = LoginUiState(isSuccess = it, isLoading = false)
//            }, {
//                uiState.value = LoginUiState(isError = it, isLoading = false)
//            })
            repository
                .login(userName, passWord)
                .checkResult({
                    val userJson = Gson().toJson(it)
//                    SPFileName.USER_INFO_CACHE.writeSPs(
//                        Config.IS_LOGIN / true,
//                        Config.TOKEN / it.authInfo.token,
//                        Config.USER / userJson
//                    )
                    MMKVUtil.encode(Config.IS_LOGIN, true)
                    MMKVUtil.encode(Config.TOKEN, it.authInfo.token)
                    MMKVUtil.encode(Config.USER, userJson)
                    UserCache.setUser(it)
                    flow.emit(LoginUiState(isSuccess = it, enableLoginButton = true))
//                    _uiState.value =
//                        LoginUiState(isSuccess = it, enableLoginButton = true)
                }, {
//                    _uiState.value =
//                        LoginUiState(isError = it, enableLoginButton = true)
                    flow.emit(LoginUiState(isError = it, enableLoginButton = true))
                })
        }
    }

    private fun isInputValid(userName: String, passWord: String): Boolean =
        userName.isNotBlank() && passWord.isNotBlank()

    val verifyInput: suspend () -> Unit = suspend {
        flow.emit(
            LoginUiState(
                enableLoginButton = isInputValid(
                    userName.value, passWord.value
                )
            )
        )
//        _uiState.value = LoginUiState(
//            enableLoginButton = isInputValid(
//                userName.value ?: "", passWord.value ?: ""
//            )
//        )
    }
}
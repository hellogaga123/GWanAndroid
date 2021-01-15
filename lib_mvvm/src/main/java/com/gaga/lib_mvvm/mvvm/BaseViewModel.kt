package com.gaga.lib_mvvm.mvvm

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @Author Gaga
 * @Date 2020/8/10 15:06
 * @Description
 */
open class BaseViewModel : ViewModel(), IBaseViewModel {

    open class UiState<T>(
        val isLoading: Boolean = false,
        val isRefresh: Boolean = false,
        val isSuccess: T? = null,
        val isError: String? = null
    )
    open class BaseUiModel<T>(
        var showLoading: Boolean = false,
        var showError: String? = null,
        var showSuccess: T? = null,
        var showEnd: Boolean = false, // 加载更多
        var isRefresh: Boolean = false // 刷新

    )
    open class BaseModel(

    )
    val mException: MutableLiveData<Throwable> = MutableLiveData()

    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {

        viewModelScope.launch { block() }

    }

    fun <T> launchOnIO(block: suspend CoroutineScope.() -> T) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                block()
            }
        }

    }

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {
    }

    override fun onCreate() {
    }

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
    }

    override fun onCleared() {
        super.onCleared()
    }
}
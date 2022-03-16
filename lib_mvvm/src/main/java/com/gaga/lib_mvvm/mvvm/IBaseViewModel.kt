package com.gaga.lib_mvvm.mvvm

import androidx.lifecycle.*

/**
 * @Author Gaga
 * @Date 2020/8/10 15:26
 * @Description
 */
interface IBaseViewModel : LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_ANY -> onAny(source, event)
            Lifecycle.Event.ON_CREATE -> onCreate()
            Lifecycle.Event.ON_START -> onStart()
            Lifecycle.Event.ON_RESUME -> onResume()
            Lifecycle.Event.ON_PAUSE -> onPause()
            Lifecycle.Event.ON_STOP -> onStop()
            Lifecycle.Event.ON_DESTROY -> onDestroy()
        }
    }

    fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?)
    fun onCreate()
    fun onStart()
    fun onResume()
    fun onPause()
    fun onStop()
    fun onDestroy()
}
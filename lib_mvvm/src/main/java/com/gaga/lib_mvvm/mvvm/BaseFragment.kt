package com.gaga.lib_mvvm.mvvm

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * @Author Gaga
 * @Date 2020/8/19 10:37
 * @Description
 */
abstract class BaseFragment(@LayoutRes open val resId: Int, val isUserBinding: Boolean = false) :
    Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (isUserBinding) {
            initBinding(inflater, container, savedInstanceState)
        } else {
            inflater.inflate(resId, container, false)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isUserBinding) {
            initBinding()
        }
        initStatusBar(false)
        initView()
        initData()
        initListener()
    }

    internal open fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return null
    }

    internal open fun initBinding() {}
    abstract fun initView()
    open fun initData() {}
    open fun initListener() {}

    /**
     * 去掉状态栏背景
     *
     * @param isTransparent
     */
    open fun initStatusBar(isTransparent: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity?.window
            window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            if (isTransparent) {
                window!!.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    window!!.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                }
            }
            window!!.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }
}
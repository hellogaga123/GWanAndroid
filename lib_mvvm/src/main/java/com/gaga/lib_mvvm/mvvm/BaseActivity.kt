package com.gaga.lib_mvvm.mvvm

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * @Author Gaga
 * @Date 2020/8/17 14:02
 * @Description
 */
abstract class BaseActivity(
    @LayoutRes open val resId: Int,
    private val isUserBinding: Boolean = false
) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isUserBinding) {
            setContentView(resId)
        } else {
            initBinding()
        }
        initStatusBar(false)
        initView()
        initData()
        initListener()
    }

    internal open fun initBinding() {

    }

    abstract fun initView()
    open fun initData() {}
    open fun initListener() {}
    open fun getContext(): Context {
        return this
    }

    /**
     * 去掉状态栏背景
     *
     * @param isTransparent
     */
    open fun initStatusBar(isTransparent: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            if (isTransparent) {
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                }
            }
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private var progressDialog: ProgressDialog? = null
    protected fun showProgressDialog() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        progressDialog?.show()
    }

    protected fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }
}
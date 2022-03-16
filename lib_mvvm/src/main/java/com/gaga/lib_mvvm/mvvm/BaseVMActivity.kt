package com.gaga.lib_mvvm.mvvm

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @Author Gaga
 * @Date 2020/8/10 14:51
 * @Description
 */
abstract class BaseVMActivity<VB : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes override val resId: Int,
    val br: Int
) : BaseActivity(resId, true) {
    protected lateinit var mViewModel: VM
    protected lateinit var mBinding: VB
    override fun initBinding() {
        super.initBinding()
        mViewModel = initVM()
        mBinding = DataBindingUtil.setContentView(this, resId)
        mBinding.lifecycleOwner = this
        lifecycle.addObserver(mViewModel)
        mBinding.setVariable(br, mViewModel)
    }

    abstract fun initVM(): VM
}
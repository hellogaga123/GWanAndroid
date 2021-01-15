package com.gaga.lib_mvvm.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @Author Gaga
 * @Date 2020/8/10 15:46
 * @Description
 */
abstract class BaseVMFragment<VB : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes override val resId: Int
) : BaseFragment(resId, true) {

    protected lateinit var mBinding: VB
    protected lateinit var mViewModel: VM

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = DataBindingUtil.inflate(inflater, resId, container, false)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun initBinding() {
        mViewModel = initVM()
        lifecycle.addObserver(mViewModel)
    }

    abstract fun initVM(): VM
}
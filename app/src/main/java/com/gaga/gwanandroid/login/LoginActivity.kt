package com.gaga.gwanandroid.login

import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.gaga.gwanandroid.R
import com.gaga.gwanandroid.databinding.ActivityLoginBinding
import com.gaga.lib_core.constant.ARouterConfig
import com.gaga.lib_mvvm.mvvm.BaseVMActivity
import com.gaga.lib_mvvm.utils.toast
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * @Author Gaga
 * @Date 2020/8/19 16:24
 * @Description
 */
@Route(path = ARouterConfig.Url_LoginActivity)
class LoginActivity :
    BaseVMActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login, BR.viewModel) {
    override fun initVM(): LoginViewModel = getViewModel()
    override fun initView() {
    }
//    override fun initListener() {
//        super.initListener()
//        mViewModel.apply {
//            flow.collect {
//                if (it.isLoading) showProgressDialog()
//                it.isSuccess?.let {
//                    dismissProgressDialog()
//                    ARouter.getInstance().build(ARouterConfig.Url_MainActivity).navigation()
//                }
//                it.isError?.let { err ->
//                    dismissProgressDialog()
//                    toast(err)
//                }
//            }
//        }
//    }
}
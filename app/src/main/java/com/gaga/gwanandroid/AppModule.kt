package com.gaga.gwanandroid

import com.gaga.gwanandroid.login.LoginRepository
import com.gaga.gwanandroid.login.LoginViewModel
import com.gaga.lib_core.constant.Config
import com.gaga.lib_core.netWorks.ApiService
import com.gaga.lib_core.netWorks.RetrofitClient
import com.gaga.module_seekpile.seek_pile.PileRepository
import com.gaga.module_seekpile.seek_pile.SeekPileMapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @Author Gaga
 * @Date 2020/11/5 18:19
 * @Description
 */
val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel() }
    viewModel { SeekPileMapViewModel(get()) }
}

val repositoryModule = module {
    single { RetrofitClient.getService(ApiService::class.java, Config.baseUrl) }
    single { LoginRepository(get()) }
    single { PileRepository(get()) }

//    single { CoroutinesDispatcherProvider() }
}

val appModule = listOf(viewModelModule, repositoryModule)
package com.gaga.module_seekpile.seek_pile

import com.gaga.lib_core.bean.PileBean
import com.gaga.lib_core.netWorks.ApiService
import com.gaga.lib_mvvm.mvvm.BaseRepository
import com.gaga.lib_mvvm.mvvm.Result

/**
 * @Author Gaga
 * @Date 2020/6/12 15:56
 * @Description
 */
class PileRepository(var service: ApiService) : BaseRepository() {
    suspend fun loadPile(pageSize: String,lat:String,lon:String,city:String): Result<PileBean> {
        return safeApiCall(call = { executeResponse(service.loadPile(pageSize,lat,lon,city)) })
    }
}
package com.gaga.module_seekpile.seek_pile

import com.gaga.lib_core.cluster.ClusterItem
import com.gaga.lib_mvvm.mvvm.BaseViewModel

/**
 * @Author Gaga
 * @Date 2020/12/25 17:11
 * @Description
 */
class SeekPileState(
    val clusterData: MutableList<ClusterItem>? = null,
    val clusterRadius: Int = 30
) : BaseViewModel.BaseModel()
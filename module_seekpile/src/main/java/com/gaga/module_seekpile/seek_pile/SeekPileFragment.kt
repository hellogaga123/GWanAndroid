package com.gaga.module_seekpile.seek_pile

import com.gaga.lib_core.utils.showFragment
import com.gaga.lib_mvvm.mvvm.BaseFragment
import com.gaga.module_seekpile.R
import kotlinx.android.synthetic.main.fragment_seek_pile.*

/**
 * @Author Gaga
 * @Date 2020/11/10 15:51
 * @Description
 */
class SeekPileFragment : BaseFragment(R.layout.fragment_seek_pile){
    var isSeekPileMap: Boolean = true
    override fun initView() {
        showFragment(R.id.fm_seekPileMap)
    }
    override fun initListener() {
        super.initListener()
        ivChangeMode.setOnClickListener {
            when (isSeekPileMap) {
                true -> {
                    isSeekPileMap = false
                    showFragment(R.id.fm_seekPileList)
                }
                false -> {
                    isSeekPileMap = true
                    showFragment(R.id.fm_seekPileMap)
                }
            }
        }
    }
}
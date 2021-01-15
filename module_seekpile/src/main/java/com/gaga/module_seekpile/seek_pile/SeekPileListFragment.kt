package com.gaga.module_seekpile.seek_pile

import android.view.animation.Animation
import com.gaga.lib_core.utils.create3dAnimation
import com.gaga.lib_mvvm.mvvm.BaseFragment
import com.gaga.module_seekpile.R

/**
 * @Author Gaga
 * @Date 2020/8/27 16:58
 * @Description
 */
class SeekPileListFragment : BaseFragment(R.layout.fragment_seek_pile_list) {
    override fun initView() {
    }
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return create3dAnimation(transit, enter, nextAnim) ?: super.onCreateAnimation(
            transit,
            enter,
            nextAnim
        )
    }
}
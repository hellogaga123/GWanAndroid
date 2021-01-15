package com.gaga.lib_core.utils

import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.gaga.lib_core.R
import com.gaga.lib_core.widget.rotate3d.Rotate3dAnimation

/**
 * @Author Gaga
 * @Date 2020/8/27 15:18
 * @Description
 */


private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction?) {
    beginTransaction().func()?.commit()
}

/**
 * activity中显示fragment
 * @receiver FragmentActivity
 * @param fragmentId Int
 * @param animations Int
 */
fun FragmentActivity.showFragment(fragmentId: Int, animations: Int = 0) {
    val fragment: Fragment? = supportFragmentManager.findFragmentById(fragmentId)
    supportFragmentManager.inTransaction {

        supportFragmentManager.fragments.filter { it != fragment }.forEach {
            hide(it)
        }
        fragment?.let {
            if (!it.isAdded) {
                add(fragmentId, it)
            }
            show(fragment)
        }
    }
}

/**
 * fragment显示fragment
 * @receiver Fragment
 * @param fragmentId Int
 */
fun Fragment.showFragment(fragmentId: Int) {
    val fragment: Fragment? = childFragmentManager.findFragmentById(fragmentId)

    childFragmentManager.inTransaction {
        setCustomAnimations(
            R.anim.rotate_3d_enter,
            R.anim.rotate_3d_exit
//            R.anim.fragment_slide_left_in,
//            R.anim.fragment_slide_right_out
        )
        childFragmentManager.fragments.filter { it != fragment }.forEach {
            hide(it)
        }
        fragment?.let {
            if (!it.isAdded) {
                add(fragmentId, it)
            }
            show(fragment)
        }
    }
}

/**
 * 创建fragment翻转动画
 * @receiver Fragment
 * @param transit Int
 * @param enter Boolean
 * @param nextAnim Int
 * @return Animation?
 */
fun Fragment.create3dAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
    return when (nextAnim) {
        R.anim.rotate_3d_enter -> {
            val animation = Rotate3dAnimation(270f, 360f, 0.5f,0.5f,0.5f,false,0)
            animation.duration = 600
            animation.startOffset = 300
            animation.fillAfter = false
            animation.interpolator = DecelerateInterpolator()
            animation
        }
        R.anim.rotate_3d_exit -> {
            val animation = Rotate3dAnimation(0f, 90f, 0.5f,0.5f,0.5f,true,0)
            animation.duration = 300
            animation.fillAfter = false
            animation.interpolator = AccelerateInterpolator()
            animation
        }
        else -> null
    }
}
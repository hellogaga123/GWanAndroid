package com.gaga.lib_core.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.widget.RelativeLayout

/**
 * @Author Gaga
 * @Date 2020/6/9 09:50
 * @Description 自定义沉浸TitleBar
 */
class CompatTitleBar : RelativeLayout {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        step()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

    }

    private fun step() {
        var compatPaddingTop = 0
        // android 4.4以上将Toolbar添加状态栏高度的上边距，沉浸到状态栏下方
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            compatPaddingTop = getStatusBarHeight()
        }

        setPadding(
            paddingLeft + 15,
            paddingTop + compatPaddingTop,
            paddingRight + 15,
            paddingBottom
        )
        Log.d("CompatToolbar", "状态栏高度step：${paddingTop}")
    }

    private fun getStatusBarHeight(): Int {
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        Log.d("CompatToolbar", "状态栏高度：" + statusBarHeight + "dp")
        return statusBarHeight
    }

    private fun px2dp(pxVal: Float): Float {
        val scale = context.resources.displayMetrics.density
        return pxVal / scale
    }
}
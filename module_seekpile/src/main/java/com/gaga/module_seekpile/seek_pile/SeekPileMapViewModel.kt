package com.gaga.module_seekpile.seek_pile

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amap.api.maps.model.LatLng
import com.gaga.lib_core.bean.PileBean
import com.gaga.lib_core.cluster.ClusterItem
import com.gaga.lib_core.cluster.ClusterOverlay
import com.gaga.lib_core.cluster.RegionItem
import com.gaga.lib_mvvm.BaseApplication
import com.gaga.lib_mvvm.mvvm.BaseViewModel
import com.gaga.lib_mvvm.mvvm.checkResult
import com.gaga.module_seekpile.R
import java.util.*


/**
 * @Author Gaga
 * @Date 2020/6/9 14:43
 * @Description
 */
class SeekPileMapViewModel(private val repository: PileRepository) : BaseViewModel() {

    private val _state = MutableLiveData<SeekPileState>()
    val state: LiveData<SeekPileState>
        get() = _state
    lateinit var mClusterOverlay: ClusterOverlay
    private val mBackDrawAbles: MutableMap<Int, Drawable> = HashMap()

    fun loadPile(pageSize: String, lat: String, lon: String, city: String) {
        launchOnUI {
            repository.loadPile(pageSize, lat, lon, city)
                .checkResult({
                    addClusterData(it)
                    Log.e("ffffffff", "loadPile: ${it.lists.size}")
                }, {

                })
        }
    }

    fun addClusterData(pileBean: PileBean) {
        val items: MutableList<ClusterItem> = ArrayList<ClusterItem>()
        launchOnUI {
            //随机10000个点
            for (b in pileBean.lists) {
//                val lat = Math.random() + 39.474923
//                val lon = Math.random() + 116.027116
                val latLng = LatLng(b.latitude.toDouble(), b.longitude.toDouble(), false)
                val regionItem =
                    when (b.state) {
                        1 -> RegionItem(
                            latLng,
                            "test+${b.siteId}",
                            R.mipmap.ic_marker_kong_xian
                        )
                        2 -> RegionItem(
                            latLng,
                            "test+${b.siteId}",
                            R.mipmap.ic_marker_zhan_yong
                        )
                        3 -> RegionItem(
                            latLng,
                            "test+${b.siteId}",
                            R.mipmap.ic_marker_zan_ting
                        )
                        else -> RegionItem(
                            latLng,
                            "test+${b.siteId}",
                            R.mipmap.ic_marker_kong_xian
                        )
                    }

//                RegionItem(
//                    latLng,
//                    "test+${b.siteId}",
//
//                    )
                items.add(regionItem)
            }
            _state.value = SeekPileState(clusterData = items)
        }
    }

    fun setClusterRenderer() {
        mClusterOverlay.setClusterRenderer {
            Log.e("aaaaaaaa", "setClusterRenderer: ")
            val radius = dp2px(BaseApplication.CONTEXT, 80f)
            when {
//                it == 1 -> {
//                    Log.e("aaaaaaaa", "setClusterRenderer1111: ")
//
//                    var bitmapDrawable: Drawable? = mBackDrawAbles[1]
//                    if (bitmapDrawable == null) {
//                        when {
//
//                        }
//                        bitmapDrawable =
//                            BaseApplication.CONTEXT.resources.getDrawable(R.drawable.icon_openmap_mark)
//                        mBackDrawAbles[1] = bitmapDrawable
//                    }
//                    bitmapDrawable
//                }
                it < 20 -> {
                    var bitmapDrawable: Drawable? = mBackDrawAbles[2]
                    if (bitmapDrawable == null) {
                        bitmapDrawable = BitmapDrawable(
                            null, drawCircle(
                                radius,
                                Color.argb(159, 210, 154, 6)
                            )
                        )
                        mBackDrawAbles[2] = bitmapDrawable
                    }
                    bitmapDrawable
                }
                it < 50 -> {
                    var bitmapDrawable: Drawable? = mBackDrawAbles[3]
                    if (bitmapDrawable == null) {
                        bitmapDrawable = BitmapDrawable(
                            null, drawCircle(
                                radius,
                                Color.argb(199, 217, 114, 0)
                            )
                        )
                        mBackDrawAbles[3] = bitmapDrawable
                    }
                    bitmapDrawable
                }
                else -> {
                    var bitmapDrawable: Drawable? = mBackDrawAbles[4]
                    if (bitmapDrawable == null) {
                        bitmapDrawable = BitmapDrawable(
                            null, drawCircle(
                                radius,
                                Color.argb(235, 215, 66, 2)
                            )
                        )
                        mBackDrawAbles[4] = bitmapDrawable
                    }
                    bitmapDrawable
                }
            }
        }
    }

    fun drawCircle(radius: Int, color: Int): Bitmap? {
        val bitmap = Bitmap.createBitmap(
            radius * 2, radius * 2,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        val paint = Paint()
        val rectF = RectF(0F, 0F, (radius * 2).toFloat(), (radius * 2).toFloat())
        paint.color = color
        canvas.drawArc(rectF, 0f, 360f, true, paint)
        return bitmap
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}
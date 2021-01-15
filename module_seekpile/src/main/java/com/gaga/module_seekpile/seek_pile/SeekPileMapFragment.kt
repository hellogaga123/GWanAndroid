package com.gaga.module_seekpile.seek_pile

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import androidx.lifecycle.Observer
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.LatLngBounds
import com.amap.api.maps.model.MyLocationStyle
import com.gaga.lib_core.cluster.ClusterOverlay
import com.gaga.lib_core.utils.LocationAMapUtils
import com.gaga.lib_core.utils.checkPermissions
import com.gaga.lib_core.utils.create3dAnimation
import com.gaga.lib_mvvm.BaseApplication
import com.gaga.lib_mvvm.mvvm.BaseVMFragment
import com.gaga.lib_mvvm.utils.toast
import com.gaga.module_seekpile.R
import com.gaga.module_seekpile.databinding.FragmentSeekPileMapBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * @Author Gaga
 * @Date 2020/6/3 13:29
 * @Description
 */
class SeekPileMapFragment :
    BaseVMFragment<FragmentSeekPileMapBinding, SeekPileMapViewModel>(R.layout.fragment_seek_pile_map) {
    override fun initVM(): SeekPileMapViewModel = getViewModel()

    lateinit var mAMap: AMap


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.map.onCreate(savedInstanceState)
    }

    override fun initView() {
        mAMap = mBinding.map.map

        checkPermissions({
            mAMap.myLocationStyle =
                LocationAMapUtils.getLocationStyle(locationType = MyLocationStyle.LOCATION_TYPE_LOCATE)
            mAMap.uiSettings.isMyLocationButtonEnabled = true//设置默认定位按钮是否显示，非必需设置。
            mAMap.isMyLocationEnabled = true // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        }, {
            toast(BaseApplication.CONTEXT, "拒绝")
        }, {
            toast(BaseApplication.CONTEXT, "拒绝并不再提醒")
        }, Manifest.permission.ACCESS_COARSE_LOCATION)

//        LocationUtils.setListener(AMapLocationListener {
//            Log.e("111111111", it.errorCode.toString() + "\n" + it.longitude + "\n" + it.latitude)
//        })
//        LocationUtils.startLocation()

    }

    override fun initListener() {
        super.initListener()
//        tvText.setOnClickListener {
//        LocationUtils.startLocation()
//        }
        mAMap.setOnMapLoadedListener {
            mAMap.addOnMyLocationChangeListener {
                Log.e("111111111", "\n" + it.longitude + "\n" + it.latitude)
                mViewModel.loadPile("-1", it.longitude.toString(), it.latitude.toString(), "")
            }
        }
        mViewModel.apply {
            state.observe(this@SeekPileMapFragment, Observer {
                mClusterOverlay = ClusterOverlay(
                    mAMap, it.clusterData,
                    dp2px(BaseApplication.CONTEXT, it.clusterRadius.toFloat()),
                    BaseApplication.CONTEXT
                )
                mViewModel.setClusterRenderer()
                mClusterOverlay.setOnClusterClickListener { marker, clusterItems ->
                    val builder = LatLngBounds.Builder()
                    for (clusterItem in clusterItems!!) {
                        builder.include(clusterItem.position)
                    }
                    val latLngBounds = builder.build()
                    mAMap.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(latLngBounds, 0)
                    )
                }
            })
        }


        //点击可以动态添加点
        mAMap.setOnMapClickListener {
//            val lat = Math.random() + 39.474923
//            val lon = Math.random() + 116.027116
//            val latLng1 = LatLng(lat, lon, false)
//            val regionItem = RegionItem(
//                latLng1,
//                "test"
//            )
//            mClusterOverlay.addClusterItem(regionItem)
        }
        mAMap.addOnMyLocationChangeListener {
        }

    }

    override fun onResume() {
        super.onResume()
        mBinding.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        mBinding.map.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.map.onDestroy()
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return create3dAnimation(transit, enter, nextAnim) ?: super.onCreateAnimation(
            transit,
            enter,
            nextAnim
        )
    }
}
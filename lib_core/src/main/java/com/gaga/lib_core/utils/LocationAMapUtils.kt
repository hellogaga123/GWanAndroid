package com.gaga.lib_core.utils

import com.amap.api.maps.model.BitmapDescriptor
import com.amap.api.maps.model.MyLocationStyle

/**
 * @Author Gaga
 * @Date 2020/8/27 13:43
 * @Description
 */

object LocationAMapUtils {
    private val myLocationStyle by lazy { MyLocationStyle() }

    /**
     *
     * @Description 设置定位参数
     * @param interval 设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
     * @param locationType
     * myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
     * myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
     * myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
     * myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
     * myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
     * //以下三种模式从5.1.0版本开始提供
     * myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
     * myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
     * myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
     *
     * @param isShowMyLocation 设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。
     * @param myLocationIcon   设置定位蓝点的icon图标方法，需要用到BitmapDescriptor类对象作为参数。
     * @param anchorLat        设置定位蓝点图标的锚点方法。
     * @param anchorLon        设置定位蓝点图标的锚点方法。
     * @param strokeColor      设置定位蓝点精度圆圈的边框颜色的方法。
     * @param radiusFillColor  设置定位蓝点精度圆圈的填充颜色的方法。
     * @param strokeWidth      设置定位蓝点精度圈的边框宽度的方法。
     */
    fun getLocationStyle(
        interval: Long = 1000,
        locationType: Int = MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER,
        isShowMyLocation: Boolean = true,
        myLocationIcon: BitmapDescriptor? = null,
        anchorLat: Long = 0L,
        anchorLon: Long = 0L,
        strokeColor: Int = 0,
        radiusFillColor: Int = 0,
        strokeWidth: Float = 0.0F
    ): MyLocationStyle {
        return myLocationStyle.apply {
            interval(interval)
            myLocationType(locationType)
        }
    }
}
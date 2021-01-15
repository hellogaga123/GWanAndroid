package com.gaga.lib_core.cluster;

import com.amap.api.maps.model.LatLng;

/**
 * Created by yiyi.qi on 16/10/10.
 */

public class RegionItem implements ClusterItem {
    private LatLng mLatLng;
    private String mTitle;
    private int mDrawableId;
//    private Bitmap mBitmap;

    public RegionItem(LatLng latLng, String title,int drawableId) {
        mLatLng = latLng;
        mTitle = title;
        mDrawableId=drawableId;
//        mBitmap = bitmap;
    }

    @Override
    public LatLng getPosition() {
        // TODO Auto-generated method stub
        return mLatLng;
    }

    @Override
    public int getDrawableId() {
        return mDrawableId;
    }

    public String getTitle() {
        return mTitle;
    }

//    @Override
//    public Bitmap getIcon() {
//        return mBitmap;
//    }
}

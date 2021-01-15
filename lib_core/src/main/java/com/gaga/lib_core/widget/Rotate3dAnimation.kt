package com.gaga.lib_core.widget.rotate3d

import android.graphics.Camera
import android.view.animation.Animation
import android.view.animation.Transformation

/**
 * @Author Gaga
 * @Date 2020/9/1 10:46
 * @Description
 */
class Rotate3dAnimation(
    fromDegrees: Float, toDegrees: Float, reverse: Boolean
) : Animation() {

    constructor(
        fromDegrees: Float,
        toDegrees: Float,
        centerX: Float,
        centerY: Float,
        depthZ: Float,
        reverse: Boolean
    ) : this(fromDegrees, toDegrees, reverse) {
        this.mCenterX = centerX
        this.mCenterY = centerY
        this.mDepthZ = depthZ
        this.mType = TYPE_PX
    }

    constructor(
        fromDegrees: Float,
        toDegrees: Float,
        centerX: Float,
        centerY: Float,
        depthZ: Float,
        reverse: Boolean,
        type: Int
    ) : this(
        fromDegrees,
        toDegrees,
        reverse
    ) {
        this.mCenterX = centerX
        this.mCenterY = centerY
        this.mDepthZ = depthZ
        this.mType = type
    }

    private val TYPE_SCALE = 0
    private val TYPE_PX = 1

    private var mFromDegrees = 0f
    private var mToDegrees = 0f
    private var mCenterX = 0f
    private var mCenterY = 0f
    private var mDepthZ = 0f
    private var mType = TYPE_PX
    private var mReverse = false
    private var mCamera: Camera? = null

    init {
        mFromDegrees = fromDegrees
        mToDegrees = toDegrees
        mReverse = reverse
    }

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
        mCamera = Camera()
        if (mType == TYPE_SCALE) {
            mCenterX *= width
            mCenterY *= height
            mDepthZ *= width
        }
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val fromDegrees = mFromDegrees
        val degrees = fromDegrees + (mToDegrees - fromDegrees) * interpolatedTime
        val centerX = mCenterX
        val centerY = mCenterY
        val camera = mCamera
        val matrix = t.matrix
        camera!!.save()
        if (mReverse) {
            //由近到远的效果
            camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime)
        } else {
            //由远及近的效果
            camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime))
        }
        camera.rotateY(degrees)
        camera.getMatrix(matrix)
        camera.restore()
        matrix.preTranslate(-centerX, -centerY)
        matrix.postTranslate(centerX, centerY)
    }
}
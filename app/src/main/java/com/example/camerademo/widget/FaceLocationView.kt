package com.example.camerademo.widget

import android.content.Context
import android.graphics.*
import android.hardware.Camera
import android.util.AttributeSet

/**
 * User: HYD
 * Date: 2022/1/11
 * Desc: 绘制人脸的位置
 */
class FaceLocationView(context: Context, attrs: AttributeSet? = null) : DrawView(context, attrs) {

    var mFaces: Array<Camera.Face>? = null
        set(value) {
            field = value
            invalidate()
        }

    var mMatrix: Matrix? = null

    init {
        mPaint.color = Color.RED
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (mFaces.isNullOrEmpty()) {
            val paint = Paint()
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            canvas?.drawPaint(paint)
            return
        }

        canvas?.setMatrix(mMatrix)

        mFaces?.forEach {
            it.rect?.apply {
                canvas?.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
            }

            it.leftEye?.apply {
                canvas?.drawText("左眼", x.toFloat(), y.toFloat(), mPaint)
            }

            it.rightEye?.apply {
                canvas?.drawText("右眼", x.toFloat(), y.toFloat(), mPaint)
            }

            it.mouth?.apply {
                canvas?.drawText("嘴巴", x.toFloat(), y.toFloat(), mPaint)
            }
        }
    }
}
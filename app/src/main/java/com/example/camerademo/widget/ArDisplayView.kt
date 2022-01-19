package com.example.camerademo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.hardware.Camera
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.example.camerademo.entity.ArEntity

/**
 * User: HYD
 * Date: 2022/1/11
 * Desc: 绘制 AR 效果
 */
class ArDisplayView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    var arEntity: ArEntity? = null
        @SuppressLint("UseCompatLoadingForDrawables")
        set(value) {
            field = value
            if (value != null) {
                mImg = resources.getDrawable(value.res)
                val imgWidth = mImg!!.minimumWidth.toFloat()
                val imgHeight = mImg!!.minimumHeight.toFloat()
                widthHeightRatio = imgWidth / imgHeight
            }
        }

    private var mFaces: Array<Camera.Face>? = null
        set(value) {
            field = value
            invalidate()
        }

    private var mMatrix: Matrix? = Matrix()
    private val mRect = RectF()
    private var mImg: Drawable? = null
    private var widthHeightRatio = 0f

    fun setData(faces: Array<Camera.Face>?, matrix: Matrix?) {
        if (faces.isNullOrEmpty() || matrix == null) {
            return
        }

        mFaces = faces
        mMatrix = matrix
    }

    @SuppressLint("UseCompatLoadingForDrawables", "DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        if (arEntity == null || mFaces.isNullOrEmpty() || canvas == null || mImg == null) {
            val paint = Paint()
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            canvas?.drawPaint(paint)
            return
        }

        canvas.save()

        arEntity?.apply {
            mRect.set(mFaces!![0].rect)
            mMatrix!!.mapRect(mRect)

            val rectWidth = mRect.right - mRect.left
            val imgScaleHeight = rectWidth / widthHeightRatio

            val left = mRect.left + leftOffset
            val right = mRect.right + rightOffset
            val top: Float
            val bottom: Float

            when (arEntity!!.gravity) {
                Gravity.TOP -> {
                    bottom = mRect.top + bottomOffset
                    top = bottom - imgScaleHeight
                }
                else -> {
                    top = mRect.top + topOffset
                    bottom = mRect.bottom + bottomOffset
                }
            }

            mImg?.setBounds(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
            mImg?.draw(canvas)
        }

        canvas.restore()
        super.onDraw(canvas)
    }
}
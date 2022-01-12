package com.example.camerademo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.hardware.Camera
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.example.camerademo.entity.ArEntity

/**
 * User: HYD
 * Date: 2022/1/11
 * Desc: 绘制 AR 效果
 */
class ArDisplayView(context: Context, attrs: AttributeSet? = null) : AppCompatImageView(context, attrs) {

    var arEntity: ArEntity? = null
        @SuppressLint("UseCompatLoadingForDrawables")
        set(value) {
            field = value
            if (value != null) {
                mImg = resources.getDrawable(value.res)
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

    fun setData(faces: Array<Camera.Face>?, matrix: Matrix?) {
        if (faces.isNullOrEmpty() || matrix == null) {
            return
        }

        mFaces = faces
        mMatrix = matrix
    }

    @SuppressLint("UseCompatLoadingForDrawables", "DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        if (arEntity == null || mFaces.isNullOrEmpty() || canvas == null) {
            val paint = Paint()
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            canvas?.drawPaint(paint)
            return
        }

        canvas.save()

        arEntity?.apply {
            mRect.set(mFaces!![0].rect)
            mMatrix!!.mapRect(mRect)

            val left = mRect.left + leftOffset
            val top = mRect.top + topOffset
            val right = mRect.right + rightOffset
            val bottom = mRect.bottom + bottomOffset

            mImg?.setBounds(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
            mImg?.draw(canvas)
        }

        canvas.restore()
        super.onDraw(canvas)
    }
}
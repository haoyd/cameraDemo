package com.example.camerademo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Camera
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.ConvertUtils

/**
 * User: HYD
 * Date: 2022/1/11
 * Desc:
 */
open class DrawView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val sideWidth = ConvertUtils.dp2px(2f).toFloat()

    private var isFill = false
    private var mColor = Color.parseColor("#000000")

    protected val mPaint = Paint()

    protected var parentWidth: Int = 0
    protected var parentHeight: Int = 0

    protected var cx: Float = 0f
    protected var cy: Float = 0f
    protected var radius: Float = 0f

    protected var mLeft: Float = 0f
    protected var mTop: Float = 0f
    protected var mRight: Float = 0f
    protected var mBottom: Float = 0f

    init {
        mPaint.isAntiAlias = true
        mPaint.color = mColor
        mPaint.style = if (isFill) Paint.Style.FILL else Paint.Style.STROKE
        mPaint.strokeWidth = sideWidth
    }
}
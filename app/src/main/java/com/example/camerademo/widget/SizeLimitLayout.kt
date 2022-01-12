package com.example.camerademo.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.camerademo.R

/**
 * 限制 View 的尺寸自定义 View
 *
 * 使用方式：
 * 将指定 View 包到该 View 中，即可限制其尺寸
 *
 * 限制方式：
 * 方式 1：将高度设置为自适应，并设置最大高度的尺寸，当高度增大到一定程度时就不再增加尺寸了
 * 方式 2：限制高度：设定宽高比，设置限制高度，组件会根据宽高比和自身宽度限制其高度
 * 方式 3：限制宽度：设定宽高比，设置限制宽度，组件会根据宽高比和自身高度限制其宽度
 *
 * 应用场景举例：banner 宽度为屏幕宽度，高度为按设计图宽高比自适应，则可通过该组件轻松设定。
 */
class SizeLimitLayout(context: Context, attr: AttributeSet? = null) : FrameLayout(context, attr) {

    var maxHeight = 0f          // 最大高度
    var widthHeightRatio = 0f   // 宽高比
    var isLimitWidth = false    // 是否根据宽高比限制宽度
    var isLimitHeight = false   // 是否根据宽高比限制高度

    init {
        if (attr != null) {
            val ta = context.obtainStyledAttributes(attr, R.styleable.ymyy_SizeLimitView)
            maxHeight = ta.getDimension(R.styleable.ymyy_SizeLimitView_ymyy_maxHeight, 0f)
            widthHeightRatio = ta.getFloat(R.styleable.ymyy_SizeLimitView_ymyy_widthHeightRatio, 0f)
            isLimitHeight = ta.getBoolean(R.styleable.ymyy_SizeLimitView_ymyy_limitHeightByWidth, false)
            if (!isLimitHeight) {
                isLimitWidth = ta.getBoolean(R.styleable.ymyy_SizeLimitView_ymyy_limitWidthByHeight, false)
            }
            ta.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)

        var heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (maxHeight != 0f) {
            heightSize = if (heightSize > maxHeight) maxHeight.toInt() else heightSize
            heightMode = MeasureSpec.EXACTLY
        } else if (isLimitWidth && widthHeightRatio != 0f) {
            widthSize = (heightSize * widthHeightRatio).toInt()
            widthMode = MeasureSpec.EXACTLY
        } else if (isLimitHeight && widthHeightRatio != 0f) {
            heightSize = (widthSize / widthHeightRatio).toInt()
            heightMode = MeasureSpec.EXACTLY
        }

        val widthSpec = MeasureSpec.makeMeasureSpec(widthSize, widthMode)
        val heightSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode)
        super.onMeasure(widthSpec, heightSpec)
    }
}
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
 * Desc: 绘制滤镜效果
 */
class ArFilterView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    var color: Int? = null
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas?) {
        color?.let {
            canvas?.drawColor(it)
        }
        super.onDraw(canvas)
    }
}
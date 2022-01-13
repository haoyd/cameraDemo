package com.example.camerademo.entity

import android.view.Gravity
import com.example.camerademo.enums.BodyPart

/**
 * User: HYD
 * Date: 2022/1/12
 * Desc: AR 配置
 */
data class ArEntity(
    val res: Int,               // 资源文件
    val part: BodyPart,         // 身体部位
    val gravity: Int,           // 在矩形的哪个方位
    val leftOffset: Float,      // 左偏移
    val topOffset: Float,       // 上偏移
    val rightOffset: Float,     // 右偏移
    val bottomOffset: Float,    // 下偏移
)
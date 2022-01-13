package com.example.camerademo.managers

import android.view.Gravity
import com.example.camerademo.R
import com.example.camerademo.entity.ArEntity
import com.example.camerademo.enums.BodyPart

/**
 * User: HYD
 * Date: 2022/1/12
 * Desc:
 */
object ArResManager {
    val resList: List<ArEntity> = listOf(
        ArEntity(R.mipmap.ic_abbit, BodyPart.HEAD, Gravity.TOP, 0f, 0f, 0f, 100f),
        ArEntity(R.mipmap.ic_boy, BodyPart.HEAD, Gravity.CENTER, -80f, -300f, 80f, 0f),
        ArEntity(R.mipmap.ic_deer, BodyPart.HEAD, Gravity.TOP, 0f, 0f, 0f, 0f),
        ArEntity(R.mipmap.ic_elk, BodyPart.HEAD, Gravity.CENTER, 0f, -400f, 0f, 0f),
        ArEntity(R.mipmap.ic_pig, BodyPart.HEAD, Gravity.TOP, 0f, 0f, 0f, 0f),
    )
}
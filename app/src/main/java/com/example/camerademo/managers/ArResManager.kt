package com.example.camerademo.managers

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
        ArEntity(R.mipmap.ic_abbit, BodyPart.HEAD, 0f, -400f, 0f, -400f),
        ArEntity(R.mipmap.ic_boy, BodyPart.HEAD, -80f, -300f, 80f, 0f),
        ArEntity(R.mipmap.ic_deer, BodyPart.HEAD, 0f, -400f, 0f, -400f),
        ArEntity(R.mipmap.ic_elk, BodyPart.HEAD, 0f, -300f, 0f, -200f),
        ArEntity(R.mipmap.ic_pig, BodyPart.HEAD, 0f, -400f, 0f, -400f),
    )
}
package com.example.camerademo.listeners

import android.graphics.Bitmap
import android.hardware.Camera

/**
 * User: HYD
 * Date: 2022/1/10
 * Desc: 存放所有的回调监听
 */

// 图片回调
typealias ImgCallback = (img: Bitmap) -> Unit

// 人脸信息回调
typealias FaceCallback = (faces: Array<Camera.Face>) -> Unit
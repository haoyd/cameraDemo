package com.example.camerademo.managers

import android.app.Activity
import android.hardware.Camera
import android.view.ViewGroup
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.LogUtils
import com.example.camerademo.common.CameraPreview
import com.example.camerademo.listeners.ImgCallback
import com.example.camerademo.utils.showToast

/**
 * User: HYD
 * Date: 2022/1/7
 * Desc: 管理相机
 */
class CameraManager(context: Activity, containerId: Int) {

    private val camera: Camera = Camera.open()
    private val mContainer: ViewGroup = context.findViewById(containerId)
    private val mPreview: CameraPreview by lazy { CameraPreview(context, camera) }
    private var isAddedPreview = false
    private var imgListener: ImgCallback? = null

    init {
        camera.setDisplayOrientation(90)
    }

    fun startPreview() {
        if (!isAddedPreview) {
            mContainer.addView(mPreview)
        }

        if (isAddedPreview) {
            camera.startPreview()
        }

        isAddedPreview = true
    }

    fun capture(listener: ImgCallback) {
        imgListener = listener

        try {
            camera.takePicture(null, null, { data, _ ->
                val sourceImg = ImageUtils.bytes2Bitmap(data)
                if (sourceImg == null) {
                    showToast("照片获取失败")
                    return@takePicture
                }

                val rotatedImg = ImageUtils.rotate(sourceImg, 90, sourceImg.width / 2f, sourceImg.height / 2f)
                listener.invoke(rotatedImg)
            })
        } catch (e: Exception) {
            LogUtils.d(e.printStackTrace())
            showToast("拍照失败")
        }
    }
}
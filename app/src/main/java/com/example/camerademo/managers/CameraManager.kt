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
class CameraManager(private val context: Activity, containerId: Int) {

    private val mContainer: ViewGroup = context.findViewById(containerId)
    private var imgListener: ImgCallback? = null

    private var frontCameraId = 0
    private var backCameraId = 0
    private var isBackCamera = true
    private var hasOpenedCamera = false
    private var rotateDegree = 90

    private var camera: Camera? = null
        set(value) {
            field = value

            mContainer.removeAllViews()
            if (value == null) return

            mContainer.addView(CameraPreview(context, value))
            camera?.setDisplayOrientation(rotateDegree)
            hasOpenedCamera = true
        }

    init {
        initFrontAndBackCameraId()
    }

    /**
     * 预览
     */
    fun startPreview() {
        camera?.startPreview()
    }

    /**
     * 拍照
     * @param listener Function1<[@kotlin.ParameterName] Bitmap, Unit>
     */
    fun capture(listener: ImgCallback) {
        imgListener = listener

        try {
            camera?.takePicture(null, null, { data, _ ->
                val sourceImg = ImageUtils.bytes2Bitmap(data)
                if (sourceImg == null) {
                    showToast("照片获取失败")
                    return@takePicture
                }

                val degree = if (isBackCamera) rotateDegree else rotateDegree * -1
                val rotatedImg = ImageUtils.rotate(sourceImg, degree, sourceImg.width / 2f, sourceImg.height / 2f)
                listener.invoke(rotatedImg)
            })
        } catch (e: Exception) {
            LogUtils.d(e.printStackTrace())
            showToast("拍照失败")
        }
    }

    /**
     * 打开摄像头
     */
    fun openCamera() {
        if (isBackCamera) openBackCamera() else openFrontCamera()
    }

    /**
     * 切换前后摄像头
     */
    fun switchCamera() {
        if (isBackCamera) openFrontCamera() else openBackCamera()
    }

    /**
     * 打开前摄像头
     */
    fun openFrontCamera() {
        if (hasOpenedCamera && !isBackCamera) {
            return
        }

        releaseCamera()
        camera = Camera.open(frontCameraId)
        isBackCamera = false
    }

    /**
     * 打开后摄像头
     */
    fun openBackCamera() {
        if (hasOpenedCamera && isBackCamera) {
            return
        }

        releaseCamera()
        camera = Camera.open(backCameraId)
        isBackCamera = true
    }

    /**
     * 释放相机
     */
    fun releaseCamera() {
        camera?.stopPreview()
        camera?.release()
        camera = null
    }

    private fun initFrontAndBackCameraId() {
        val cameraInfo = Camera.CameraInfo()
        val count = Camera.getNumberOfCameras()

        // 获取第一颗后置摄像头
        for (i in 0 until count) {
            Camera.getCameraInfo(i, cameraInfo)
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                frontCameraId = i
                break
            }
        }

        // 获取第一颗前置摄像头
        for (i in 0 until count) {
            Camera.getCameraInfo(i, cameraInfo)
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                backCameraId = i
                break
            }
        }
    }
}
package com.example.camerademo.managers

import android.app.Activity
import android.graphics.Matrix
import android.hardware.Camera
import android.hardware.Camera.CameraInfo
import android.view.ViewGroup
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.LogUtils
import com.example.camerademo.common.CameraPreview
import com.example.camerademo.listeners.FaceCallback
import com.example.camerademo.listeners.ImgCallback
import com.example.camerademo.utils.showToast

/**
 * User: HYD
 * Date: 2022/1/7
 * Desc: 管理相机
 */
class CameraManager(private val context: Activity, containerId: Int) {

    var isDetectingFace = false

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

    /**
     * 检测人脸
     * @param listener Function1<[@kotlin.ParameterName] Array<Face>, Unit>
     */
    fun startFaceDetection(listener: FaceCallback) {
        if (!isSupportDetectFace()) {
            showToast("设备不支持人脸检测")
            return
        }

        camera?.setFaceDetectionListener { faces, _ ->
            listener.invoke(faces)
        }

        try {
            camera?.startFaceDetection()
            isDetectingFace = true
        } catch (e: Exception) {
            showToast("启动人脸检测失败")
        }
    }

    /**
     * 停止人脸检测
     */
    fun stopFaceDetection() {
        try {
            camera?.stopFaceDetection()
        } catch (e: Exception) {
            showToast("停止人脸检测失败")
        }
    }

    /**
     * 因为对摄像头进行了旋转，所以同时也旋转画板矩阵
     * 详细请查看[android.hardware.Camera.Face.rect]
     * @return 旋转后的矩阵
     */
    fun getFaceRect(): Matrix {
        val matrix = Matrix()
        matrix.setScale(if (isBackCamera) 1f else -1f, 1f)
        // 刚才我们设置了camera的旋转参数，所以这里也要设置一下
        matrix.postRotate(rotateDegree.toFloat())
        // Camera driver coordinates range from (-1000, -1000) to (1000, 1000).
        // UI coordinates range from (0, 0) to (width, height).
        matrix.postScale(mContainer.width / 2000f, mContainer.height / 2000f)
        matrix.postTranslate(mContainer.width / 2f, mContainer.height / 2f)
        return matrix
    }

    /**
     * 是否支持人脸检测
     * @return Boolean
     */
    private fun isSupportDetectFace(): Boolean {
        val params = camera?.parameters ?: return false
        return params.maxNumDetectedFaces > 0
    }

    /**
     * 获取前置摄像头后后置摄像头的摄像头 ID
     */
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
package com.example.camerademo.common

import android.content.Context
import android.hardware.Camera
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.camerademo.extentions.tryWith
import java.io.IOException

/**
 * User: HYD
 * Date: 2022/1/7
 * Desc:
 */
class CameraPreview(
    context: Context,
    private val mCamera: Camera
) : SurfaceView(context), SurfaceHolder.Callback {

    companion object {
        private const val TAG = "CameraPreview"
    }

    private val mHolder: SurfaceHolder = holder.apply {
        // 监听底层相机生命周期
        addCallback(this@CameraPreview)
        // required on Android versions prior to 3.0
        setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        // Surface 创建完成，告诉相机应该在哪预览
        mCamera.apply {
            tryWith {
                setPreviewDisplay(holder)
                startPreview()
            }
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        // empty. Take care of releasing the Camera preview in your activity.
        mCamera.stopPreview()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, w: Int, h: Int) {
        if (mHolder.surface == null) {
            return
        }

        tryWith { mCamera.stopPreview() }
        mCamera.apply {
            try {
                setPreviewDisplay(mHolder)
                startPreview()
            } catch (e: Exception) {
                Log.d(TAG, "Error starting camera preview: ${e.message}")
            }
        }
    }
}
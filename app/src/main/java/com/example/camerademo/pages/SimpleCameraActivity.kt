package com.example.camerademo.pages

import android.os.Bundle
import android.view.View
import com.example.camerademo.BaseActivity
import com.example.camerademo.R
import com.example.camerademo.managers.CameraManager
import com.example.camerademo.widget.ImgDialog

class SimpleCameraActivity : BaseActivity() {

    private val cameraManager: CameraManager by lazy {
        CameraManager(this, R.id.previewLayout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_camera)

        cameraManager.openCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraManager.releaseCamera()
    }

    fun capture(view: View) {
        cameraManager.capture {
            val dialog = ImgDialog(this)

            dialog.setOnDismissListener {
                cameraManager.startPreview()
            }

            dialog.showImg(it)
        }
    }

    fun switchCamera(view: View) {
        cameraManager.switchCamera()
    }
}
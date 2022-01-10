package com.example.camerademo

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.example.camerademo.common.CameraPreview
import com.example.camerademo.managers.CameraManager
import com.example.camerademo.utils.showToast
import com.example.camerademo.widget.ImgDialog
import kotlinx.android.synthetic.main.activity_simple_camera.*

class SimpleCameraActivity : BaseActivity() {

    private val cameraManager: CameraManager by lazy {
        CameraManager(this, R.id.previewLayout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_camera)

        cameraManager.startPreview()
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
}
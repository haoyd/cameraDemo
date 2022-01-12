package com.example.camerademo.pages

import android.os.Bundle
import android.view.View
import com.example.camerademo.BaseActivity
import com.example.camerademo.R
import com.example.camerademo.managers.CameraManager
import com.example.camerademo.widget.ArResDialog
import kotlinx.android.synthetic.main.activity_ai.*
import kotlinx.android.synthetic.main.activity_ar.*

class ArActivity : BaseActivity() {

    private val cameraManager: CameraManager by lazy {
        CameraManager(this, R.id.cameraContainer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)
        cameraManager.openFrontCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraManager.releaseCamera()
    }

    fun showAr(view: View) {
        val dialog = ArResDialog(this)
        dialog.itemListener = {
            arDisplayView.arEntity = it
            dialog.dismiss()
            startDetect()
        }
        dialog.show()
    }

    fun switchCamera(view: View) {
        val isDetecting = cameraManager.isDetectingFace
        if (isDetecting) cameraManager.stopFaceDetection()
        cameraManager.switchCamera()
        if (isDetecting) startDetect()
    }

    private fun startDetect() {
        cameraManager.startFaceDetection {
            arDisplayView.setData(it, cameraManager.getFaceRect())
        }
    }
}
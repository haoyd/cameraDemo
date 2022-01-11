package com.example.camerademo.pages

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.example.camerademo.BaseActivity
import com.example.camerademo.R
import com.example.camerademo.managers.CameraManager
import kotlinx.android.synthetic.main.activity_ai.*

class AiActivity : BaseActivity() {

    private val cameraManager: CameraManager by lazy {
        CameraManager(this, R.id.cameraContainer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ai)
        cameraManager.openFrontCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraManager.releaseCamera()
    }

    fun detectFace(view: View) {
        startDetect()
    }

    fun switchCamera(view: View) {
        val isDetecting = cameraManager.isDetectingFace
        if (isDetecting) cameraManager.stopFaceDetection()
        cameraManager.switchCamera()
        if (isDetecting) startDetect()
    }

    private fun startDetect() {
        cameraManager.startFaceDetection {
            faceLocationView.mMatrix = cameraManager.getFaceRect()
            faceLocationView.mFaces = it
        }
    }
}
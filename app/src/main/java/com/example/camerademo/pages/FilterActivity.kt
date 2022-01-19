package com.example.camerademo.pages

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.example.camerademo.BaseActivity
import com.example.camerademo.R
import com.example.camerademo.managers.CameraManager
import com.example.camerademo.widget.ArResDialog
import kotlinx.android.synthetic.main.activity_ar.*
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : BaseActivity() {

    private val cameraManager: CameraManager by lazy {
        CameraManager(this, R.id.cameraContainer)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        cameraManager.openFrontCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraManager.releaseCamera()
    }

    fun showFilter(view: View) {
        arFilterView.color = Color.parseColor("#4011eeee")
    }

    fun hideFilter(view: View) {
        arFilterView.color = Color.TRANSPARENT
    }
}
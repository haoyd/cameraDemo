package com.example.camerademo

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.camerademo.entity.PageEntity

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initConfig()

        val title = intent?.getStringExtra("title")
        if (!TextUtils.isEmpty(title)) {
            setTitle(title)
        }
    }

    protected fun startPage(entity: PageEntity) {
        startPage(entity.cls, entity.name)
    }

    private fun startPage(cls: Class<*>?, pageName: String) {
        val intent = Intent(this, cls)
        intent.putExtra("title", pageName)
        startActivity(intent)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun initConfig() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}
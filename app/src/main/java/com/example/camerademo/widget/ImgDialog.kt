package com.example.camerademo.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import com.example.camerademo.R
import kotlinx.android.synthetic.main.dialog_img.*

/**
 * User: HYD
 * Date: 2022/1/10
 * Desc:
 */
class ImgDialog(context: Context) : Dialog(context) {

    init {
        setContentView(R.layout.dialog_img)

        closeView.setOnClickListener {
            dismiss()
        }
    }

    fun showImg(bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
        show()
    }

}
package com.example.camerademo.utils

import android.widget.Toast

fun showToast(msg: String) {
    Toast.makeText(Env.app, msg, Toast.LENGTH_SHORT).show()
}
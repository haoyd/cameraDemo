package com.example.camerademo.extentions

import com.blankj.utilcode.util.LogUtils

/**
 * User: HYD
 * Date: 2022/1/7
 * Desc:
 */

/**
 * 捕获异常
 */
inline fun tryWith(crossinline body: () -> Unit) {
    try {
        body()
    } catch (e: Exception) {
        LogUtils.e(e)
    }
}
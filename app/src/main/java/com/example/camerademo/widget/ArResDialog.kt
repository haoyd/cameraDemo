package com.example.camerademo.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ScreenUtils
import com.example.camerademo.R
import com.example.camerademo.entity.ArEntity
import com.example.camerademo.managers.ArResManager
import kotlinx.android.synthetic.main.dialog_ar_res.*

/**
 * User: HYD
 * Date: 2022/1/12
 * Desc:
 */
class ArResDialog(context: Context) : Dialog(context, R.style.BottomDialogStyle) {

    var itemListener: ((entity: ArEntity) -> Unit)? = null

    private var resList: List<ArEntity> = ArResManager.resList
    private val adapter = MyAdapter()

    private val dp100 = ConvertUtils.dp2px(100f)
    private val dp5 = ConvertUtils.dp2px(5f)

    init {
        setContentView(R.layout.dialog_ar_res)
        window?.attributes?.apply {
            gravity = Gravity.BOTTOM
            width = WindowManager.LayoutParams.MATCH_PARENT
        }
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lm = LinearLayoutManager(context)
        lm.orientation = RecyclerView.HORIZONTAL
        arListView.layoutManager = lm
        arListView.adapter = adapter
    }

    private class MyHolder(val v: ImageView): RecyclerView.ViewHolder(v)

    private inner class MyAdapter : RecyclerView.Adapter<MyHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            val view = ImageView(context)
            val lp = ViewGroup.MarginLayoutParams(dp100, dp100)
            lp.setMargins(0, 0, dp5, 0)
            view.layoutParams = lp
            return MyHolder(view)
        }

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            holder.v.setImageResource(resList[position].res)
            holder.v.setOnClickListener {
                itemListener?.invoke(resList[position])
            }
        }

        override fun getItemCount(): Int {
            return if (resList.isNullOrEmpty()) 0 else resList.size
        }
    }
}
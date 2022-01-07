package com.example.camerademo

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.camerademo.entity.PageEntity
import com.example.camerademo.utils.Env
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), AdapterView.OnItemClickListener {

    private val pages = listOf(
        PageEntity("基础功能", SimpleCameraActivity::class.java),
    )

    private val pageNameList: List<String>
        get() {
            val list = mutableListOf<String>()
            for (page in pages) {
                list.add(page.name)
            }
            return list
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Env.app = application

        title = "相机"
        mListView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, pageNameList)
        mListView.onItemClickListener = this
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        startPage(pages[position])
    }
}
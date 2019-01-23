package com.kiffon.filter.demo

import android.app.AlertDialog
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.widget.Toast
import com.plumcookingwine.tree.ItemStatus
import com.plumcookingwine.tree.dao.TreeListDao
import com.kiffon.filter.FilterAdapter
import com.kiffon.filter.dao.FilterDao
import com.kiffon.filter.dao.OptionDao
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val mData = mutableListOf<TreeListDao<FilterDao, OptionDao>>()

    private lateinit var mAdapter: FilterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAdapter = FilterAdapter(mData)

        mRecyclerView.let {
            val manager = GridLayoutManager(this, 3)

            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (mAdapter.getItemViewType(position) == ItemStatus.VIEW_TYPE_SUB_ITEM) {
                        return 1
                    }
                    return 3
                }
            }

            it.layoutManager = manager

            it.adapter = mAdapter
//
            mAdapter.setData(initData())



            mBtnConfirm.setOnClickListener {
                val treeListDao = mAdapter.getSelectOptionDao()
                var str = ""
                treeListDao?.map {
                    str += "\n${it.groupDao.mTitle}"
                    Log.e("TAG", it.groupDao.mTitle)
                    it.groupDao.mCheckOption?.map {
                        str += "\n\t${it.mTitle}"
                        Log.e("TAG", "\t\t" + it.mTitle)
                    }
                }

                AlertDialog.Builder(this).setTitle("您选择了：")
                        .setCancelable(true)
                        .setMessage(str)
                        .create()
                        .show()
//                Toast.makeText(this, str, Toast.LENGTH_SHORT).show()

            }
        }

    }

    private fun initData(): MutableList<TreeListDao<FilterDao, OptionDao>> {
        val list = mutableListOf<TreeListDao<FilterDao, OptionDao>>()

        for (i in 0..5) {
            val dao = TreeListDao<FilterDao, OptionDao>()
            val filterDao = FilterDao(i, "title$i", "title$i", null)
            val subs = mutableListOf<OptionDao>()
            for (j in 0..20) {
                val subListDao = OptionDao(j, "o${j}t${i}", "o${j}t${i}", false)
                subs.add(subListDao)
            }

            dao.groupDao = filterDao
            dao.subList = subs

            if (i % 2 == 0) {
                dao.isMultiCheck = true
            }

            list.add(dao)
        }

        return list
    }
}

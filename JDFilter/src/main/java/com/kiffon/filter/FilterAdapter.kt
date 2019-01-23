@file:Suppress("DEPRECATION")

package com.kiffon.filter

import android.widget.CheckBox
import android.widget.TextView
import com.plumcookingwine.tree.adapter.AbsTreeListAdapter
import com.plumcookingwine.tree.dao.TreeListDao
import com.kiffon.filter.dao.FilterDao
import com.kiffon.filter.dao.OptionDao

/**
 * Created by Kevin on 2019/1/22.
 */
class FilterAdapter(private val treeList: MutableList<TreeListDao<FilterDao, OptionDao>>?) :
        AbsTreeListAdapter<FilterDao, OptionDao>(treeList) {

    private var selectList: MutableList<TreeListDao<FilterDao, OptionDao>> = mutableListOf()

    override fun groupLayoutId(): Int {
        return R.layout.item_tree_title_layout
    }

    override fun subLayoutId(): Int {
        return R.layout.item_tree_child_layout
    }

    override fun onBindGroupHolder(holder: GroupItemViewHolder?, k: FilterDao?, groupIndex: Int, position: Int) {
        val view = holder?.itemView

        view?.setOnClickListener {
            toggleMoreHide(groupIndex)
        }

        val mTvFilter = view?.findViewById<TextView>(R.id.mTvFilter)
        mTvFilter?.text = k?.mTitle ?: "null"

        val mCbDesc = view?.findViewById<CheckBox>(R.id.mCbDesc)
        var selectedOptions = ""
        treeList?.get(groupIndex)?.subList?.map {
            if (it.isCheck) {
                selectedOptions += it.mTitle + ","
            }
        }
        if (selectedOptions.isEmpty().not()) {
            mCbDesc?.text = selectedOptions.substring(0, selectedOptions.lastIndex)
        } else {
            mCbDesc?.text = ""
        }
    }

    override fun onBindSubHolder(holder: SubItemViewHolder?, v: OptionDao?, subIndex: Int, groupIndex: Int, position: Int) {
        if (v == null) return
        val view = holder?.itemView
        val mCbSub = view?.findViewById<CheckBox>(R.id.mCbSub)
        mCbSub?.text = v.mTitle
        mCbSub?.isChecked = v.isCheck
        mCbSub?.setOnClickListener {
            val check = mCbSub.isChecked
            if (treeList!![groupIndex].isMultiCheck) {
                treeList[groupIndex].subList?.map { its ->
                    its.isCheck = false
                }
            }

            v.isCheck = check
            notifyDataSetChanged()

        }
    }

    fun getSelectOptionDao(): List<TreeListDao<FilterDao, OptionDao>>? {

        selectList.clear()

        treeList?.map { item ->

            val list = mutableListOf<OptionDao>()
            item.subList.map {
                if (it.isCheck) {
                    list.add(it)
                }
            }
            if (list.size > 0) {
                item.groupDao.mCheckOption = list
                selectList.add(item)
            }

        }

        return selectList
    }

}
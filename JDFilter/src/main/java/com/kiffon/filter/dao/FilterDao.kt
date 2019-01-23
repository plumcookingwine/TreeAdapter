package com.kiffon.filter.dao

/**
 * Created by Kevin on 2019/1/22.
 */
data class FilterDao(
        var _ID: Int = 0,
        var mTitle: String = "",
        var mDesc: String = "",
        var mCheckOption: List<OptionDao>?
)
package com.plumcookingwine.tree.dao;

import java.util.List;

/**
 * Created by Kevin on 2019/1/22.
 */
public class TreeListDao<K, V> {

    /**
     *  配置项
     */
    // 是否可展开收起
    private boolean isEnableExpand = true;
    // 默认是否展开状态
    private boolean isExpand = false;
    // 是否支持多选
    private boolean isMultiCheck = false;
    // 收起状态最少展示子item数量
    private int minCount = 6;

    /**
     * 数据
     */
    // 组
    private K groupDao;
    // 子
    private List<V> subList;

    public TreeListDao(){}

    public TreeListDao(K groupDao, List<V> subList) {
        this.groupDao = groupDao;
        this.subList = subList;
    }

    public K getGroupDao() {
        return groupDao;
    }

    public void setGroupDao(K groupDao) {
        this.groupDao = groupDao;
    }

    public List<V> getSubList() {
        return subList;
    }

    public void setSubList(List<V> subList) {
        this.subList = subList;
    }

    public boolean isEnableExpand() {
        return isEnableExpand;
    }

    public void setEnableExpand(boolean enableExpand) {
        isEnableExpand = enableExpand;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public int getMinCount() {
        return minCount;
    }

    public void setMinCount(int minCount) {
        this.minCount = minCount;
    }

    public boolean isMultiCheck() {
        return isMultiCheck;
    }

    public void setMultiCheck(boolean multiCheck) {
        isMultiCheck = multiCheck;
    }
}

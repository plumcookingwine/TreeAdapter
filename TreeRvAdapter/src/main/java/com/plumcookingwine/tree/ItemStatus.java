package com.plumcookingwine.tree;

/**
 * Created by Kevin on 2019/1/22.
 * <p>
 * 用来存储item的状态， 并计算真实索引值
 */
public class ItemStatus {
    // 一级item
    public static final int VIEW_TYPE_GROUP_ITEM = 0x00000000;
    // 二级item
    public static final int VIEW_TYPE_SUB_ITEM = 0x00000001;

    // 当前item类型
    private int mViewType;
    // 组索引
    private int mGroupItemIndex;
    // 子索引
    private int mSubItemIndex;

    public ItemStatus() {
    }

    public ItemStatus(int mViewType, int mGroupItemIndex, int mSubItemIndex) {
        this.mViewType = mViewType;
        this.mGroupItemIndex = mGroupItemIndex;
        this.mSubItemIndex = mSubItemIndex;
    }

    public int getViewType() {
        return mViewType;
    }

    public void setViewType(int mViewType) {
        this.mViewType = mViewType;
    }

    public int getGroupItemIndex() {
        return mGroupItemIndex;
    }

    public void setGroupItemIndex(int mGroupItemIndex) {
        this.mGroupItemIndex = mGroupItemIndex;
    }

    public int getSubItemIndex() {
        return mSubItemIndex;
    }

    public void setSubItemIndex(int mSubItemIndex) {
        this.mSubItemIndex = mSubItemIndex;
    }
}

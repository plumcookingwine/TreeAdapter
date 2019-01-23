package com.plumcookingwine.tree.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plumcookingwine.tree.ItemStatus;
import com.plumcookingwine.tree.dao.TreeListDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 2019/1/22.
 */
public abstract class AbsTreeListAdapter<K, V> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TreeListDao<K, V>> mTreeList;

    private List<Boolean> mGroupItemStatus;

    public AbsTreeListAdapter(List<TreeListDao<K, V>> treeList) {
        this.mTreeList = treeList;
    }

    public abstract int groupLayoutId();

    public abstract int subLayoutId();

    public abstract void onBindGroupHolder(GroupItemViewHolder holder, K k, int groupIndex, int position);

    public abstract void onBindSubHolder(SubItemViewHolder holder, V v, int subIndex, int groupIndex, int position);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        RecyclerView.ViewHolder viewHolder;

        if (viewType == ItemStatus.VIEW_TYPE_GROUP_ITEM) {
            view = inflater.inflate(groupLayoutId(), parent, false);
            viewHolder = new GroupItemViewHolder(view);
        } else {
            view = inflater.inflate(subLayoutId(), parent, false);
            viewHolder = new SubItemViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        ItemStatus itemStatus = getItemStatusByPosition(position);

        TreeListDao<K, V> data = mTreeList.get(itemStatus.getGroupItemIndex());

        int groupIndex = itemStatus.getGroupItemIndex();
        int subIndex = itemStatus.getSubItemIndex();

        if (itemStatus.getViewType() == ItemStatus.VIEW_TYPE_GROUP_ITEM) {
            GroupItemViewHolder holder = (GroupItemViewHolder) viewHolder;

            onBindGroupHolder(holder, data.getGroupDao(), groupIndex, position);
        } else if (itemStatus.getViewType() == ItemStatus.VIEW_TYPE_SUB_ITEM) {
            try {
                SubItemViewHolder holder = (SubItemViewHolder) viewHolder;
                onBindSubHolder(holder, data.getSubList().get(subIndex), subIndex, groupIndex, position);
            } catch (Exception e) {
//                Log.e("TAG", "position === " + position + ";subIndex === " + subIndex);
            }

        }

    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        if (0 == mGroupItemStatus.size()) {
            return itemCount;
        }
        for (int i = 0; i < mTreeList.size(); i++) {
            itemCount++;
            int subSize = mTreeList.get(i).getSubList().size();
            if (mGroupItemStatus.get(i)) {
                itemCount += subSize;
            } else {
                int minCount = mTreeList.get(i).getMinCount();
                itemCount += subSize > minCount ? minCount : subSize;
            }
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemStatusByPosition(position).getViewType();
    }

    public void setData(List<TreeListDao<K, V>> list) {
        this.mTreeList.clear();
        this.mTreeList.addAll(list);
        initGroupItemStatus();
        notifyDataSetChanged();
    }

    public void toggleMoreHide(int groupIndex) {
        if (mTreeList.get(groupIndex).isEnableExpand()) {
            mGroupItemStatus.set(groupIndex, !mGroupItemStatus.get(groupIndex));
            notifyDataSetChanged();
        }
    }

    private void initGroupItemStatus() {
        if (mGroupItemStatus == null) {
            mGroupItemStatus = new ArrayList<>();
        }
        mGroupItemStatus.clear();
        for (TreeListDao<K, V> item : mTreeList) {
            mGroupItemStatus.add(!item.isEnableExpand() || (item.isEnableExpand() && item.isExpand()));
        }
    }

    private ItemStatus getItemStatusByPosition(int position) {

        ItemStatus itemStatus = new ItemStatus();
        int itemCount = 0;
        int i = 0;

        while (i < mGroupItemStatus.size()) {

            if (itemCount == position) {
                itemStatus.setViewType(ItemStatus.VIEW_TYPE_GROUP_ITEM);
                itemStatus.setGroupItemIndex(i);
                break;
            } else if (itemCount > position) {
                itemStatus.setViewType(ItemStatus.VIEW_TYPE_SUB_ITEM);
                itemStatus.setGroupItemIndex(i - 1);
                TreeListDao<K, V> dao = mTreeList.get(i - 1);
                int subSize = dao.getSubList().size();
                int temp = itemCount - subSize;
//                Log.i("TAG", "\ntemp === " + temp + ";itemcount === " + itemCount + ";subsize === " + subSize + ";pos === " + position);
                if (!mGroupItemStatus.get(i - 1)) {
                    int ind;
                    if (subSize > dao.getMinCount()) {
                        ind = position - temp - subSize + dao.getMinCount();
                    } else {
                        ind = position - temp;
                    }
                    itemStatus.setSubItemIndex(ind);
                } else {
                    itemStatus.setSubItemIndex(position - temp);
                }
                break;
            }
            TreeListDao<K, V> dao = mTreeList.get(i);
            int subSize = dao.getSubList().size();
            int minCount = dao.getMinCount();
            int realCount = subSize > minCount ? minCount : subSize;
            itemCount += realCount + 1;
            if (mGroupItemStatus.get(i)) {
                if (subSize > minCount) {
                    itemCount += (subSize - minCount);
                }
            }
            i++;
        }

        if (i >= mGroupItemStatus.size()) {
            itemStatus.setViewType(ItemStatus.VIEW_TYPE_SUB_ITEM);
            itemStatus.setGroupItemIndex(i - 1);
            int subSize = mTreeList.get(i - 1).getSubList().size();
            int minCount = mTreeList.get(i - 1).getMinCount();
            int temp = itemCount - subSize;
//            Log.i("TAG", "\ntemp2 === " + temp + ";itemcount === " + itemCount + ";subsize === " + subSize + ";pos === " + position);
            if (!mGroupItemStatus.get(i - 1)) {
                int ind;
                if (subSize > minCount) {
                    ind = position - temp - subSize + minCount;
                } else {
                    ind = position - temp;
                }
                itemStatus.setSubItemIndex(ind);
            } else {
                itemStatus.setSubItemIndex(position - temp);
            }
        }

        return itemStatus;
    }


    public class GroupItemViewHolder extends RecyclerView.ViewHolder {
        GroupItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class SubItemViewHolder extends RecyclerView.ViewHolder {
        SubItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}

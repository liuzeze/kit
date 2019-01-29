package lz.com.tools.recycleview.checked;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.recyclerview.widget.RecyclerView;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-28       创建class
 */
public class MultiUnCancelCheckedHelper<T extends Object> extends CheckHelper<T> {


    private T mUnCancelItem;

    @Override
    public void onSelect(RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, T obj, int position) {
        if (mOnCheckedListener != null) {
            if (!checkedList.containsKey(obj)) {
                //点击选中
                if (Objects.equals(mUnCancelItem, obj)) {
                    for (Map.Entry<T, RecyclerView.ViewHolder> entry : checkedList.entrySet()) {
                        mOnCheckedListener.onUnChecked(entry.getValue(), entry.getKey());
                    }
                    checkedList.clear();
                } else {
                    if (checkedList.containsKey(mUnCancelItem)) {
                        RecyclerView.ViewHolder viewHolder = checkedList.get(mUnCancelItem);
                        if (viewHolder != null) {
                            checkedList.remove(mUnCancelItem);
                            mOnCheckedListener.onUnChecked(viewHolder, mUnCancelItem);
                        } else {
                            checkedList.remove(mUnCancelItem);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
                checkedList.put(obj, holder);
                mOnCheckedListener.onChecked(holder, obj);


            } else {
                //点击取消
                if (!Objects.equals(mUnCancelItem, obj)) {
                    checkedList.remove(obj);
                    mOnCheckedListener.onUnChecked(holder, obj);
                    if (checkedList.size() == 0) {
                        checkedList.put(mUnCancelItem, null);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            mOnCheckedListener.onSelectitem(getCheckedList());

        }

    }

    @Override
    public void isChecked(RecyclerView.ViewHolder holder, T obj, int position) {
        if (checkedList.containsKey(obj)) {
            checkedList.put(obj, holder);
            if (mOnCheckedListener != null) {
                mOnCheckedListener.onChecked(holder, obj);
            }
        } else {
            if (mOnCheckedListener != null) {
                mOnCheckedListener.onUnChecked(holder, obj);
            }
        }
    }


    public void setDefaultItem(T... objs) {
        for (T obj : objs) {
            if (!checkedList.containsKey(obj)) {
                checkedList.put(obj, null);
            }
        }
        if (mOnCheckedListener != null) {
            mOnCheckedListener.onSelectitem(getCheckedList());
        }
    }

    public void setDefaultItem(List<T> objs) {
        for (T obj : objs) {
            if (!checkedList.containsKey(obj)) {
                checkedList.put(obj, null);
            }
        }
        if (mOnCheckedListener != null) {
            mOnCheckedListener.onSelectitem(getCheckedList());
        }
    }

    public void setUnCancelItem(T unCancelItem) {
        mUnCancelItem = unCancelItem;
        if (!checkedList.containsKey(mUnCancelItem)) {
            checkedList.put(mUnCancelItem, null);
        }
        if (mOnCheckedListener != null) {
            mOnCheckedListener.onSelectitem(getCheckedList());
        }
    }

    @Override
    public ArrayList<T> getCheckedList() {
        ArrayList<T> checkedList = super.getCheckedList();
        checkedList.remove(mUnCancelItem);
        return checkedList;
    }
}

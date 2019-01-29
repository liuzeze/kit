package lz.com.tools.recycleview.checked;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-28       创建class
 */
public class MultiCheckedHelper<T extends Object> extends CheckHelper<T> {


    @Override
    public void onSelect(RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, T obj, int position) {
        if (mOnCheckedListener != null) {
            if (!unCancelLIst.contains(obj)) {
                if (!checkedList.containsKey(obj)) {
                    checkedList.put(obj, null);
                    mOnCheckedListener.onChecked(holder, obj);
                } else {
                    checkedList.remove(obj);
                    mOnCheckedListener.onUnChecked(holder, obj);
                }
            }

            mOnCheckedListener.onSelectitem(getCheckedList());

        }

    }

    @Override
    public void isChecked(RecyclerView.ViewHolder holder, T obj, int position) {
        if (checkedList.containsKey(obj)) {
            checkedList.put(obj, null);
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

    public void setUnCancelItem(T... unCancelItem) {
        for (T t : unCancelItem) {
            if (!unCancelLIst.contains(t)) {
                unCancelLIst.add(t);
                checkedList.put(t, null);
            }
        }
        if (mOnCheckedListener != null) {
            mOnCheckedListener.onSelectitem(getCheckedList());
        }
    }


}

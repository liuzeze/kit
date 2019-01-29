package lz.com.tools.recycleview.checked;

import java.util.HashMap;
import java.util.List;

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
            if (!checkedList.contains(obj)) {
                if (checkedList.contains(mUnCancelItem)) {
                    checkedList.remove(mUnCancelItem);
                    adapter.notifyDataSetChanged();
                }
                checkedList.add(obj);
                mOnCheckedListener.onChecked(holder, obj);
            } else {
                if (mUnCancelItem != obj) {
                    checkedList.remove(obj);
                    mOnCheckedListener.onUnChecked(holder, obj);
                    if (checkedList.size() == 0) {
                        checkedList.add(mUnCancelItem);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }

    }

    @Override
    public void isChecked(RecyclerView.ViewHolder holder, T obj, int position) {
        if (checkedList.contains(obj)) {
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
            if (!checkedList.contains(obj)) {
                checkedList.add(obj);
            }
        }
    }

    public void setDefaultItem(List<T> objs) {
        for (T obj : objs) {
            if (!checkedList.contains(obj)) {
                checkedList.add(obj);
            }
        }

    }

    public void setUnCancelItem(T unCancelItem) {
        mUnCancelItem = unCancelItem;
        if (!checkedList.contains(mUnCancelItem)) {
            checkedList.add(mUnCancelItem);
        }
    }


}

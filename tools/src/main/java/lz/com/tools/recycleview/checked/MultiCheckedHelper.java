package lz.com.tools.recycleview.checked;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-28       创建class
 */
public class MultiCheckedHelper<T extends Object> extends CheckHelper<T> {


    @Override
    public void onSelect(RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, T obj, int position) {
        if (mOnCheckedListener != null) {
            if (!unCancelLIst.contains(obj)) {
                if (!checkedList.contains(obj)) {
                    checkedList.add(obj);
                    mOnCheckedListener.onChecked(holder, obj);
                } else {
                    checkedList.remove(obj);
                    mOnCheckedListener.onUnChecked(holder, obj);
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

    public void setUnCancelItem(T... unCancelItem) {
        for (T t : unCancelItem) {
            if (!unCancelLIst.contains(t)) {
                unCancelLIst.add(t);
                checkedList.add(t);
            }
        }
    }


}

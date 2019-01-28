package lz.com.tools.recycleview.checked;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-28       创建class
 */
public class MultiCheckedHelper<T extends Object> extends CheckHelper<T> {


    @Override
    public void onSelect(RecyclerView.ViewHolder holder, T obj, int position) {
        if (mOnCheckedListener != null) {
            if (!checkedList.contains(obj)) {
                checkedList.add(obj);
                mOnCheckedListener.onChecked(holder, obj);
            } else {
                checkedList.remove(obj);
                mOnCheckedListener.onUnChecked(holder, obj);
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
            checkedList.add(obj);
        }
    }

    public void setDefaultItem(List<T> objs) {
        checkedList.addAll(objs);

    }


}

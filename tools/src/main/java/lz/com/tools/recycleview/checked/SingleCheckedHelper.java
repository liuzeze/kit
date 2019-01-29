package lz.com.tools.recycleview.checked;

import java.util.ArrayList;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-28       创建class
 */
public class SingleCheckedHelper<T extends Object> extends CheckHelper<T> {
    private boolean isCanCancel = true;

    @Override
    public void onSelect(RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, T obj, int position) {
        if (mOnCheckedListener != null) {
            if (checkedList.containsKey(obj)) {
                //已选的
                if (isCanCancel) {
                    mOnCheckedListener.onUnChecked(holder, obj);
                    checkedList.clear();
                }
            } else {
                //没有选的
                for (Map.Entry<T, RecyclerView.ViewHolder> entry : checkedList.entrySet()) {
                    if (entry.getValue() != null) {
                        mOnCheckedListener.onUnChecked(entry.getValue(), obj);
                    }
                    break;
                }
                checkedList.clear();
                checkedList.put(obj, holder);

                mOnCheckedListener.onChecked(holder, obj);
            }

            mOnCheckedListener.onSelectitem(getCheckedList());
        }
    }

    @Override
    public void isChecked(RecyclerView.ViewHolder holder, T obj, int position) {
        if (mOnCheckedListener != null) {
            if (checkedList.containsKey(obj)) {
                checkedList.put(obj, holder);
                mOnCheckedListener.onChecked(holder, obj);
            } else {
                mOnCheckedListener.onUnChecked(holder, obj);
            }
        }
    }

    public SingleCheckedHelper<T> setCanCancel(boolean canCancel) {
        isCanCancel = canCancel;
        return this;
    }

    public void setDefaultItem(T obj) {
        checkedList.clear();
        checkedList.put(obj, null);
        if (mOnCheckedListener != null) {
            mOnCheckedListener.onSelectitem(getCheckedList());
        }
    }
}

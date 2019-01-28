package lz.com.tools.recycleview.checked;

import androidx.recyclerview.widget.RecyclerView;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-28       创建class
 */
public class SingleCheckedHelper<T extends Object> extends CheckHelper<T> {
    private RecyclerView.ViewHolder preHolder;
    private boolean isCanCancel = true;

    @Override
    public void onSelect(RecyclerView.ViewHolder holder, T obj, int position) {
        if (mOnCheckedListener != null) {
            if (checkedList.contains(obj)) {
                //已选的
                if (isCanCancel) {
                    mOnCheckedListener.onUnChecked(holder, obj);
                    checkedList.clear();
                    preHolder = null;
                }
            } else {
                //没有选的
                checkedList.clear();
                checkedList.add(obj);

                if (preHolder != null) {
                    mOnCheckedListener.onUnChecked(preHolder, obj);
                }
                preHolder = holder;
                mOnCheckedListener.onChecked(holder, obj);
            }
        }
    }

    @Override
    public void isChecked(RecyclerView.ViewHolder holder, T obj, int position) {
        if (mOnCheckedListener != null) {
            if (checkedList.contains(obj)) {
                preHolder = holder;
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
        checkedList.add(obj);
    }

    public T getObj() {
        return checkedList.size() > 0 ? checkedList.get(0) : null;
    }
}

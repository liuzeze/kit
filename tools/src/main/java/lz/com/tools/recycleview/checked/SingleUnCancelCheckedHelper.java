package lz.com.tools.recycleview.checked;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-28       创建class
 */
public class SingleUnCancelCheckedHelper<T extends Object> extends CheckHelper<T> {


    private RecyclerView.ViewHolder preHolder;
    private T mUnCancelItem;


    @Override
    public void onSelect(RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, T obj, int position) {
        if (mOnCheckedListener != null) {

            if (checkedList.contains(obj)) {
                //取消选中
                mOnCheckedListener.onUnChecked(holder, obj);
                checkedList.clear();
                preHolder = null;
                //选中默认第一个的条目
                checkedList.add(mUnCancelItem);
                adapter.notifyItemChanged(0);

            } else {
                //选中条目
                checkedList.clear();
                checkedList.add(obj);

                if (preHolder != null) {
                    mOnCheckedListener.onUnChecked(preHolder, obj);
                }
                preHolder = holder;
                mOnCheckedListener.onChecked(holder, obj);
            }
//            adapter.notifyItemChanged(0);
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


    public void setDefaultItem(T obj) {
        checkedList.clear();
        checkedList.add(obj);
    }

    public T getObj() {
        return checkedList.size() > 0 ? checkedList.get(0) : null;
    }


    public void setUnCancelItem(T unCancelItem) {
        mUnCancelItem = unCancelItem;
        if (!checkedList.contains(mUnCancelItem)) {
            checkedList.add(mUnCancelItem);
        }
    }


}

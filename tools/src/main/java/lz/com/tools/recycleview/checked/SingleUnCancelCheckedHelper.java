package lz.com.tools.recycleview.checked;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.recyclerview.widget.RecyclerView;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-28       创建class
 */
public class SingleUnCancelCheckedHelper<T extends Object> extends CheckHelper<T> {


    private T mUnCancelItem;


    @Override
    public void onSelect(RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, T obj, int position) {
        if (mOnCheckedListener != null) {

            if (checkedList.containsKey(obj)) {
                //取消选中
                if (!Objects.equals(mUnCancelItem, obj)) {
                    mOnCheckedListener.onUnChecked(holder, obj);
                    checkedList.clear();
                    //选中默认第一个的条目
                    checkedList.put(mUnCancelItem, null);
                    adapter.notifyDataSetChanged();
                }
            } else {
                //选中条目
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


    public void setDefaultItem(T obj) {
        checkedList.clear();
        checkedList.put(obj, null);
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

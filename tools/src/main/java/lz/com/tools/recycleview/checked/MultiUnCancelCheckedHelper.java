package lz.com.tools.recycleview.checked;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.recyclerview.widget.RecyclerView;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-28       多选列表
 */
public class MultiUnCancelCheckedHelper<T extends Object> extends CheckHelper<T> {


    private T mAlwaysSelectItem;
    private RecyclerView.ViewHolder mAlwaysSelecHolder;

    @Override
    public void onSelect(RecyclerView.ViewHolder holder, T obj, int position) {
        if (mOnCheckedListener != null) {
            //点击未选中的条目
            if (!checkedList.containsKey(obj)) {
                //点击选中
                if (Objects.equals(mAlwaysSelectItem, obj)) {
                    //点击的是互斥的的  需要把其他条目置为未选中
                    for (Map.Entry<T, RecyclerView.ViewHolder> entry : checkedList.entrySet()) {
                        mOnCheckedListener.onChecked(entry.getValue(), entry.getKey(),false);
                    }
                    checkedList.clear();
                    checkedList.put(obj, holder);
                    mOnCheckedListener.onChecked(holder, obj,true);
                } else {
                    //点击互斥条目之外的条目
                    if (checkedList.containsKey(mAlwaysSelectItem)) {
                        RecyclerView.ViewHolder viewHolder = checkedList.get(mAlwaysSelectItem);
                        if (viewHolder != null) {
                            checkedList.remove(mAlwaysSelectItem);
                            mOnCheckedListener.onChecked(viewHolder, mAlwaysSelectItem,false);
                        }
                    }
                    checkedList.put(obj, holder);
                    mOnCheckedListener.onChecked(holder, obj,true);
                }

            } else {
                //点击已选中的条目  要取消选中
                if (!Objects.equals(mAlwaysSelectItem, obj)) {
                    checkedList.remove(obj);
                    mOnCheckedListener.onChecked(holder, obj,false);
                    if (mAlwaysSelecHolder != null) {
                        if (checkedList.size() == 0) {
                            checkedList.put(mAlwaysSelectItem, mAlwaysSelecHolder);
                            mOnCheckedListener.onChecked(mAlwaysSelecHolder, mAlwaysSelectItem,true);
                        }
                    }
                }
            }

        }

    }

    @Override
    public void isChecked(RecyclerView.ViewHolder holder, T obj, int position) {
        if (checkedList.size() > 1) {
            checkedList.remove(mAlwaysSelectItem);
        }
        if (Objects.equals(mAlwaysSelectItem, obj)) {
            mAlwaysSelecHolder = holder;
        }
        if (mOnCheckedListener != null) {
            if (checkedList.containsKey(obj)) {
                checkedList.put(obj, holder);
                mOnCheckedListener.onChecked(holder, obj,true);
            } else {
                mOnCheckedListener.onChecked(holder, obj,false);

            }
        }
    }


    @Override
    public CheckHelper setMultiDefaultItem(T... objs) {
        for (T obj : objs) {
            if (!checkedList.containsKey(obj)) {
                checkedList.put(obj, null);
            }
        }
        return this;
    }

    @Override
    public void setMultiDefaultItem(List<T> objs) {
        for (T obj : objs) {
            if (!checkedList.containsKey(obj)) {
                checkedList.put(obj, null);
            }
        }

    }

    @Override
    public CheckHelper setAlwaysSelectItem(T... unCancelItem) {
        if (unCancelItem != null) {
            mAlwaysSelectItem = unCancelItem[0];
            if (!checkedList.containsKey(mAlwaysSelectItem)) {
                checkedList.put(mAlwaysSelectItem, null);
            }
        }
        return this;
    }

    @Override
    public ArrayList<T> getCheckedList() {
        ArrayList<T> checkedList = super.getCheckedList();
        checkedList.remove(mAlwaysSelectItem);
        return checkedList;
    }
}

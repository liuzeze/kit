package lz.com.tools.recycleview.checked;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-28       单选列表
 */
public class SingleCheckedHelper<T extends Object> extends CheckHelper<T> {

    private boolean mIsLastItemEnable = true;

    private T mAlwaysSelectItem;
    private RecyclerView.ViewHolder mAlwaysSelecHolder;


    @Override
    public void onSelect(RecyclerView.ViewHolder holder, T obj, int position) {
        if (mOnCheckedListener != null) {
            //点击已选中的条目
            if (checkedList.containsKey(obj)) {
                //最后一个不可取消
                if (mIsLastItemEnable) {
                    //取消选中
                    if (!Objects.equals(mAlwaysSelectItem, obj)) {
                        checkedList.clear();
                        mOnCheckedListener.onChecked(holder, obj, false);
                        //选中默认第一个的条目
                        if (mAlwaysSelectItem != null) {
                            checkedList.put(mAlwaysSelectItem, mAlwaysSelecHolder);
                            mOnCheckedListener.onChecked(mAlwaysSelecHolder, mAlwaysSelectItem, true);
                        }
                    }
                }
            } else {
                //选中条目 上一个取消选中
                for (Map.Entry<T, RecyclerView.ViewHolder> entry : checkedList.entrySet()) {
                    if (entry.getValue() != null) {
                        mOnCheckedListener.onChecked(entry.getValue(), obj, false);
                    }
                    break;
                }
                //当前选中
                checkedList.clear();
                checkedList.put(obj, holder);
                mOnCheckedListener.onChecked(holder, obj, true);
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
                mOnCheckedListener.onChecked(holder, obj, true);
            } else {
                mOnCheckedListener.onChecked(holder, obj, false);
            }
        }
    }


    @Override
    public CheckHelper setSingleDefaultItem(T obj) {
        checkedList.clear();
        checkedList.put(obj, null);

        return this;
    }

    @Override
    public CheckHelper setAlwaysSelectItem(T... alwaysSelectItem) {
        if (alwaysSelectItem != null) {
            mAlwaysSelectItem = alwaysSelectItem[0];
            if (!checkedList.containsKey(mAlwaysSelectItem)) {
                checkedList.put(mAlwaysSelectItem, null);
            }
        }
        return this;

    }

    @Override
    public CheckHelper<T> setLastItemEnable(boolean canCancel) {
        mIsLastItemEnable = canCancel;
        return this;
    }


    @Override
    public ArrayList<T> getCheckedList() {
        ArrayList<T> checkedList = super.getCheckedList();
        checkedList.remove(mAlwaysSelectItem);
        return checkedList;
    }
}

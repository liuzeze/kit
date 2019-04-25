package lz.com.tools.recycleview.checked;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public abstract class CheckHelper<T extends Object> {
    protected HashMap<T, RecyclerView.ViewHolder> checkedList = new HashMap<>();

    protected OnCheckedListener mOnCheckedListener;

    public abstract void onSelect(RecyclerView.ViewHolder holder, T obj, int position);

    public abstract void isChecked(RecyclerView.ViewHolder holder, T obj, int position);


    public CheckHelper<T> setOnCheckedListener(OnCheckedListener onCheckedListener) {
        mOnCheckedListener = onCheckedListener;
        return this;
    }

    public interface OnCheckedListener<D, V extends RecyclerView.ViewHolder> {
        void onChecked(V holder, D obj);

        void onUnChecked(V holder, D obj);

    }

    public ArrayList<T> getCheckedList() {
        return new ArrayList<>(checkedList.keySet());
    }


    public CheckHelper setDefaultItem(T... obj) {
        return this;
    }

    public CheckHelper setAlwaysSelectItem(T... unCancelItem) {
        return this;
    }

    public CheckHelper<T> setLastItemEnable(boolean canCancel) {
        return this;
    }

    public void setDefaultItem(List<T> objs) {

    }
}
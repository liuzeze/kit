package lz.com.tools.recycleview.checked;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public abstract class CheckHelper<T extends Object> {
    protected HashMap<T, RecyclerView.ViewHolder> checkedList = new HashMap<>();
    protected ArrayList<T> unCancelLIst = new ArrayList<>();

    protected OnCheckedListener mOnCheckedListener;

    public abstract void onSelect(RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, T obj, int position);

    public abstract void isChecked(RecyclerView.ViewHolder holder, T obj, int position);


    public CheckHelper<T> setOnCheckedListener(OnCheckedListener onCheckedListener) {
        mOnCheckedListener = onCheckedListener;
        return this;
    }

    public interface OnCheckedListener<D, V extends RecyclerView.ViewHolder> {
       abstract void onChecked(V holder, D obj);

        abstract void onUnChecked(V holder, D obj);

        abstract void onSelectitem(List<D> itemLists);
    }

    public ArrayList<T> getCheckedList() {
        return new ArrayList<>(checkedList.keySet());
    }
}
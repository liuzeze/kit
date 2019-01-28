package lz.com.tools.recycleview.checked;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


public abstract class CheckHelper<T extends Object> {
    protected ArrayList<T> checkedList = new ArrayList<>();

    protected OnCheckedListener mOnCheckedListener;

    public abstract void onSelect(RecyclerView.ViewHolder holder, T obj,int position);

    public abstract void isChecked(RecyclerView.ViewHolder holder, T obj,int position);


    public CheckHelper<T> setOnCheckedListener(OnCheckedListener onCheckedListener) {
        mOnCheckedListener = onCheckedListener;
        return this;
    }

    public interface OnCheckedListener<D, V extends RecyclerView.ViewHolder> {
        void onChecked(V holder, D obj);

        void onUnChecked(V holder, D obj);
    }
}
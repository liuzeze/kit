package lz.com.kit.aac;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.Nullable;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2018-08-22       创建class
 */
public class BaseBindAdapter<T, B extends ViewDataBinding> extends RecyclerView.Adapter<BaseBindAdapter.BindingViewHolder<B>> {


    private final int mVariableId;
    protected List<T> mData;
    protected int mLayoutResId;


    public BaseBindAdapter(@LayoutRes int layoutResId, int variableId, @Nullable List<T> data) {
        mData = data == null ? new ArrayList<>() : data;
        mLayoutResId = layoutResId;
        mVariableId = variableId;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        B binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), mLayoutResId, viewGroup, false);
        if (binding == null) {
            return new BindingViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(mLayoutResId, viewGroup, false));
        }
        return new BindingViewHolder(binding.getRoot());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder viewData, int i) {
        T t = mData.get(i);
        B binding = DataBindingUtil.getBinding(viewData.itemView);
        BindingViewHolder(binding, t);
        binding.executePendingBindings();
    }

    protected void BindingViewHolder(B binding, T item) {
        binding.setVariable(mVariableId, item);
    }


    public static class BindingViewHolder<H extends ViewDataBinding> extends RecyclerView.ViewHolder {
        public BindingViewHolder(View view) {
            super(view);
        }

    }


}

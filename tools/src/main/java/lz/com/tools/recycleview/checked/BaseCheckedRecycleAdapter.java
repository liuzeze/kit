package lz.com.tools.recycleview.checked;

import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽       2019-01-11       基类adapter  加header  footer
 * 加载更多 点击事件  长按事件
 */
public abstract class BaseCheckedRecycleAdapter<T, K extends BaseViewHolder> extends BaseRecycleAdapter<T, K> implements CheckHelper.OnCheckedListener<T, K> {
    protected CheckHelper<T> mCheckHelper;
    public static final int SINGLE = 1;
    public static final int MULTI = 3;

    public BaseCheckedRecycleAdapter(int layoutResId) {
        super(layoutResId);
        initCheckHelper(SINGLE);
    }

    public BaseCheckedRecycleAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        initCheckHelper(SINGLE);
    }

    public BaseCheckedRecycleAdapter(int layoutResId, int selectType) {
        super(layoutResId);
        initCheckHelper(selectType);
    }

    public BaseCheckedRecycleAdapter(int layoutResId, @Nullable List<T> data, int selectType) {
        super(layoutResId, data);
        initCheckHelper(selectType);
    }

    private void initCheckHelper(int selectType) {
        if (selectType == SINGLE) {
            mCheckHelper = new SingleCheckedHelper<T>();
        } else if (selectType == MULTI) {
            mCheckHelper = new MultiUnCancelCheckedHelper<T>();
        }else {
            mCheckHelper = new SingleCheckedHelper<T>();
        }
        mCheckHelper.setOnCheckedListener(this);
    }


    @Override
    protected void onBindView(final K holder, T item) {
        if (mCheckHelper != null) {
            mCheckHelper.isChecked(holder, item, holder.getLayoutPosition() - getHeaderLayoutCount());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getOnItemClickListener() != null) {
                    getOnItemClickListener().onItemClick(BaseCheckedRecycleAdapter.this, v, holder.getLayoutPosition() - getHeaderLayoutCount());
                }
                if (mCheckHelper != null) {
                    int position = holder.getLayoutPosition() - getHeaderLayoutCount();
                    mCheckHelper.onSelect(BaseCheckedRecycleAdapter.this, holder, getItem(position), position);
                }

            }
        });

    }

    public CheckHelper<T> getCheckHelper() {
        return mCheckHelper;
    }

    public ArrayList<T> getSelectitem() {
        if (mCheckHelper != null) {

            return mCheckHelper.getCheckedList();
        } else {
            return new ArrayList<>();
        }

    }
}

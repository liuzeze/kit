package lz.com.tools.linkedlist;

import android.view.View;

import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-10       创建class
 */
public abstract class LinkedAdapter2Config<T extends LinkBean> {

    public abstract int getLieanLayout();

    public int getGridLayout() {
        return -1;
    }

    public boolean isShowGrid() {
        return false;
    }


    public int setSpanCount() {
        return 1;
    }


    public abstract void onBindViewData(BaseViewHolder holder, T item, int position);


    public void onItemClickListener(T t, View view, int position) {

    }
}

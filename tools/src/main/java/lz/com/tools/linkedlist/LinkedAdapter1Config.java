package lz.com.tools.linkedlist;

import lz.com.tools.recycleview.adapter.BaseViewHolder;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-10       创建class
 */
public interface LinkedAdapter1Config < T extends  LinkBean>{

    int getItemLayout();


    void onBindViewData(BaseViewHolder holder, T item, int position);
}

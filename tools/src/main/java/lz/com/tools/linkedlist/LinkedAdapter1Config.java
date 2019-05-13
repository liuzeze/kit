package lz.com.tools.linkedlist;

import lz.com.tools.recycleview.adapter.BaseViewHolder;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-10       创建class
 */
public interface LinkedAdapter1Config {

    int getItemLayout();


    void onBindViewData(BaseViewHolder holder, LinkBean item, int position);
}

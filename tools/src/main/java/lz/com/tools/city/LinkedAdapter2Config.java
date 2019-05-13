package lz.com.tools.city;

import lz.com.tools.recycleview.adapter.BaseViewHolder;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-10       创建class
 */
public abstract class LinkedAdapter2Config {

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


    public abstract void onBindViewData(BaseViewHolder holder, LinkBean item, int position, boolean showGrid);


}

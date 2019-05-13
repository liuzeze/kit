package lz.com.tools.linkedlist;

import lz.com.tools.R;
import lz.com.tools.recycleview.adapter.BaseViewHolder;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-10       创建class
 */
public class DefaultAdapter2Config extends LinkedAdapter2Config {
    @Override
    public int getLieanLayout() {
        return R.layout.item_right_layout;
    }

    @Override
    public int getGridLayout() {
        return R.layout.item_right_layout;
    }

    @Override
    public void onBindViewData(BaseViewHolder holder, LinkBean item, int position, boolean showGrid) {
        holder.setText(R.id.tv_righttitle, item.getGroupTag());
        holder.setText(R.id.tv_sub_righttitle, item.getGroupTag());

    }
}

package lz.com.tools.linkedlist;

import lz.com.tools.R;
import lz.com.tools.recycleview.adapter.BaseViewHolder;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-10       创建class
 */
public class DefaultAdapter1Config  implements LinkedAdapter1Config {
    @Override
    public int getItemLayout() {
        return R.layout.item_left_text;
    }

    @Override
    public void onBindViewData(BaseViewHolder holder, LinkBean item, int position) {
        holder.setText(R.id.tv_lefttitle,item.getGroupTag());
    }


}

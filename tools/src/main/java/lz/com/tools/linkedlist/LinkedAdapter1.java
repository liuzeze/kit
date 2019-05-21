package lz.com.tools.linkedlist;

import androidx.annotation.Nullable;

import java.util.List;

import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-10       创建class
 */
public class LinkedAdapter1 extends BaseRecycleAdapter<LinkBean, BaseViewHolder> {

    private final LinkedAdapter1Config mConfig;
    private int mPosition = 0;

    public LinkedAdapter1 setPosition(int position) {
        if (mPosition != position) {

            notifyItemChanged(mPosition);
            mPosition = position;
            notifyItemChanged(mPosition);
        }
        return this;
    }

    public LinkedAdapter1(LinkedAdapter1Config config, @Nullable List<LinkBean> data) {
        super(config.getItemLayout(), data);
        mConfig = config;
    }

    @Override
    protected void onBindView(BaseViewHolder holder, LinkBean item) {
        int position = holder.getLayoutPosition() - getHeaderLayoutCount();
        mConfig.onBindViewData(holder, item, position);
        holder.itemView.setSelected(mPosition == position);
    }


}

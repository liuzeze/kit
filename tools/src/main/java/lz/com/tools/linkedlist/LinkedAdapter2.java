package lz.com.tools.linkedlist;

import androidx.annotation.Nullable;

import java.util.List;

import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-10       创建class
 */
public class LinkedAdapter2 extends BaseRecycleAdapter<LinkBean, BaseViewHolder> {

    private final LinkedAdapter2Config mConfig;

    public LinkedAdapter2(LinkedAdapter2Config config, @Nullable List<LinkBean> data) {
        super(config.isShowGrid()?config.getGridLayout():config.getLieanLayout(), data);
        mConfig = config;
    }

    @Override
    protected void onBindView(BaseViewHolder holder, LinkBean item) {
        mConfig.onBindViewData(holder, item, holder.getLayoutPosition() - getHeaderLayoutCount(),mConfig.isShowGrid());
    }


    public LinkedAdapter2Config getConfig() {
        return mConfig;
    }
}

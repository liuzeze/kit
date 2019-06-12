package lz.com.tools.linkedlist;

import android.view.ViewGroup;

import androidx.annotation.Nullable;

import java.util.List;

import lz.com.tools.recycleview.ReboundReyclerView;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-10       创建class
 */
public class LinkedAdapter2 extends BaseRecycleAdapter<LinkBean, BaseViewHolder> {

    private final LinkedAdapter2Config mConfig;
    private final ReboundReyclerView mRecyclerview;

    public LinkedAdapter2(LinkedAdapter2Config config, @Nullable List<LinkBean> data, ReboundReyclerView recyclerview) {
        super(config.isShowGrid() ? config.getGridLayout() : config.getLieanLayout(), data);
        mConfig = config;
        mRecyclerview = recyclerview;
    }

    @Override
    protected void onBindView(BaseViewHolder holder, LinkBean item) {
        int position = holder.getLayoutPosition() - getHeaderLayoutCount();
        if (mConfig.isShowGrid()) {
            try {
                ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                layoutParams.width = mRecyclerview.getMeasuredWidth() / mConfig.setSpanCount();

                if (position == getData().size() - 1) {
                    layoutParams.height = mRecyclerview.getMeasuredHeight();
                } else {
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                }

                holder.itemView.setLayoutParams(layoutParams);

            } catch (Exception e) {
                e.printStackTrace();
                holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }
        mConfig.onBindViewData(holder, item, position);
    }


    public LinkedAdapter2Config getConfig() {
        return mConfig;
    }
}

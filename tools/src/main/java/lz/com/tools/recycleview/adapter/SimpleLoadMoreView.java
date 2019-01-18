package lz.com.tools.recycleview.adapter;

import lz.com.tools.R;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-17       创建class
 */
public class SimpleLoadMoreView extends LoadMoreView {


    @Override
    public int getLayoutId() {
        return R.layout.list_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }

}

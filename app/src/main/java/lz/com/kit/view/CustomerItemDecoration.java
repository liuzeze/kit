package lz.com.kit.view;


import android.content.Context;
import android.graphics.Color;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.decoration.Divider;
import lz.com.tools.recycleview.decoration.DividerBuilder;
import lz.com.tools.recycleview.decoration.DividerItemDecoration;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2018-12-13       列表分割线
 */

public class CustomerItemDecoration extends DividerItemDecoration {

    public CustomerItemDecoration(Context context) {
        super(context);
    }

    @Override
    public Divider getDivider(RecyclerView parent, int itemPosition) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        Divider divider = null;
        if (layoutManager != null) {
            if (layoutManager instanceof GridLayoutManager) {
                int itemCount = -1;
                if (parent.getAdapter() instanceof BaseRecycleAdapter) {
                    BaseRecycleAdapter adapter = (BaseRecycleAdapter) parent.getAdapter();
                    itemPosition = itemPosition - adapter.getHeaderLayoutCount();
                    itemCount = adapter.getData().size();
                }
                if (itemCount == -1 || (itemPosition != -1 && itemPosition < itemCount)) {
                    switch (itemPosition % 4) {
                        case 0:
                            //每一行第一个显示rignt和bottom
                            divider = new DividerBuilder()
                                    .setBottomSideLine(true, 0xff666666, 10, 0, 0)
                                    .create();
                            break;
                        default:
                            //每一行第一个显示rignt和bottom
                            divider = new DividerBuilder()
                                    .setLeftSideLine(true, 0xff666666, 10, 0, 0)
                                    .setBottomSideLine(true, 0xff666666, 10, 0, 0)
                                    .create();
                            break;
                    }
                }
            } else if (layoutManager instanceof LinearLayoutManager) {
                divider = new DividerBuilder()
                        .setTopSideLine(true,Color.TRANSPARENT, 10, 0, 0)
                        .create();
            }
        }
        return divider;
    }
}
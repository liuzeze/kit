package lz.com.kit;

import java.util.ArrayList;

import butterknife.BindView;
import lz.com.tools.city.LinkBean;
import lz.com.tools.city.LinkRecyclerView;
import lz.com.tools.city.LinkedAdapter1Config;
import lz.com.tools.city.LinkedAdapter2Config;
import lz.com.tools.inject.InjectLayout;
import lz.com.tools.recycleview.adapter.BaseViewHolder;


@InjectLayout(layoutId = R.layout.activity_shop_category,titleName = "商品分类选择")
public class ShopCategoryActivity extends BaseKitActivity {

    @BindView(R.id.linkedRecycler)
    LinkRecyclerView linkedRecycler;


    @Override
    public void initData() {

        ArrayList<LinkBean> linkBeans = new ArrayList<>();
        getRightData(linkBeans, 10);
        getRightData(linkBeans, 3);
        getRightData(linkBeans, 6);
        getRightData(linkBeans, 17);
        getRightData(linkBeans, 5);
        getRightData(linkBeans, 2);
        getRightData(linkBeans, 9);
        getRightData(linkBeans, 4);

        linkedRecycler.setData(linkBeans)
                .setAdapter1Config(new LinkedAdapter1Config() {
                    @Override
                    public int getItemLayout() {
                        return lz.com.tools.R.layout.item_left_text;
                    }

                    @Override
                    public void onBindViewData(BaseViewHolder holder, LinkBean item, int position) {
                        holder.setText(lz.com.tools.R.id.tv_lefttitle, item.getGroupTag());
                    }
                })
                .setAdapter2Config(new LinkedAdapter2Config() {
                    @Override
                    public int getLieanLayout() {
                        return lz.com.tools.R.layout.item_left_text;
                    }

                    @Override
                    public int setSpanCount() {
                        return 4;
                    }

                    @Override
                    public boolean isShowGrid() {
                        return true;
                    }

                    @Override
                    public int getGridLayout() {
                        return lz.com.tools.R.layout.item_right_layout;
                    }

                    @Override
                    public void onBindViewData(BaseViewHolder holder, LinkBean item, int position, boolean showGrid) {
                        if (showGrid) {
                            holder.setText(lz.com.tools.R.id.tv_righttitle, item.getGroupTag());
                            holder.setText(lz.com.tools.R.id.tv_sub_righttitle, item.getTitleText());
                        } else {
                            holder.setText(lz.com.tools.R.id.tv_lefttitle, item.getTitleText());

                        }

                    }
                })
                .init();
    }

    private void getRightData(ArrayList<LinkBean> linkBeans, int count) {
        LinkBean e;
        for (int i = 0; i < count; i++) {
            e = new LinkBean();
            e.setGroupTag("标题" + count);
            e.setTitleText("条目" + i);
            linkBeans.add(e);
        }
    }

}



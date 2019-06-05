package lz.com.kit;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import lz.com.kit.bean.LinkListBean;
import lz.com.tools.linkedlist.LinkBean;
import lz.com.tools.linkedlist.LinkRecyclerView;
import lz.com.tools.linkedlist.LinkedAdapter1Config;
import lz.com.tools.linkedlist.LinkedAdapter2Config;
import lz.com.tools.inject.InjectLayout;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;
import lz.com.tools.recycleview.decoration.sticky.PowerfulStickyDecoration;
import lz.com.tools.recycleview.decoration.sticky.listener.PowerGroupListener;
import lz.com.tools.util.LzDisplayUtils;
import lz.com.tools.util.LzDp2Px;
import lz.com.tools.util.LzToast;


@InjectLayout(layoutId = R.layout.activity_shop_category, titleName = "商品分类选择")
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
        getRightData(linkBeans, 60);

        linkedRecycler.setData(linkBeans)
                .setItemDecoration(PowerfulStickyDecoration.Builder.init(new PowerGroupListener() {
                    @Override
                    public View getGroupView(int position) {
                        TextView textView = new TextView(mActivity);
                        textView.setBackgroundColor(Color.RED);
                        textView.setGravity(Gravity.CENTER_VERTICAL);
                        textView.setPadding(5, 5, 5, 5);
                        textView.setText(linkBeans.get(position).getGroupTag());
                        return textView;
                    }

                    @Override
                    public String getGroupName(int position) {
                        LinkBean linkBean = linkBeans.get(position);
                        return linkBean.getGroupTag();
                    }
                })
                        .setDivideHeight(0)
                        .setGroupHeight(LzDp2Px.dp2px(mActivity, 50))
                        .setDivideColor(Color.YELLOW)
                        .build())
                .setAdapter1Config(new LinkedAdapter1Config<LinkBean>() {
                    @Override
                    public int getItemLayout() {
                        return R.layout.item_left_text;
                    }

                    @Override
                    public void onBindViewData(BaseViewHolder holder, LinkBean item, int position) {
                        holder.setText(R.id.tv_lefttitle, item.getGroupTag());
                    }
                })
                .setAdapter2Config(new LinkedAdapter2Config<LinkListBean>() {
                    @Override
                    public int getLieanLayout() {
                        return R.layout.item_left_text;
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
                        return R.layout.item_right_layout;
                    }

                    @Override
                    public void onBindViewData(BaseViewHolder holder, LinkListBean item, int position) {
                        if (isShowGrid()) {
                            holder.setText(R.id.tv_righttitle, item.getGroupTag());
                            holder.setText(R.id.tv_sub_righttitle, item.getTitleText());
                        } else {
                            holder.setText(R.id.tv_lefttitle, item.getTitleText());
                        }
                    }

                    @Override
                    public void onItemClickListener(LinkListBean linkListBean, View view, int position) {
                        super.onItemClickListener(linkListBean, view, position);
                        LzToast.showToast(linkListBean.getTitleText() + "位置===" + position);
                    }
                })
                .init();
    }

    private void getRightData(ArrayList<LinkBean> linkBeans, int count) {
        LinkListBean e;
        for (int i = 0; i < count; i++) {
            e = new LinkListBean();
            e.setGroupTag("分类" + count);
            if (count > 6) {
                e.setGroupTag("分类分类分类分类" + count);
            }
            e.setTitleText("条目" + i);
            linkBeans.add(e);
        }
    }

}



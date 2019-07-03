package lz.com.kit;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.annotations.Nullable;
import lz.com.kit.aac.MvvmActivity;
import lz.com.kit.bean.ActivityBean;
import lz.com.kit.mvp.MvpActivity;
import lz.com.kit.paging.PagingActivity;
import lz.com.tools.inject.InjectLayout;
import lz.com.tools.recycleview.ReboundReyclerView;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;
import lz.com.tools.recycleview.decoration.Divider;
import lz.com.tools.recycleview.decoration.DividerBuilder;
import lz.com.tools.recycleview.decoration.DividerItemDecoration;


@InjectLayout(layoutId = R.layout.activity_main,isShowBackIcon = false)
public class MainActivity extends BaseKitActivity {
    @BindView(R.id.recyclevie)
    ReboundReyclerView recyclevie;
    private BaseRecycleAdapter<ActivityBean, BaseViewHolder> mAdapter;

    @Override
    public void initData() {


        ArrayList<ActivityBean> strings = new ArrayList<>();
        strings.add(new ActivityBean().setName("ScrollView").setCalssName(ScrollViewActivity.class));
        strings.add(new ActivityBean().setName("RecyclerView").setCalssName(RecyclerViewActivity.class));
        strings.add(new ActivityBean().setName("IntervalActivity").setCalssName(IntervalActivity.class));
        strings.add(new ActivityBean().setName("Checked").setCalssName(SelectActivity.class));
        strings.add(new ActivityBean().setName("PageRecyclerViewActivity").setCalssName(PageRecyclerViewActivity.class));
        strings.add(new ActivityBean().setName("PermissionActivity").setCalssName(PermissionActivity.class));
        strings.add(new ActivityBean().setName("DialogActivity").setCalssName(DialogActivity.class));
        strings.add(new ActivityBean().setName("IocActivity").setCalssName(IocActivity.class));
        strings.add(new ActivityBean().setName("ShopActivity").setCalssName(ShopActivity.class));
        strings.add(new ActivityBean().setName("CitySelectActivity").setCalssName(CitySelectActivity.class));
        strings.add(new ActivityBean().setName("TitleActivity").setCalssName(TitleActivity.class));
        strings.add(new ActivityBean().setName("TablayoutActivity").setCalssName(TablayoutActivity.class));
        strings.add(new ActivityBean().setName("BannerActivity").setCalssName(BannerActivity.class));
        strings.add(new ActivityBean().setName("TextViewActivity").setCalssName(TextViewActivity.class));
        strings.add(new ActivityBean().setName("MvpActivity").setCalssName(MvpActivity.class));
        strings.add(new ActivityBean().setName("StatueActivity").setCalssName(StatueActivity.class));
        strings.add(new ActivityBean().setName("MenuActivity").setCalssName(MenuActivity.class));
        strings.add(new ActivityBean().setName("ShopCategoryActivity").setCalssName(ShopCategoryActivity.class));
        strings.add(new ActivityBean().setName("LongImageActivity").setCalssName(LongImageActivity.class));
        strings.add(new ActivityBean().setName("MvvmActivity").setCalssName(MvvmActivity.class));
        strings.add(new ActivityBean().setName("PagingActivity").setCalssName(PagingActivity.class));
        strings.add(new ActivityBean().setName("FinancialActivity").setCalssName(FinancialActivity.class));

        mAdapter = new BaseRecycleAdapter<ActivityBean, BaseViewHolder>(R.layout.item_text_list) {

            @Override
            protected void onBindView(BaseViewHolder holder, ActivityBean item) {
                holder.setText(R.id.tv_1, item.name);
            }
        };
        recyclevie.setLayoutManager(new GridLayoutManager(this, 3));
        recyclevie.addItemDecoration(new DividerItemDecoration(this) {
            @Nullable
            @Override
            public Divider getDivider(RecyclerView parent, int itemPosition) {
                return new DividerBuilder()
                        .setLeftSideLine(true, Color.TRANSPARENT, 10, 0, 0)
                        .setTopSideLine(true, Color.TRANSPARENT, 10, 0, 0)
                        .setRightSideLine(true, Color.TRANSPARENT, 10, 0, 0)
                        .setBottomSideLine(true, Color.TRANSPARENT, 10, 0, 0)
                        .create();
            }
        });
        mAdapter.bindToRecyclerView(recyclevie);
        mAdapter.setNewData(strings);
        mAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleAdapter adapter, View view, int position) {
                Intent mIntent = new Intent(MainActivity.this, mAdapter.getData().get(position).calssName);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mIntent.putExtra("type", BaseKitActivity.SLIDE_CODE);
                    startActivity(mIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                } else {
                    startActivity(mIntent);
                }
            }
        });

    }


}

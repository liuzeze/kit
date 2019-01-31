package lz.com.kit;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import lz.com.kit.bean.ActivityBean;
import lz.com.tools.recycleview.ReboundReyclerView;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;
import lz.com.tools.recycleview.decoration.Divider;
import lz.com.tools.recycleview.decoration.DividerBuilder;
import lz.com.tools.recycleview.decoration.DividerItemDecoration;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.recyclevie)
    ReboundReyclerView recyclevie;
    private BaseRecycleAdapter<ActivityBean, BaseViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        ArrayList<ActivityBean> strings = new ArrayList<>();
        strings.add(new ActivityBean().setName("ScrollView").setCalssName(ScrollViewActivity.class));
        strings.add(new ActivityBean().setName("RecyclerView").setCalssName(RecyclerViewActivity.class));
        strings.add(new ActivityBean().setName("IntervalActivity").setCalssName(IntervalActivity.class));
        strings.add(new ActivityBean().setName("SingleChecked").setCalssName(SingleSelectActivity.class));
        strings.add(new ActivityBean().setName("MultiCheckedActivity").setCalssName(MultiCheckedActivity.class));
        strings.add(new ActivityBean().setName("MultiUnCancelCheckedActivity").setCalssName(MultiUnCancelCheckedActivity.class));
        strings.add(new ActivityBean().setName("SingleUnCancelCheckedActivity").setCalssName(SingleUnCancelCheckedActivity.class));
        strings.add(new ActivityBean().setName("PageRecyclerViewActivity").setCalssName(PageRecyclerViewActivity.class));

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
                    mIntent.putExtra("type", BaseActivity.SLIDE_CODE);
                    startActivity(mIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                } else {
                    startActivity(mIntent);
                }
            }
        });

    }


}

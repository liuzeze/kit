package lz.com.kit;

import android.graphics.Color;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import lz.com.kit.view.SimpleRefreshLayout;
import lz.com.tools.recycleview.ReboundReyclerView;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;
import lz.com.tools.recycleview.decoration.sticky.StickyDecoration;
import lz.com.tools.recycleview.decoration.sticky.listener.GroupListener;
import lz.com.tools.recycleview.layoutmanager.LzLinearLayoutManager;
import lz.com.tools.recycleview.layoutmanager.PagerLayoutManager;
import lz.com.tools.recycleview.swipe.SwipeMenuItem;

public class PageRecyclerViewActivity extends BaseActivity {


    @BindView(R.id.recyclevie)
    ReboundReyclerView recyclevie;
    private BaseRecycleAdapter<String, BaseViewHolder> mAdapter;
    private PagerLayoutManager mLzLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        ButterKnife.bind(this);
        mLzLinearLayoutManager = new PagerLayoutManager(this,RecyclerView.VERTICAL);
        recyclevie.setLayoutManager(mLzLinearLayoutManager);


        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            strings.add("点击切换滑动方向+" + i);
        }
        mAdapter = new BaseRecycleAdapter<String, BaseViewHolder>(R.layout.item_page_text) {

            @Override
            protected void onBindView(BaseViewHolder holder, String item) {
                holder.setText(R.id.tv_1, item);

            }
        };
        recyclevie.setAdapter(mAdapter);
        mAdapter.setNewData(strings);


        mAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleAdapter adapter, View view, int position) {
                if (mLzLinearLayoutManager.getOrientation() == RecyclerView.VERTICAL) {
                    mLzLinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                } else {

                    mLzLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                }

            }
        });


        mLzLinearLayoutManager.setOnViewPagerListener(new PagerLayoutManager.OnViewPagerListener() {
            @Override
            public void onInitComplete() {

            }

            @Override
            public void onPageRelease(boolean isNext, int position) {

            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {

            }
        });


    }
}

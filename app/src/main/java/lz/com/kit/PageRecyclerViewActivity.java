package lz.com.kit;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import lz.com.tools.inject.InjectLayout;
import lz.com.tools.recycleview.ReboundReyclerView;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;
import lz.com.tools.recycleview.layoutmanager.PagerLayoutManager;

@InjectLayout(layoutId = R.layout.activity_recycler_view, titleName = "仿抖音")
public class PageRecyclerViewActivity extends BaseKitActivity {

    @BindView(R.id.recyclevie)
    ReboundReyclerView recyclevie;
    private BaseRecycleAdapter<String, BaseViewHolder> mAdapter;
    private PagerLayoutManager mLzLinearLayoutManager;

    @Override
    public void initData() {

        mLzLinearLayoutManager = new PagerLayoutManager(this, RecyclerView.VERTICAL);
        recyclevie.setLayoutManager(mLzLinearLayoutManager);


        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            strings.add("点击切换滑动方向+" + i);
        }
        mAdapter = new BaseRecycleAdapter<String, BaseViewHolder>(R.layout.item_page_text) {

            @Override
            protected void onBindView(BaseViewHolder holder, String item) {
                holder.setText(R.id.tv_1, item);
                holder.setGone(R.id.checkbox, false);

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

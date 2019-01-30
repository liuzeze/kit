package lz.com.kit;

import android.graphics.Color;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import lz.com.kit.view.SimpleRefreshLayout;
import lz.com.tools.recycleview.ReboundReyclerView;
import lz.com.tools.recycleview.layoutmanager.LzLinearLayoutManager;
import lz.com.tools.recycleview.swipe.SwipeMenuItem;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;
import lz.com.tools.recycleview.decoration.sticky.StickyDecoration;
import lz.com.tools.recycleview.decoration.sticky.listener.GroupListener;

public class RecyclerViewActivity extends BaseActivity {


    @BindView(R.id.recyclevie)
    ReboundReyclerView recyclevie;
    private BaseRecycleAdapter<String, BaseViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        ButterKnife.bind(this);
//        recyclevie.setLayoutManager(new LzLinearLayoutManager(this));
        recyclevie.setLayoutManager(new GridLayoutManager(this, 4));

        recyclevie.addItemDecoration(StickyDecoration.Builder.init(new GroupListener() {
            @Override
            public String getGroupName(int position) {
                return (position) % 4 == 0 ? "位置" + position : null;
            }
        })
                .setHeaderCount(1)
                .setDivideHeight(10)
                .setDivideColor(Color.RED)
                .build());
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            strings.add("位置+" + i);
        }
        mAdapter = new BaseRecycleAdapter<String, BaseViewHolder>(R.layout.item_text_list) {

            @Override
            protected void onBindView(BaseViewHolder holder, String item) {
                holder.setText(R.id.tv_1, item);
                holder.addOnClickListener(R.id.tv_1);
                holder.getView(R.id.tv_1).setClipToOutline(true);
                holder.getView(R.id.tv_1).setOutlineProvider(new ViewOutlineProvider() {
                    @Override
                    public void getOutline(View view, Outline outline) {
                        outline.setOval(0, 0, view.getWidth(), view.getHeight());
                    }
                });
            }
        };
        mAdapter.bindToRecyclerView(recyclevie);
        mAdapter.setNewData(strings);

        TextView header = new TextView(this);
        header.setText("\n\n\n封装Adapter ,可以添加Header Footer  加载更对\n 下拉刷新,可以在列表任意位置显示刷新布局" +
                " \n  空布局 侧滑删除 分割线  悬浮吸顶   列表滑动标题渐变   仿ios越界回弹\n\n\n");
        mAdapter.addHeaderView(header);


        recyclevie.setEnableRefrash(true);
        SimpleRefreshLayout simpleRefreshLayout = new SimpleRefreshLayout(this);
        mAdapter.addHeaderView(simpleRefreshLayout);
        recyclevie.setRefreshView(simpleRefreshLayout);


        for (int i = 0; i < 10; i++) {

            TextView header2 = new TextView(this);
            header2.setText("底部控件====" + i);
            mAdapter.addFooterView(header2);
        }


        mAdapter.setSwipeMenuItem(new SwipeMenuItem(this)
                .setEnableSwipe(false)
                .setIos(true)
                .setLeftSwipe(true)
                .setWidth(300));
        mAdapter.setOnSwipeClickListener(new BaseRecycleAdapter.OnSwipeClickListener() {
            @Override
            public void OnSwipeClick(BaseRecycleAdapter adapter, View view, int position) {
                Toast.makeText(view.getContext(), "position===" + position, Toast.LENGTH_SHORT).show();
                TextView emptyView = new TextView(RecyclerViewActivity.this);
                emptyView.setText("11111111111111111111");
                adapter.setHeaderFooterEmpty(true, true);
                adapter.setEmptyView(emptyView);
            }
        });
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.setNewData(null);
                mAdapter.addData(strings);
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseRecycleAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseRecycleAdapter adapter, View view, int position) {
                Toast.makeText(view.getContext(), "position===" + position, Toast.LENGTH_SHORT).show();

            }
        });
        mAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleAdapter adapter, View view, int position) {
                Toast.makeText(view.getContext(), "position===" + position, Toast.LENGTH_SHORT).show();

            }
        });

        recyclevie.setScrollAlphaChangeListener(new ReboundReyclerView.ScrollAlphaChangeListener() {
            @Override
            public void onAlphaChange(int alpha, int scrollDyCounter) {
                System.out.println("透明度变换" + alpha);
            }

            @Override
            public int setLimitHeight() {
                return 1300;
            }
        });


        mAdapter.setOnLoadMoreListener(new BaseRecycleAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mAdapter.loadMoreEnd();
               /* ArrayList<String> strings = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    strings.add("加载更多+" + (mAdapter.getData().size() + i));
                }
                mAdapter.addData(strings);


                if (mAdapter.getData().size() == 60) {
                    mAdapter.loadMoreFail();

                } else if (mAdapter.getData().size() == 120) {
                    mAdapter.loadMoreEnd();

                } else {
                    mAdapter.loadMoreComplete();
                }*/
            }
        }, recyclevie);
//        adapter.disableLoadMoreIfNotFullPage(recyclevie);

        recyclevie.setOnRefreshListener(new ReboundReyclerView.OnRefreshListener() {
            @Override
            public void onRefreshing(int offset) {
                recyclevie.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setNewData(strings);

                        recyclevie.closeRefresh();

                    }
                }, 2000);
            }
        });
        recyclevie.setOnUpScrollListener(new ReboundReyclerView.OnUpScrollListener() {
            @Override
            public void onUpScroll(int offset) {

            }

            @Override
            public void onUpMoveScroll(int offset) {

            }
        });


    }
}

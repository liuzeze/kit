package lz.com.kit;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import lz.com.tools.recycleview.ReboundReyclerView;
import lz.com.tools.recycleview.SwipeMenuItem;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;

public class RecyclerViewActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclevie)
    ReboundReyclerView recyclevie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);


        tvTitle.getBackground().setAlpha(0);

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("位置+" + i);
        }
        BaseRecycleAdapter<String, BaseViewHolder> adapter = new BaseRecycleAdapter<String, BaseViewHolder>(R.layout.item_text_list) {

            @Override
            protected void onBindView(BaseViewHolder holder, String item) {
                holder.setText(R.id.tv_1, item);
                holder.addOnClickListener(R.id.tv_1);
            }
        };
        adapter.bindToRecyclerView(recyclevie);
        adapter.setNewData(strings);

        TextView header = new TextView(this);
        header.setText("头部空间");
        adapter.addHeaderView(header);


        recyclevie.setEnableRefrash(true);
        SimpleRefreshLayout simpleRefreshLayout = new SimpleRefreshLayout(this);
        adapter.addHeaderView(simpleRefreshLayout);
        recyclevie.setRefreshView(simpleRefreshLayout);


        for (int i = 0; i < 10; i++) {

            TextView header2 = new TextView(this);
            header2.setText("底部控件====" + i);
            adapter.addFooterView(header2);
        }


        adapter.setSwipeMenuItem(new SwipeMenuItem(this)
                .setEnableSwipe(false)
                .setIos(true)
                .setLeftSwipe(true)
                .setWidth(300));
        adapter.setOnSwipeClickListener(new BaseRecycleAdapter.OnSwipeClickListener() {
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
                adapter.setNewData(null);
                adapter.addData(strings);
            }
        });
        adapter.setOnItemChildClickListener(new BaseRecycleAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseRecycleAdapter adapter, View view, int position) {
                Toast.makeText(view.getContext(), "position===" + position, Toast.LENGTH_SHORT).show();

            }
        });
        adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleAdapter adapter, View view, int position) {
                Toast.makeText(view.getContext(), "position===" + position, Toast.LENGTH_SHORT).show();

            }
        });

        recyclevie.setScrollAlphaChangeListener(new ReboundReyclerView.ScrollAlphaChangeListener() {
            @Override
            public void onAlphaChange(int alpha, int scrollDyCounter) {
                tvTitle.getBackground().setAlpha(alpha);
                System.out.println("滑动距离" + scrollDyCounter);
            }

            @Override
            public int setLimitHeight() {
                return 1300;
            }
        });


        adapter.setOnLoadMoreListener(new BaseRecycleAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                ArrayList<String> strings = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    strings.add("加载更多+" + (adapter.getData().size() + i));
                }
                adapter.addData(strings);


                if (adapter.getData().size() == 60) {
                    adapter.loadMoreFail();

                } else if (adapter.getData().size() == 120) {
                    adapter.loadMoreEnd();

                } else {
                    adapter.loadMoreComplete();
                }
            }
        }, recyclevie);
//        adapter.disableLoadMoreIfNotFullPage(recyclevie);

        recyclevie.setOnRefreshListener(new ReboundReyclerView.OnRefreshListener() {
            @Override
            public void onRefreshing(int offset) {
                tvTitle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
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

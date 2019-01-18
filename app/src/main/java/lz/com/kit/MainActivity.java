package lz.com.kit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import lz.com.tools.recycleview.ReboundReyclerView;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;

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

        mAdapter = new BaseRecycleAdapter<ActivityBean, BaseViewHolder>(R.layout.item_text_list) {

            @Override
            protected void onBindView(BaseViewHolder holder, ActivityBean item) {
                holder.setText(R.id.tv_1, item.name);
//                holder.addOnClickListener(R.id.tv_1);
            }
        };
        recyclevie.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter.bindToRecyclerView(recyclevie);
        mAdapter.setNewData(strings);
        mAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleAdapter adapter, View view, int position) {
                startActivity(new Intent(MainActivity.this, mAdapter.getData().get(position).calssName));
            }
        });

    }


}

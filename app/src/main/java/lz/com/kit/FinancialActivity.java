package lz.com.kit;

import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DividerItemDecoration;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Consumer;

import butterknife.BindView;
import lz.com.tools.inject.InjectLayout;
import lz.com.tools.recycleview.ReboundReyclerView;
import lz.com.tools.recycleview.ScrollHelperView;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.adapter.BaseViewHolder;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-06-04       创建class
 */
@InjectLayout(layoutId = R.layout.activity_recycler_view, titleName = "仿金融列表横向滑动")
public class FinancialActivity extends BaseKitActivity {

    @BindView(R.id.recyclevie)
    ReboundReyclerView recyclevie;
    @BindView(R.id.refresh)
    ScrollHelperView refresh;

    ArrayList mArrayList = new ArrayList<String>();
    ArrayList<View> mViews = new ArrayList<>();
    private float mStartX;
    private float mStartY;

    @Override
    protected void initData() {
        ClassLoader classLoader = this.getClassLoader();
        Class<? extends FinancialActivity> aClass = getClass();

        for (int i = 0; i < 50; i++) {
            mArrayList.add("标题" + i);
        }
        mArrayList.subList(0, 20);
        BaseRecycleAdapter<String, BaseViewHolder> adapter = new BaseRecycleAdapter<String, BaseViewHolder>(R.layout.item_scroll_layout, mArrayList) {

            @Override
            protected void onBindView(BaseViewHolder holder, String item) {
                holder.setText(R.id.tv_title, item);
                mViews.add(holder.getView(R.id.h_scroll));

                HorizontalScrollView horizontalScrollView = holder.getView(R.id.h_scroll);
                horizontalScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        mViews.forEach(view -> view.scrollTo(horizontalScrollView.getScrollX(), 0));
                    }
                });

            }
        };
        recyclevie.setAdapter(adapter);
        recyclevie.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    @NotNull
    private View.OnTouchListener getL() {
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mStartX = event.getX();
                        mStartY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float moveX = event.getX();
                        float moveY = event.getY();

                        if (Math.abs(moveX - mStartX) > Math.abs(moveY - mStartY)) {
                            //横向滑动
                            mViews.forEach(new Consumer<View>() {
                                @Override
                                public void accept(View view) {
                                    view.setNestedScrollingEnabled(false);
                                    view.scrollTo((int) (moveX - mStartX), 0);
                                }
                            });
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        mStartX = 0;
                        mStartY = 0;
                        break;
                    default:
                        break;
                }
                return false;
            }
        };
        return onTouchListener;
    }
}

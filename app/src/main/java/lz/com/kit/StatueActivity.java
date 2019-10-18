package lz.com.kit;


import android.view.View;
import android.view.ViewGroup;

import lz.com.kit.view.BolangAnim;
import lz.com.status.StatusConfigBuild;
import lz.com.status.StatusView;
import lz.com.tools.inject.InjectLayout;
import lz.com.tools.widget.WaterRippleAnim;

@InjectLayout(layoutId = R.layout.activity_statue, titleName = "状态加载控件")
public class StatueActivity extends BaseKitActivity {

    @Override
    protected void initData() {
        //基类统一封装
        initBaseStatue();
        //单独控件使用
        initSubView();
    }

    /**
     * 单独控件使用
     */
    private void initSubView() {
        StatusView statusView5 = findViewById(R.id.statusview);
        statusView5.showEmptyView();

        StatusView statusView6 = StatusView.init(findViewById(R.id.tv6));
        statusView6.showErrorView();


        StatusView statusView1 = StatusView.init(this, R.id.tv1);
        statusView1.config(new StatusConfigBuild().setLoadTitle("加载中")
                .setTitleColor(R.color.colorAccent));
        statusView1.showLoadingView();
        statusView1.setLoadtitle("");
        statusView1.setErrortitle("");
        statusView1.setEmptytitle("");


        final StatusView statusView2 = StatusView.init(this, R.id.tv2);
        statusView2.config(new StatusConfigBuild()
                .setErrorTitle("加载失败")
                .setTitleColor(R.color.colorAccent)
                .setEmptyTitle("没有数据")
                .setEmptyRetryListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        statusView2.showContentView();
                    }
                })
                .setErrorRetryListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        statusView2.showEmptyView();
                    }
                })
        );
        statusView2.showErrorView();


        final StatusView statusView3 = StatusView.init(this, R.id.tv3);
        statusView3.config(new StatusConfigBuild()
                .setTitleColor(R.color.colorAccent)
                .setEmptyTitle("没数据")
                .setRetryTextColor(R.color.colorAccent)
                .setEmptyRetryListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        statusView3.showContentView();
                    }
                })
                .setErrorRetryListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        statusView3.showEmptyView();
                    }
                })
        );
        statusView3.showEmptyView();


        final StatusView statusView4 = StatusView.init(this, R.id.tv4);
        statusView4.showContentView();


        findViewById(R.id.tv5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusView4.showContentView();
            }
        });


        StatusView statusView7 = StatusView.init(findViewById(R.id.tv7));
        WaterRippleAnim animView2 = new WaterRippleAnim(this);
        animView2.setText("loading");
        animView2.setTextSize(30);
        statusView7.config(new StatusConfigBuild()
                .setLoadTitle("加载中")
                .addLoadAnim(animView2))
                .showLoadingView();
    }

    /**
     * 基类统一封装
     *
     * @return
     */
    private void initBaseStatue() {
        View childAt = ((ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0)).getChildAt(1);
        final StatusView init = StatusView.init(childAt);
        BolangAnim animView = new BolangAnim(this);
        init.config(new StatusConfigBuild()
                .setShowloadTitle(false)
                .setErrorTitle("加载失败")
                .setTitleColor(R.color.colorAccent)
                .setEmptyTitle("没有数据")
                .addLoadAnim(animView)
                .setEmptyRetryListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        init.showContentView();
                    }
                })
                .setErrorRetryListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        init.showEmptyView();

                    }
                }));
        init.showLoadingView();

        childAt.postDelayed(new Runnable() {
            @Override
            public void run() {
                init.showErrorView();
            }
        }, 5000);
    }
}

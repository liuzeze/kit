package lz.com.tools.linkedlist;

import android.content.Context;
import android.graphics.Color;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import java.util.ArrayList;
import java.util.Objects;

import lz.com.tools.R;
import lz.com.tools.recycleview.ReboundReyclerView;
import lz.com.tools.recycleview.adapter.BaseRecycleAdapter;
import lz.com.tools.recycleview.decoration.sticky.BaseDecoration;
import lz.com.tools.recycleview.decoration.sticky.PowerfulStickyDecoration;
import lz.com.tools.recycleview.decoration.sticky.StickyDecoration;
import lz.com.tools.recycleview.decoration.sticky.listener.GroupListener;
import lz.com.tools.recycleview.decoration.sticky.listener.PowerGroupListener;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-10       创建class
 */
public class LinkRecyclerView extends FrameLayout {

    private LinkedAdapter1 mLinkedAdapter1;
    private LinkedAdapter2 mLinkedAdapter2;

    private ArrayList<LinkBean> mLinkBeans2 = new ArrayList<>();
    private ArrayList<LinkBean> mLinkBeans1 = new ArrayList<>();
    private ArrayMap<String, Integer> mGroupList = new ArrayMap<>();
    private ReboundReyclerView mRecyclerview1;
    private ReboundReyclerView mRecyclerview2;
    private LinearLayoutManager mLayoutManager2;
    private LinkedAdapter2Config mConfig = new DefaultAdapter2Config();
    private BaseDecoration mItemDecoration;

    public LinkRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public LinkRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LinkRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public LinkRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        View inflate = View.inflate(getContext(), R.layout.layout_link_recycler, null);
        addView(inflate);
        mRecyclerview1 = findViewById(R.id.recyclerview1);
        mRecyclerview2 = findViewById(R.id.recyclerview2);
        ((SimpleItemAnimator) mRecyclerview1.getItemAnimator()).setSupportsChangeAnimations(false);
        mLayoutManager2 = (LinearLayoutManager) mRecyclerview2.getLayoutManager();


        mRecyclerview2.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                int position = ((LinearLayoutManager) Objects.requireNonNull(mRecyclerview2.getLayoutManager())).findFirstVisibleItemPosition();


                int i = mLinkBeans1.indexOf(mLinkBeans2.get(position));
                if (i != -1) {
                    mLinkedAdapter1.setPosition(i);
                }

            }
        });
    }


    public LinkRecyclerView setData(ArrayList<LinkBean> linkBeans) {
        mLinkBeans2 = linkBeans;

        for (int i = 0; i < mLinkBeans2.size(); i++) {
            LinkBean bean = mLinkBeans2.get(i);
            if (i == 0) {
                mGroupList.put(bean.getGroupTag(), i);
                mLinkBeans1.add(bean);
            } else {
                LinkBean bean1 = mLinkBeans2.get(i - 1);
                if (!bean1.getGroupTag().equals(bean.getGroupTag())) {
                    mGroupList.put(bean.getGroupTag(), i);
                    mLinkBeans1.add(bean);
                }
            }


        }
        return this;
    }

    public LinkRecyclerView setAdapter1Config(LinkedAdapter1Config config) {

        mLinkedAdapter1 = new LinkedAdapter1(config, mLinkBeans1);
        return this;
    }

    public LinkRecyclerView setAdapter2Config(LinkedAdapter2Config config) {
        if (config != null) {
            mConfig = config;
        }
        mLinkedAdapter2 = new LinkedAdapter2(mConfig, mLinkBeans2, mRecyclerview2);
        return this;
    }

    public LinkRecyclerView setItemDecoration(BaseDecoration itemDecoration) {
        mItemDecoration = itemDecoration;
        return this;
    }


    public void init() {
        if (mLinkedAdapter1 == null) {
            mLinkedAdapter1 = new LinkedAdapter1(new DefaultAdapter1Config(), mLinkBeans1);
        }
        if (mLinkedAdapter2 == null) {
            mLinkedAdapter2 = new LinkedAdapter2(new DefaultAdapter2Config(), mLinkBeans2, mRecyclerview2);
        }
        mRecyclerview1.setAdapter(mLinkedAdapter1);
        mRecyclerview2.setAdapter(mLinkedAdapter2);
        mLinkedAdapter1.setNewData(mLinkBeans1);
        mLinkedAdapter2.setNewData(mLinkBeans2);
        setShowGrid();

        mLinkedAdapter1.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleAdapter adapter, View view, int position) {
                mLinkedAdapter1.setPosition(position);

                int i = mGroupList.indexOfKey(mLinkBeans1.get(position).getGroupTag());
                if (i > -1) {
                    Integer position1 = mGroupList.valueAt(i);
                    if (position1 != null) {
                        mLayoutManager2.scrollToPositionWithOffset(position1, 0);
                    }
                }

            }
        });
        mLinkedAdapter2.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleAdapter adapter, View view, int position) {
                mConfig.onItemClickListener(mLinkedAdapter2.getData().get(position), view, position);
            }
        });

    }


    private void setShowGrid() {
        if (mItemDecoration == null) {
            mItemDecoration = StickyDecoration.Builder
                    .init(new GroupListener() {

                        @Override
                        public String getGroupName(int position) {
                            LinkBean linkBean = mLinkBeans2.get(position);
                            return linkBean.getGroupTag();

                        }
                    }).setDivideHeight(10)
                    .setDivideColor(Color.TRANSPARENT)
                    .setGroupBackground(Color.GRAY)
                    .setGroupTextColor(Color.WHITE).build();
        }
        if (mLinkedAdapter2.getConfig().isShowGrid()) {
            mLayoutManager2 = new GridLayoutManager(getContext(), mLinkedAdapter2.getConfig().setSpanCount());
            mItemDecoration.resetSpan(mRecyclerview2, (GridLayoutManager) mLayoutManager2);

        } else {
            mLayoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        }
        mRecyclerview2.setLayoutManager(mLayoutManager2);
        mRecyclerview2.addItemDecoration(mItemDecoration);
    }

}

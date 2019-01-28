package lz.com.tools.recycleview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import lz.com.tools.R;
import lz.com.tools.recycleview.checked.CheckHelper;
import lz.com.tools.recycleview.swipe.SwipeMenuItem;
import lz.com.tools.recycleview.swipe.SwipeMenuLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽       2019-01-11       基类adapter  加header  footer
 * 加载更多 点击事件  长按事件
 */
public abstract class BaseRecycleAdapter<T, K extends BaseViewHolder> extends RecyclerView.Adapter<K> {


    private List<T> mData;
    private @LayoutRes
    int mLayoutResId;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;


    private LoadMoreView mLoadMoreView = new SimpleLoadMoreView();
    public static final int HEADER_VIEW = 0x00000111;
    public static final int EMPTY_VIEW = 0x00000555;

    public static final int LOADING_VIEW = 0x00000222;
    public static final int FOOTER_VIEW = 0x00000333;
    private LinearLayout mHeaderLayouts;
    private LinearLayout mFooterLayouts;
    private RequestLoadMoreListener mRequestLoadMoreListener;
    private boolean mLoading;
    private SwipeMenuItem mSwipeMenuItem;
    private RecyclerView mRecyclerView;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemChildLongClickListener mOnItemChildLongClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private OnSwipeClickListener mSwipeClickListener;
    private SwipeMenuLayout mSwipeMenuLayout;
    private FrameLayout mEmptyLayout;
    private boolean mHeadAndEmptyEnable;
    private boolean mFootAndEmptyEnable;
    private boolean mIsUseEmpty;
    private boolean mLoadMoreEnable;
    private boolean mNextLoadEnable;
    private CheckHelper mCheckHelper;


    public BaseRecycleAdapter<T, K> setSwipeMenuItem(SwipeMenuItem swipeMenuItem) {
        mSwipeMenuItem = swipeMenuItem;
        return this;
    }


    public BaseRecycleAdapter(@LayoutRes int layoutResId) {
        this.mData = new ArrayList<T>();
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
    }

    public BaseRecycleAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : data;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (getEmptyViewCount() == 1) {
            boolean header = mHeadAndEmptyEnable && getHeaderLayoutCount() != 0;
            switch (position) {
                case 0:
                    if (header) {
                        return HEADER_VIEW;
                    } else {
                        return EMPTY_VIEW;
                    }
                case 1:
                    if (header) {
                        return EMPTY_VIEW;
                    } else {
                        return FOOTER_VIEW;
                    }
                case 2:
                    return FOOTER_VIEW;
                default:
                    return EMPTY_VIEW;
            }
        }
        int numHeaders = getHeaderLayoutCount();
        if (position < numHeaders) {
            return HEADER_VIEW;
        } else {
            int adjPosition = position - numHeaders;
            int adapterCount = mData.size();
            if (adjPosition < adapterCount) {
                return super.getItemViewType(position);
            } else {
                adjPosition = adjPosition - adapterCount;
                int numFooters = getFooterLayoutCount();
                if (adjPosition < numFooters) {
                    return FOOTER_VIEW;
                } else {
                    return LOADING_VIEW;
                }
            }


        }
    }

    @NonNull
    @Override
    public K onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        final K k;
        switch (viewType) {
            case HEADER_VIEW:
                k = (K) new BaseViewHolder(mHeaderLayouts);
                break;
            case FOOTER_VIEW:
                k = (K) new BaseViewHolder(mFooterLayouts);
                break;
            case EMPTY_VIEW:
                k = (K) new BaseViewHolder(mEmptyLayout);
                break;
            case LOADING_VIEW:
                k = getLoadingView(parent);
                break;
            default:
                View inflate = mLayoutInflater.inflate(mLayoutResId, parent, false);
                k = (K) new BaseViewHolder(inflate);
                this.setOnItemClickListener(k);
                break;
        }
        k.setAdapter(this);


        if (mSwipeMenuItem == null) return k;

        switch (viewType) {
            case HEADER_VIEW:
            case FOOTER_VIEW:
            case EMPTY_VIEW:
            case LOADING_VIEW:
                break;
            default:

                mSwipeMenuLayout = (SwipeMenuLayout) mLayoutInflater.inflate(R.layout.recycler_swipe_view_item, parent, false);
                FrameLayout flitemright = mSwipeMenuLayout.findViewById(R.id.fl_item_right);
                FrameLayout flitemcontent = mSwipeMenuLayout.findViewById(R.id.fl_item_content);
                mSwipeMenuLayout.setIos(mSwipeMenuItem.isIos()).setLeftSwipe(mSwipeMenuItem.isLeftSwipe()).setSwipeEnable(true);

                View rightLayout = mSwipeMenuItem.getRightLayout();
                ViewGroup.LayoutParams layoutParams = flitemright.getLayoutParams();
                layoutParams.width = mSwipeMenuItem.getWidth();
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                flitemright.setLayoutParams(layoutParams);
                flitemright.setBackground(mSwipeMenuItem.getBackground());
                flitemright.addView(rightLayout);
                flitemcontent.addView(k.itemView);

                flitemright.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mSwipeClickListener != null) {
                            mSwipeClickListener.OnSwipeClick(BaseRecycleAdapter.this, v, k.getLayoutPosition() - getHeaderLayoutCount());
                        }
                    }
                });

                try {

                    Field itemView = getSupperClass(k.getClass()).getDeclaredField("itemView");
                    if (!itemView.isAccessible()) itemView.setAccessible(true);
                    itemView.set(k, mSwipeMenuLayout);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }


        return k;
    }

    private Class<?> getSupperClass(Class<?> aClass) {
        Class<?> supperClass = aClass.getSuperclass();
        if (supperClass != null && !supperClass.equals(Object.class)) {
            return getSupperClass(supperClass);
        }
        return aClass;
    }

    /**
     * 条目点击监听
     *
     * @param baseViewHolder
     */
    private void setOnItemClickListener(final BaseViewHolder baseViewHolder) {
        View itemView = baseViewHolder.itemView;
        if (itemView == null) {
            return;
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(BaseRecycleAdapter.this, v, baseViewHolder.getLayoutPosition() - getHeaderLayoutCount());
                }
                if (mEnableItemChecked) {
                    if (mCheckHelper != null) {
                        int position = baseViewHolder.getLayoutPosition() - getHeaderLayoutCount();
                        mCheckHelper.onSelect(baseViewHolder, getItem(position),position);
                    }
                }
            }
        });
        if (getOnItemLongClickListener() != null) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return getOnItemLongClickListener().onItemLongClick(BaseRecycleAdapter.this, v, baseViewHolder.getLayoutPosition() - getHeaderLayoutCount());
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull K holder, int position) {

        loadMoreData(position);

        switch (holder.getItemViewType()) {
            case HEADER_VIEW:
            case EMPTY_VIEW:
            case FOOTER_VIEW:
                break;
            case LOADING_VIEW:
                mLoadMoreView.convert(holder);
                break;
            default:
                if (mEnableItemChecked) {
                    if (mCheckHelper != null) {
                        mCheckHelper.isChecked(holder, getItem(position - getHeaderLayoutCount()),position - getHeaderLayoutCount());
                    }
                }
                onBindView(holder, getItem(position - getHeaderLayoutCount()));
                break;
        }
    }

    @Override
    public int getItemCount() {
        int count;
        if (getEmptyViewCount() == 1) {
            count = 1;
            if (mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
                count++;
            }
            if (mFootAndEmptyEnable && getFooterLayoutCount() != 0) {
                count++;
            }
        } else {
            count = getHeaderLayoutCount() + mData.size() + getFooterLayoutCount() + getLoadMoreViewCount();
        }
        return count;
    }

    protected abstract void onBindView(K holder, T item);


    public void bindToRecyclerView(RecyclerView recyclerView) {
        if (mRecyclerView != null) {
            throw new RuntimeException("Don't bind twice");
        }
        mRecyclerView = recyclerView;
        mRecyclerView.setAdapter(this);
    }

    /*================================空页面==================================*/

    public void setEmptyView(int layoutResId, ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutResId, viewGroup, false);
        setEmptyView(view);
    }


    public void setEmptyView(View emptyView) {
        setNewData(null);
        boolean insert = false;
        if (mEmptyLayout == null) {
            mEmptyLayout = new FrameLayout(emptyView.getContext());
            final RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
            final ViewGroup.LayoutParams lp = emptyView.getLayoutParams();
            if (lp != null) {
                layoutParams.width = lp.width;
                layoutParams.height = lp.height;
            }
            mEmptyLayout.setLayoutParams(layoutParams);
            insert = true;
        }
        mEmptyLayout.removeAllViews();
        mEmptyLayout.addView(emptyView);
        mIsUseEmpty = true;
        if (insert) {
            if (getEmptyViewCount() == 1) {
                int position = 0;
                if (mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
                    position++;
                }
                notifyItemInserted(position);
            }
        }
    }

    public int getEmptyViewCount() {
        if (mEmptyLayout == null || mEmptyLayout.getChildCount() == 0) {
            return 0;
        }
        if (!mIsUseEmpty) {
            return 0;
        }
        if (mData.size() != 0) {
            return 0;
        }
        return 1;
    }


    public void setHeaderAndEmpty(boolean isHeadAndEmpty) {
        setHeaderFooterEmpty(isHeadAndEmpty, false);
    }

    public void setHeaderFooterEmpty(boolean isHeadAndEmpty, boolean isFootAndEmpty) {
        mHeadAndEmptyEnable = isHeadAndEmpty;
        mFootAndEmptyEnable = isFootAndEmpty;
    }

    public void isUseEmpty(boolean isUseEmpty) {
        mIsUseEmpty = isUseEmpty;
    }

    private int getHeaderViewPosition() {
        //Return to header view notify position
        if (getEmptyViewCount() == 1) {
            if (mHeadAndEmptyEnable) {
                return 0;
            }
        } else {
            return 0;
        }
        return -1;
    }

    private int getFooterViewPosition() {
        //Return to footer view notify position
        if (getEmptyViewCount() == 1) {
            int position = 1;
            if (mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
                position++;
            }
            if (mFootAndEmptyEnable) {
                return position;
            }
        } else {
            return getHeaderLayoutCount() + mData.size();
        }
        return -1;
    }
    /*===============================header 添加==============================*/

    public int addHeaderView(View header) {
        return addHeaderView(header, -1);
    }


    public int addHeaderView(View header, int index) {
        return addHeaderView(header, index, LinearLayout.VERTICAL);
    }

    public int addHeaderView(View header, int index, int orientation) {
        if (mHeaderLayouts == null) {
            mHeaderLayouts = new LinearLayout(header.getContext());
            if (orientation == LinearLayout.VERTICAL) {
                mHeaderLayouts.setOrientation(LinearLayout.VERTICAL);
                mHeaderLayouts.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            } else {
                mHeaderLayouts.setOrientation(LinearLayout.HORIZONTAL);
                mHeaderLayouts.setLayoutParams(new RecyclerView.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
            }
        }
        final int childCount = mHeaderLayouts.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        mHeaderLayouts.addView(header, index);
        if (mHeaderLayouts.getChildCount() == 1) {
            int position = getHeaderViewPosition();
            if (position != -1) {
                notifyItemInserted(position);
            }
        }
        return index;
    }


    public void removeHeaderView(View header) {
        if (getHeaderLayoutCount() == 0) return;

        mHeaderLayouts.removeView(header);
        int position = getHeaderViewPosition();
        if (position != -1) {
            notifyItemRemoved(position);
        }
    }


    public void removeAllHeaderView() {
        if (getHeaderLayoutCount() == 0) return;
        mHeaderLayouts.removeAllViews();
        int position = getHeaderViewPosition();
        if (position != -1) {
            notifyItemRemoved(position);
        }
    }

    public int getHeaderLayoutCount() {
        if (mHeaderLayouts == null || mHeaderLayouts.getChildCount() == 0) {
            return 0;
        }
        return 1;
    }


    /*===========================添加footer=================*/
    public int addFooterView(View footer) {
        return addFooterView(footer, -1, LinearLayout.VERTICAL);
    }

    public int addFooterView(View footer, int index) {
        return addFooterView(footer, index, LinearLayout.VERTICAL);
    }


    public int addFooterView(View footer, int index, int orientation) {
        if (mFooterLayouts == null) {
            mFooterLayouts = new LinearLayout(footer.getContext());
            if (orientation == LinearLayout.VERTICAL) {
                mFooterLayouts.setOrientation(LinearLayout.VERTICAL);
                mFooterLayouts.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            } else {
                mFooterLayouts.setOrientation(LinearLayout.HORIZONTAL);
                mFooterLayouts.setLayoutParams(new RecyclerView.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
            }
        }
        final int childCount = mFooterLayouts.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        mFooterLayouts.addView(footer, index);
        if (mFooterLayouts.getChildCount() == 1) {
            int position = getFooterViewPosition();
            if (position != -1) {
                notifyItemInserted(position);
            }

        }
        return index;
    }


    public void removeFooterView(View footer) {
        if (getFooterLayoutCount() == 0) return;

        mFooterLayouts.removeView(footer);
        if (mFooterLayouts.getChildCount() == 0) {
            int position = getFooterViewPosition();
            if (position != -1) {
                notifyItemRemoved(position);
            }

        }
    }


    public void removeAllFooterView() {
        if (getFooterLayoutCount() == 0) return;
        mFooterLayouts.removeAllViews();
        int position = getHeaderViewPosition();
        if (position != -1) {
            notifyItemRemoved(position);
        }
    }

    public int getFooterLayoutCount() {
        if (mFooterLayouts == null || mFooterLayouts.getChildCount() == 0) {
            return 0;
        }
        return 1;
    }

    /*============================加载更多数据=========================*/
    protected void loadMoreData(int position) {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        if (position < getItemCount() - 1) {
            return;
        }
        if (mLoadMoreView.getLoadMoreStatus() != LoadMoreView.STATUS_DEFAULT) {
            return;
        }
        mLoadMoreView.setLoadMoreStatus(LoadMoreView.STATUS_LOADING);
        if (!mLoading) {
            mLoading = true;
            if (mRecyclerView != null) {
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        mRequestLoadMoreListener.onLoadMoreRequested();
                    }
                });
            } else {
                mRequestLoadMoreListener.onLoadMoreRequested();
            }
        }
    }

    private K getLoadingView(ViewGroup parent) {
        View view = mLayoutInflater.inflate(mLoadMoreView.getLayoutId(), parent, false);
        K holder = (K) new BaseViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLoadMoreView.getLoadMoreStatus() == LoadMoreView.STATUS_FAIL) {
                    notifyLoadMoreToLoading();
                }

            }
        });
        return holder;
    }

    public void notifyLoadMoreToLoading() {
        if (mLoadMoreView.getLoadMoreStatus() == LoadMoreView.STATUS_LOADING) {
            return;
        }
        mLoadMoreView.setLoadMoreStatus(LoadMoreView.STATUS_DEFAULT);
        notifyItemChanged(getLoadMoreViewPosition());
    }

    public int getLoadMoreViewCount() {
        if (mRequestLoadMoreListener == null || !mLoadMoreEnable) {
            return 0;
        }
        if (!mNextLoadEnable && mLoadMoreView.isLoadEndMoreGone()) {
            return 0;
        }
        if (mData.size() == 0) {
            return 0;
        }
        return 1;
    }


    public void loadMoreEnd() {
        loadMoreEnd(false);
    }


    public void loadMoreEnd(boolean gone) {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        mLoading = false;
        mNextLoadEnable = false;
        mLoadMoreView.setLoadMoreEndGone(gone);
        if (gone) {
            notifyItemRemoved(getLoadMoreViewPosition());
        } else {
            mLoadMoreView.setLoadMoreStatus(LoadMoreView.STATUS_END);
            notifyItemChanged(getLoadMoreViewPosition());
        }
    }


    public void loadMoreComplete() {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        mLoading = false;
        mNextLoadEnable = false;

        mLoadMoreView.setLoadMoreStatus(LoadMoreView.STATUS_DEFAULT);
        notifyItemChanged(getLoadMoreViewPosition());
    }


    public void loadMoreFail() {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        mLoading = false;

        mLoadMoreView.setLoadMoreStatus(LoadMoreView.STATUS_FAIL);
        notifyItemChanged(getLoadMoreViewPosition());
    }

    public int getLoadMoreViewPosition() {
        return getHeaderLayoutCount() + mData.size() + getFooterLayoutCount();
    }


    @Nullable
    public T getItem(@IntRange(from = 0) int position) {
        if (position >= 0 && position < mData.size()) {
            return mData.get(position);
        } else {
            return null;
        }
    }

    public void setEnableLoadMore(boolean enable) {
        int oldLoadMoreCount = getLoadMoreViewCount();
        mLoadMoreEnable = enable;
        int newLoadMoreCount = getLoadMoreViewCount();

        if (oldLoadMoreCount == 1) {
            if (newLoadMoreCount == 0) {
                notifyItemRemoved(getLoadMoreViewPosition());
            }
        } else {
            if (newLoadMoreCount == 1) {
                mLoadMoreView.setLoadMoreStatus(LoadMoreView.STATUS_DEFAULT);
                notifyItemInserted(getLoadMoreViewPosition());
            }
        }
    }

    public void disableLoadMoreIfNotFullPage(RecyclerView recyclerView) {
        setEnableLoadMore(false);
        if (recyclerView == null) return;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager == null) return;
        if (manager instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isFullScreen(linearLayoutManager)) {
                        setEnableLoadMore(true);
                    }
                }
            }, 50);
        } else if (manager instanceof StaggeredGridLayoutManager) {
            final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) manager;
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    final int[] positions = new int[staggeredGridLayoutManager.getSpanCount()];
                    staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(positions);
                    int pos = getTheBiggestNumber(positions) + 1;
                    if (pos != getItemCount()) {
                        setEnableLoadMore(true);
                    }
                }
            }, 50);
        }
    }

    private boolean isFullScreen(LinearLayoutManager llm) {
        return (llm.findLastCompletelyVisibleItemPosition() + 1) != getItemCount() ||
                llm.findFirstCompletelyVisibleItemPosition() != 0;
    }

    private int getTheBiggestNumber(int[] numbers) {
        int tmp = -1;
        if (numbers == null || numbers.length == 0) {
            return tmp;
        }
        for (int num : numbers) {
            if (num > tmp) {
                tmp = num;
            }
        }
        return tmp;
    }

    /*-====================================兼容gridview 加 head  footer  ===============*/
    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    if (type == HEADER_VIEW && isHeaderViewAsFlow()) {
                        return 1;
                    }
                    if (type == FOOTER_VIEW && isFooterViewAsFlow()) {
                        return 1;
                    }
                    if (mSpanSizeLookup == null) {
                        return isFixedViewType(type) ? gridManager.getSpanCount() : 1;
                    } else {
                        return (isFixedViewType(type)) ? gridManager.getSpanCount() : mSpanSizeLookup.getSpanSize(gridManager,
                                position - getHeaderLayoutCount());
                    }
                }


            });
        }
    }

    protected boolean isFixedViewType(int type) {
        return type == EMPTY_VIEW || type == HEADER_VIEW || type == FOOTER_VIEW || type ==
                LOADING_VIEW;
    }

    private boolean headerViewAsFlow, footerViewAsFlow;

    public void setHeaderViewAsFlow(boolean headerViewAsFlow) {
        this.headerViewAsFlow = headerViewAsFlow;
    }

    public boolean isHeaderViewAsFlow() {
        return headerViewAsFlow;
    }

    public void setFooterViewAsFlow(boolean footerViewAsFlow) {
        this.footerViewAsFlow = footerViewAsFlow;
    }

    public boolean isFooterViewAsFlow() {
        return footerViewAsFlow;
    }

    private SpanSizeLookup mSpanSizeLookup;

    public interface SpanSizeLookup {
        int getSpanSize(GridLayoutManager gridLayoutManager, int position);
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    /*--------------------------点击监听   加载更多监听接口---------------------------------*/
    public interface OnItemClickListener {

        void onItemClick(BaseRecycleAdapter adapter, View view, int position);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface RequestLoadMoreListener {
        void onLoadMoreRequested();

    }

    public void setOnLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener, RecyclerView recyclerView) {
        mLoading = false;
        mRecyclerView = recyclerView;
        mNextLoadEnable = true;
        mLoadMoreEnable = true;
        mRequestLoadMoreListener = requestLoadMoreListener;
//        disableLoadMoreIfNotFullPage(mRecyclerView);
    }

    public interface OnItemChildClickListener {

        void onItemChildClick(BaseRecycleAdapter adapter, View view, int position);
    }

    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        mOnItemChildClickListener = listener;
    }

    @Nullable
    public OnItemChildClickListener getOnItemChildClickListener() {
        return mOnItemChildClickListener;
    }

    public interface OnItemChildLongClickListener {

        boolean onItemChildLongClick(BaseRecycleAdapter adapter, View view, int position);
    }

    public void setOnItemChildLongClickListener(OnItemChildLongClickListener listener) {
        mOnItemChildLongClickListener = listener;
    }


    @Nullable
    public final OnItemChildLongClickListener getOnItemChildLongClickListener() {
        return mOnItemChildLongClickListener;
    }

    public interface OnItemLongClickListener {

        boolean onItemLongClick(BaseRecycleAdapter adapter, View view, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    public final OnItemLongClickListener getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    public final OnSwipeClickListener getOnSwipeClickListener() {
        return mSwipeClickListener;
    }

    public void setOnSwipeClickListener(OnSwipeClickListener listener) {
        mSwipeClickListener = listener;
    }

    public interface OnSwipeClickListener {

        void OnSwipeClick(BaseRecycleAdapter adapter, View view, int position);
    }
    /*=======================================数据设置和清除==========================*/

    public void setNewData(@Nullable List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : data;
        if (mRequestLoadMoreListener != null) {
            mLoading = false;
            mNextLoadEnable = false;
            mLoadMoreView.setLoadMoreStatus(LoadMoreView.STATUS_DEFAULT);
        }
        notifyDataSetChanged();
    }


    public void addData(@IntRange(from = 0) int position, @NonNull T data) {
        mData.add(position, data);
        notifyItemInserted(position + getHeaderLayoutCount());
        compatibilityDataSizeChanged(1);
    }


    public void addData(@NonNull T data) {
        mData.add(data);
        notifyItemInserted(mData.size() + getHeaderLayoutCount());
        compatibilityDataSizeChanged(1);
    }


    public void remove(@IntRange(from = 0) int position) {
        mData.remove(position);
        int internalPosition = position + getHeaderLayoutCount();
        notifyItemRemoved(internalPosition);
        compatibilityDataSizeChanged(0);
        notifyItemRangeChanged(internalPosition, mData.size() - internalPosition);
    }


    public void setData(@IntRange(from = 0) int index, @NonNull T data) {
        mData.set(index, data);
        notifyItemChanged(index + getHeaderLayoutCount());
    }


    public void addData(@IntRange(from = 0) int position, @NonNull Collection<? extends T> newData) {
        mData.addAll(position, newData);
        notifyItemRangeInserted(position + getHeaderLayoutCount(), newData.size());
        compatibilityDataSizeChanged(newData.size());
    }

    public void addData(@NonNull Collection<? extends T> newData) {
        mData.addAll(newData);
        notifyItemRangeInserted(mData.size() - newData.size() + getHeaderLayoutCount(), newData.size());
        compatibilityDataSizeChanged(newData.size());
    }


    public void replaceData(@NonNull Collection<? extends T> data) {
        if (data != mData) {
            mData.clear();
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }


    private void compatibilityDataSizeChanged(int size) {
        final int dataSize = mData == null ? 0 : mData.size();
        if (dataSize == size) {
            notifyDataSetChanged();
        }
    }

    @NonNull
    public List<T> getData() {
        return mData;
    }

    /*==========================单选多选==================================*/

    private boolean mEnableItemChecked = false;

    public void setCheckHelper(CheckHelper helper) {
        mEnableItemChecked = true;
        mCheckHelper = helper;
    }

}

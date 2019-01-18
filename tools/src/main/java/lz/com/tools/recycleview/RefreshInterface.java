package lz.com.tools.recycleview;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import lz.com.tools.recycleview.BaseRefrashHeader;
import lz.com.tools.recycleview.RefreshState;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;
import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;
import static androidx.annotation.RestrictTo.Scope.SUBCLASSES;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-18       创建class
 */
public interface RefreshInterface {
    /**
     * 手指拖动下拉（会连续多次调用，添加isDragging并取代之前的onPulling、onReleasing）
     *
     * @param isDragging    true 手指正在拖动 false 回弹动画
     * @param percent       下拉的百分比 值 = offset/footerHeight (0 - percent - (footerHeight+maxDragHeight) / footerHeight )
     * @param offset        下拉的像素偏移量  0 - offset - (footerHeight+maxDragHeight)
     * @param height        高度 HeaderHeight or FooterHeight
     * @param maxDragHeight 最大拖动高度
     */
    @RestrictTo({LIBRARY, LIBRARY_GROUP, SUBCLASSES})
    void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight);

    /**
     * 状态改变事件 {@link RefreshState}
     *
     * @param refreshLayout RefreshLayout
     * @param oldState      改变之前的状态
     * @param newState      改变之后的状态
     */
    @RestrictTo({LIBRARY, LIBRARY_GROUP, SUBCLASSES})
    void onStateChanged(@NonNull BaseRefrashHeader refreshLayout, @NonNull int oldState, @NonNull int newState);

}

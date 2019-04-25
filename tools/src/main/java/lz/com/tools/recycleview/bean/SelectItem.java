package lz.com.tools.recycleview.bean;

import androidx.annotation.Nullable;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-29       创建class
 */
public abstract class SelectItem {

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof SelectItem) {
            return ((SelectItem) obj).getSelectIndex().equals(this.getSelectIndex());
        }
        return false;


    }

    @Override
    public int hashCode() {
        return getSelectIndex().hashCode();
    }

    protected abstract String getSelectIndex();
}

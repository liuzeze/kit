package lz.com.tools.linkedlist;

import androidx.annotation.Nullable;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-05-10       创建class
 */
public class LinkBean {

    private String groupTag = "";


    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof LinkBean) {
            return ((LinkBean) obj).groupTag.equals(this.groupTag);
        }
        return false;


    }

    @Override
    public int hashCode() {
        return groupTag.hashCode();
    }

    public String getGroupTag() {
        return groupTag;
    }

    public LinkBean setGroupTag(String groupTag) {
        this.groupTag = groupTag;
        return this;
    }

}

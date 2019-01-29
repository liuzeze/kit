package lz.com.kit.bean;

import androidx.annotation.Nullable;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-28       创建class
 */
public class SelectBean {
    public String name = "";

    @Override
    public boolean equals(@Nullable Object obj) {

        return  ((SelectBean) obj).name.equals(name);
    }
}

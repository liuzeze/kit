package lz.com.kit.bean;

import androidx.fragment.app.FragmentActivity;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-18       创建class
 */
public class ActivityBean {
    public String name = "";
    public Class<? extends FragmentActivity> calssName;

    public ActivityBean setName(String name) {
        this.name = name;
        return this;
    }

    public ActivityBean setCalssName(Class<? extends FragmentActivity> calssName) {
        this.calssName = calssName;
        return this;
    }
}

package lz.com.kit.bean;

import android.app.Activity;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-18       创建class
 */
public class ActivityBean {
    public String name = "";
    public Class<? extends Activity> calssName;

    public ActivityBean setName(String name) {
        this.name = name;
        return this;
    }

    public ActivityBean setCalssName(Class<? extends Activity> calssName) {
        this.calssName = calssName;
        return this;
    }
}

package lz.com.kit;

import android.app.Application;

import lz.com.tools.util.LzUtil;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-22       创建class
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LzUtil.init(this);
    }
}

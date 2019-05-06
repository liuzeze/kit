package lz.com.kit.app;

import android.app.Application;

import com.lz.fram.net.RxRequestUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import lz.com.kit.BuildConfig;
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

        RxRequestUtils.initConfig(new GlobalConfiguration());
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .methodOffset(7)
                .logStrategy(new LogcatLogStrategy())
                .tag("lz_tag")
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }
}

package lz.com.tools.util;

import android.content.Context;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-01-22       创建class
 */
public class LzUtil {

    private LzUtil() {
    }

    private static Context sContext;

    public static void init(Context context) {
        sContext = context;
    }

    public static Context getApp() {
        return sContext;
    }
}

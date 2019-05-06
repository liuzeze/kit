package lz.com.tools.util;

import android.content.Context;

import static lz.com.tools.util.LzDisplayUtils.getDensity;
import static lz.com.tools.util.LzDisplayUtils.getFontDensity;

/**
 * 常用单位转换的辅助类
 */
public class LzDp2Px {
    private LzDp2Px() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }



    /**
     * 单位转换: dp -> px
     *
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        return (int) (getDensity(context) * dp + 0.5);
    }

    /**
     * 单位转换: sp -> px
     *
     * @param sp
     * @return
     */
    public static int sp2px(Context context, float sp) {
        return (int) (getFontDensity(context) * sp + 0.5);
    }

    /**
     * 单位转换:px -> dp
     *
     * @param px
     * @return
     */
    public static int px2dp(Context context, float px) {
        return (int) (px / getDensity(context) + 0.5);
    }

    /**
     * 单位转换:px -> sp
     *
     * @param px
     * @return
     */
    public static int px2sp(Context context, float px) {
        return (int) (px / getFontDensity(context) + 0.5);
    }

}

package lz.com.tools.util;


import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2018-07-12       先不要使用
 */
public class LzTimeUtils {


    /**
     * <pre>
     *                          HH:mm    15:44
     *                         h:mm a    3:44 下午
     *                        HH:mm z    15:44 CST
     *                        HH:mm Z    15:44 +0800
     *                     HH:mm zzzz    15:44 中国标准时间
     *                       HH:mm:ss    15:44:40
     *                     yyyy-MM-dd    2016-08-12
     *               yyyy-MM-dd HH:mm    2016-08-12 15:44
     *            yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
     *       yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
     *  EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
     *       yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     *   yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
     *                         K:mm a    3:44 下午
     *               EEE, MMM d, ''yy    星期五, 八月 12, '16
     *          hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
     *   yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
     *     EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
     *                  yyMMddHHmmssZ    160812154440+0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
     * </pre>
     */
    /*
     * 毫秒与毫秒的倍数
     */
    public static final int MSEC = 1;

    /******************** 时间相关常量 ********************/
    /**
     * 秒与毫秒的倍数
     */
    public static final int SEC = 1000;
    /**
     * 分与毫秒的倍数
     */
    public static final int MIN = 60000;
    /**
     * 时与毫秒的倍数
     */
    public static final int HOUR = 3600000;
    /**
     * 天与毫秒的倍数
     */
    public static final int DAY = 86400000;

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }

    //年月日 时分秒
    public static final String DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    //年月日 时分
    public static final String DATE_FORMAT_YMDHM = "yyyy-MM-dd HH:mm";
    //年月日
    public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";
    //年月
    public static final String DATE_FORMAT_YM = "yyyy-MM";
    //月日
    public static final String DATE_FORMAT_MD = "MM-dd";
    // 时分秒
    public static final String DATE_FORMAT_HMS = "HH:mm:ss";
    // 时分
    public static final String DATE_FORMAT_HM = "HH:mm";
    //年月日
    public static final String DATE_FORMAT2_YMD = "yyyy/MM/dd";
    public static final String DATE_FORMAT3_YMD = "yyyy年MM月dd日";


    private LzTimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Milliseconds to the formatted time string.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param millis The milliseconds.
     * @return the formatted time string
     */
    public static String millis2String(final long millis) {
        return millis2String(millis, DATE_FORMAT_YMDHMS);
    }

    /**
     * Milliseconds to the formatted time string.
     *
     * @param millis The milliseconds.
     * @param format The format.
     * @return the formatted time string
     */
    public static String millis2String(final long millis, @NonNull final String format) {
        return new SimpleDateFormat(format).format(new Date(millis));
    }

    /**
     * Formatted time string to the milliseconds.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the milliseconds
     */
    public static long string2Millis(final String time) {
        return string2Millis(time, DATE_FORMAT_YMDHMS);
    }

    /**
     * Formatted time string to the milliseconds.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the milliseconds
     */
    public static long string2Millis(final String time, @NonNull final String format) {
        try {
            return new SimpleDateFormat(format).parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getStringFormat(final String inputTime,
                                         @NonNull final String inputFormat,
                                         @NonNull final String outFormat) {
        return new SimpleDateFormat(outFormat).format(string2Millis(inputTime, inputFormat));

    }

    public static String getStringFormat(final String inputTime,
                                         @NonNull final String outFormat) {
        return new SimpleDateFormat(outFormat).format(string2Millis(inputTime, DATE_FORMAT_YMDHMS));

    }


    /**
     * Formatted time string to the date.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the date
     */
    public static Date string2Date(final String time) {
        return string2Date(time, DATE_FORMAT_YMDHMS);
    }

    /**
     * Formatted time string to the date.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the date
     */
    public static Date string2Date(final String time, @NonNull final String format) {
        try {
            return new SimpleDateFormat(format).parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Date to the formatted time string.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param date The date.
     * @return the formatted time string
     */
    public static String date2String(final Date date) {
        return date2String(date, DATE_FORMAT_YMDHMS);
    }

    /**
     * Date to the formatted time string.
     *
     * @param date   The date.
     * @param format The format.
     * @return the formatted time string
     */
    public static String date2String(final Date date, @NonNull final String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * Date to the milliseconds.
     *
     * @param date The date.
     * @return the milliseconds
     */
    public static long date2Millis(final Date date) {
        return date.getTime();
    }

    /**
     * Milliseconds to the date.
     *
     * @param millis The milliseconds.
     * @return the date
     */
    public static Date millis2Date(final long millis) {
        return new Date(millis);
    }

    /**
     * Return the time span, in unit.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time1 The first formatted time string.
     * @param time2 The second formatted time string.
     * @param unit  The unit of time span.
     * @return the time span, in unit
     */
    public static long getTimeSpan(final String time1,
                                   final String time2,
                                   @Unit final int unit) {
        return getTimeSpan(time1, time2, DATE_FORMAT_YMDHMS, unit);
    }

    /**
     * Return the time span, in unit.
     *
     * @param time1  The first formatted time string.
     * @param time2  The second formatted time string.
     * @param format The format.
     * @param unit   The unit of time span.
     * @return the time span, in unit
     */
    public static long getTimeSpan(final String time1,
                                   final String time2,
                                   @NonNull final String format,
                                   @Unit final int unit) {
        return millis2TimeSpan(string2Millis(time1, format) - string2Millis(time2, format), unit);
    }

    /**
     * Return the time span, in unit.
     *
     * @param date1 The first date.
     * @param date2 The second date.
     * @param unit  The unit of time span.
     * @return the time span, in unit
     */
    public static long getTimeSpan(final Date date1,
                                   final Date date2,
                                   @Unit final int unit) {
        return millis2TimeSpan(date2Millis(date1) - date2Millis(date2), unit);
    }

    /**
     * Return the time span, in unit.
     *
     * @param millis1 The first milliseconds.
     * @param millis2 The second milliseconds.
     * @param unit    The unit of time span.
     * @return the time span, in unit
     */
    public static long getTimeSpan(final long millis1,
                                   final long millis2,
                                   @Unit final int unit) {
        return millis2TimeSpan(millis1 - millis2, unit);
    }

    /**
     * Return the fit time span.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time1     The first formatted time string.
     * @param time2     The second formatted time string.
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return the fit time span
     */
    public static String getFitTimeSpan(final String time1,
                                        final String time2,
                                        final int precision) {
        long delta = string2Millis(time1, DATE_FORMAT_YMDHMS) - string2Millis(time2, DATE_FORMAT_YMDHMS);
        return millis2FitTimeSpan(delta, precision);
    }

    /**
     * Return the fit time span.
     *
     * @param time1     The first formatted time string.
     * @param time2     The second formatted time string.
     * @param format    The format.
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return the fit time span
     */
    public static String getFitTimeSpan(final String time1,
                                        final String time2,
                                        @NonNull final String format,
                                        final int precision) {
        long delta = string2Millis(time1, format) - string2Millis(time2, format);
        return millis2FitTimeSpan(delta, precision);
    }

    /**
     * Return the fit time span.
     *
     * @param date1     The first date.
     * @param date2     The second date.
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return the fit time span
     */
    public static String getFitTimeSpan(final Date date1, final Date date2, final int precision) {
        return millis2FitTimeSpan(date2Millis(date1) - date2Millis(date2), precision);
    }

    /**
     * Return the fit time span.
     *
     * @param millis1   The first milliseconds.
     * @param millis2   The second milliseconds.
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return the fit time span
     */
    public static String getFitTimeSpan(final long millis1,
                                        final long millis2,
                                        final int precision) {
        return millis2FitTimeSpan(millis1 - millis2, precision);
    }

    /**
     * Return the current time in milliseconds.
     *
     * @return the current time in milliseconds
     */
    public static long getNowMills() {
        return System.currentTimeMillis();
    }

    /**
     * Return the current formatted time string.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @return the current formatted time string
     */
    public static String getNowString() {
        return millis2String(System.currentTimeMillis(), DATE_FORMAT_YMDHMS);
    }

    /**
     * Return the current formatted time string.
     *
     * @param format The format.
     * @return the current formatted time string
     */
    public static String getNowString(@NonNull final String format) {
        return millis2String(System.currentTimeMillis(), format);
    }

    /**
     * Return the current date.
     *
     * @return the current date
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * Return the time span by now, in unit.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @param unit The unit of time span.
     * @return the time span by now, in unit
     */
    public static long getTimeSpanByNow(final String time, @Unit final int unit) {
        return getTimeSpan(time, getNowString(), DATE_FORMAT_YMDHMS, unit);
    }

    /**
     * Return the time span by now, in unit.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @param unit   The unit of time span.
     * @return the time span by now, in unit
     */
    public static long getTimeSpanByNow(final String time,
                                        @NonNull final String format,
                                        @Unit final int unit) {
        return getTimeSpan(time, getNowString(format), format, unit);
    }

    /**
     * Return the time span by now, in unit.
     *
     * @param date The date.
     * @param unit The unit of time span.
     * @return the time span by now, in unit
     */
    public static long getTimeSpanByNow(final Date date, @Unit final int unit) {
        return getTimeSpan(date, new Date(), unit);
    }

    /**
     * Return the time span by now, in unit.
     *
     * @param millis The milliseconds.
     * @param unit   The unit of time span.
     * @return the time span by now, in unit
     */
    public static long getTimeSpanByNow(final long millis, @Unit final int unit) {
        return getTimeSpan(millis, System.currentTimeMillis(), unit);
    }

    /**
     * Return the fit time span by now.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time      The formatted time string.
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return the fit time span by now
     */
    public static String getFitTimeSpanByNow(final String time, final int precision) {
        return getFitTimeSpan(time, getNowString(), DATE_FORMAT_YMDHMS, precision);
    }

    /**
     * Return the fit time span by now.
     *
     * @param time      The formatted time string.
     * @param format    The format.
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return the fit time span by now
     */
    public static String getFitTimeSpanByNow(final String time,
                                             @NonNull final String format,
                                             final int precision) {
        return getFitTimeSpan(time, getNowString(format), format, precision);
    }

    /**
     * Return the fit time span by now.
     *
     * @param date      The date.
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return the fit time span by now
     */
    public static String getFitTimeSpanByNow(final Date date, final int precision) {
        return getFitTimeSpan(date, getNowDate(), precision);
    }

    /**
     * Return the fit time span by now.
     *
     * @param millis    The milliseconds.
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return the fit time span by now
     */
    public static String getFitTimeSpanByNow(final long millis, final int precision) {
        return getFitTimeSpan(millis, System.currentTimeMillis(), precision);
    }

    /**
     * Return the friendly time span by now.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the friendly time span by now
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final String time) {
        return getFriendlyTimeSpanByNow(time, DATE_FORMAT_YMDHMS);
    }

    /**
     * Return the friendly time span by now.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the friendly time span by now
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final String time,
                                                  @NonNull final String format) {
        return getFriendlyTimeSpanByNow(string2Millis(time, format));
    }

    /**
     * Return the friendly time span by now.
     *
     * @param date The date.
     * @return the friendly time span by now
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final Date date) {
        return getFriendlyTimeSpanByNow(date.getTime());
    }

    /**
     * Return the friendly time span by now.
     *
     * @param millis The milliseconds.
     * @return the friendly time span by now
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0) {
            // U can read http://www.apihome.cn/api/java/Formatter.html to understand it.
            return String.format("%tc", millis);
        }
        if (span < 1000) {
            return "刚刚";
        } else if (span < MIN) {
            return String.format(Locale.getDefault(), "%d秒前", span / SEC);
        } else if (span < HOUR) {
            return String.format(Locale.getDefault(), "%d分钟前", span / MIN);
        }
        // 获取当天 00:00
        long wee = getWeeOfToday();
        if (millis >= wee) {
            return String.format("今天%tR", millis);
        } else if (millis >= wee - DAY) {
            return String.format("昨天%tR", millis);
        } else {
            return String.format("%tF", millis);
        }
    }

    private static long getWeeOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * Return the milliseconds differ time span.
     *
     * @param millis   The milliseconds.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     * @return the milliseconds differ time span
     */
    public static long getMillis(final long millis,
                                 final long timeSpan,
                                 @Unit final int unit) {
        return millis + timeSpan2Millis(timeSpan, unit);
    }

    /**
     * Return the milliseconds differ time span.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time     The formatted time string.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     * @return the milliseconds differ time span
     */
    public static long getMillis(final String time,
                                 final long timeSpan,
                                 @Unit final int unit) {
        return getMillis(time, DATE_FORMAT_YMDHMS, timeSpan, unit);
    }

    /**
     * Return the milliseconds differ time span.
     *
     * @param time     The formatted time string.
     * @param format   The format.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     * @return the milliseconds differ time span.
     */
    public static long getMillis(final String time,
                                 @NonNull final String format,
                                 final long timeSpan,
                                 @Unit final int unit) {
        return string2Millis(time, format) + timeSpan2Millis(timeSpan, unit);
    }

    /**
     * Return the milliseconds differ time span.
     *
     * @param date     The date.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     * @return the milliseconds differ time span.
     */
    public static long getMillis(final Date date,
                                 final long timeSpan,
                                 @Unit final int unit) {
        return date2Millis(date) + timeSpan2Millis(timeSpan, unit);
    }

    /**
     * Return the formatted time string differ time span.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param millis   The milliseconds.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     * @return the formatted time string differ time span
     */
    public static String getString(final long millis,
                                   final long timeSpan,
                                   @Unit final int unit) {
        return getString(millis, DATE_FORMAT_YMDHMS, timeSpan, unit);
    }

    /**
     * Return the formatted time string differ time span.
     *
     * @param millis   The milliseconds.
     * @param format   The format.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     * @return the formatted time string differ time span
     */
    public static String getString(final long millis,
                                   @NonNull final String format,
                                   final long timeSpan,
                                   @Unit final int unit) {
        return millis2String(millis + timeSpan2Millis(timeSpan, unit), format);
    }

    /**
     * Return the formatted time string differ time span.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time     The formatted time string.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link #MSEC}</li>
     *                 <li>{@link #SEC }</li>
     *                 <li>{@link #MIN }</li>
     *                 <li>{@link #HOUR}</li>
     *                 <li>{@link #DAY }</li>
     *                 </ul>
     * @return the formatted time string differ time span
     */
    public static String getString(final String time,
                                   final long timeSpan,
                                   @Unit final int unit) {
        return getString(time, DATE_FORMAT_YMDHMS, timeSpan, unit);
    }

    /**
     * Return the formatted time string differ time span.
     *
     * @param time     The formatted time string.
     * @param format   The format.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link #MSEC}</li>
     *                 <li>{@link #SEC }</li>
     *                 <li>{@link #MIN }</li>
     *                 <li>{@link #HOUR}</li>
     *                 <li>{@link #DAY }</li>
     *                 </ul>
     * @return the formatted time string differ time span
     */
    public static String getString(final String time,
                                   @NonNull final String format,
                                   final long timeSpan,
                                   @Unit final int unit) {
        return millis2String(string2Millis(time, format) + timeSpan2Millis(timeSpan, unit), format);
    }

    /**
     * Return the formatted time string differ time span.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param date     The date.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link #MSEC}</li>
     *                 <li>{@link #SEC }</li>
     *                 <li>{@link #MIN }</li>
     *                 <li>{@link #HOUR}</li>
     *                 <li>{@link #DAY }</li>
     *                 </ul>
     * @return the formatted time string differ time span
     */
    public static String getString(final Date date,
                                   final long timeSpan,
                                   @Unit final int unit) {
        return getString(date, DATE_FORMAT_YMDHMS, timeSpan, unit);
    }

    /**
     * Return the formatted time string differ time span.
     *
     * @param date     The date.
     * @param format   The format.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link #MSEC}</li>
     *                 <li>{@link #SEC }</li>
     *                 <li>{@link #MIN }</li>
     *                 <li>{@link #HOUR}</li>
     *                 <li>{@link #DAY }</li>
     *                 </ul>
     * @return the formatted time string differ time span
     */
    public static String getString(final Date date,
                                   @NonNull final String format,
                                   final long timeSpan,
                                   @Unit final int unit) {
        return millis2String(date2Millis(date) + timeSpan2Millis(timeSpan, unit), format);
    }


    /**
     * Return the date differ time span.
     *
     * @param millis   The milliseconds.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link #MSEC}</li>
     *                 <li>{@link #SEC }</li>
     *                 <li>{@link #MIN }</li>
     *                 <li>{@link #HOUR}</li>
     *                 <li>{@link #DAY }</li>
     *                 </ul>
     * @return the date differ time span
     */
    public static Date getDate(final long millis,
                               final long timeSpan,
                               @Unit final int unit) {
        return millis2Date(millis + timeSpan2Millis(timeSpan, unit));
    }

    /**
     * Return the date differ time span.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time     The formatted time string.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link #MSEC}</li>
     *                 <li>{@link #SEC }</li>
     *                 <li>{@link #MIN }</li>
     *                 <li>{@link #HOUR}</li>
     *                 <li>{@link #DAY }</li>
     *                 </ul>
     * @return the date differ time span
     */
    public static Date getDate(final String time,
                               final long timeSpan,
                               @Unit final int unit) {
        return getDate(time, DATE_FORMAT_YMDHMS, timeSpan, unit);
    }

    /**
     * Return the date differ time span.
     *
     * @param time     The formatted time string.
     * @param format   The format.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link #MSEC}</li>
     *                 <li>{@link #SEC }</li>
     *                 <li>{@link #MIN }</li>
     *                 <li>{@link #HOUR}</li>
     *                 <li>{@link #DAY }</li>
     *                 </ul>
     * @return the date differ time span
     */
    public static Date getDate(final String time,
                               @NonNull final String format,
                               final long timeSpan,
                               @Unit final int unit) {
        return millis2Date(string2Millis(time, format) + timeSpan2Millis(timeSpan, unit));
    }

    /**
     * Return the date differ time span.
     *
     * @param date     The date.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link #MSEC}</li>
     *                 <li>{@link #SEC }</li>
     *                 <li>{@link #MIN }</li>
     *                 <li>{@link #HOUR}</li>
     *                 <li>{@link #DAY }</li>
     *                 </ul>
     * @return the date differ time span
     */
    public static Date getDate(final Date date,
                               final long timeSpan,
                               @Unit final int unit) {
        return millis2Date(date2Millis(date) + timeSpan2Millis(timeSpan, unit));
    }

    /**
     * Return the milliseconds differ time span by now.
     *
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link #MSEC}</li>
     *                 <li>{@link #SEC }</li>
     *                 <li>{@link #MIN }</li>
     *                 <li>{@link #HOUR}</li>
     *                 <li>{@link #DAY }</li>
     *                 </ul>
     * @return the milliseconds differ time span by now
     */
    public static long getMillisByNow(final long timeSpan, @Unit final int unit) {
        return getMillis(getNowMills(), timeSpan, unit);
    }

    /**
     * Return the formatted time string differ time span by now.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link #MSEC}</li>
     *                 <li>{@link #SEC }</li>
     *                 <li>{@link #MIN }</li>
     *                 <li>{@link #HOUR}</li>
     *                 <li>{@link #DAY }</li>
     *                 </ul>
     * @return the formatted time string differ time span by now
     */
    public static String getStringByNow(final long timeSpan, @Unit final int unit) {
        return getStringByNow(timeSpan, DATE_FORMAT_YMDHMS, unit);
    }

    /**
     * Return the formatted time string differ time span by now.
     *
     * @param timeSpan The time span.
     * @param format   The format.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link #MSEC}</li>
     *                 <li>{@link #SEC }</li>
     *                 <li>{@link #MIN }</li>
     *                 <li>{@link #HOUR}</li>
     *                 <li>{@link #DAY }</li>
     *                 </ul>
     * @return the formatted time string differ time span by now
     */
    public static String getStringByNow(final long timeSpan,
                                        @NonNull final String format,
                                        @Unit final int unit) {
        return getString(getNowMills(), format, timeSpan, unit);
    }

    /**
     * Return the date differ time span by now.
     *
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link #MSEC}</li>
     *                 <li>{@link #SEC }</li>
     *                 <li>{@link #MIN }</li>
     *                 <li>{@link #HOUR}</li>
     *                 <li>{@link #DAY }</li>
     *                 </ul>
     * @return the date differ time span by now
     */
    public static Date getDateByNow(final long timeSpan, @Unit final int unit) {
        return getDate(getNowMills(), timeSpan, unit);
    }

    /**
     * Return whether it is today.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isToday(final String time) {
        return isToday(string2Millis(time, DATE_FORMAT_YMDHMS));
    }

    /**
     * Return whether it is today.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isToday(final String time, @NonNull final String format) {
        return isToday(string2Millis(time, format));
    }

    /**
     * Return whether it is today.
     *
     * @param date The date.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isToday(final Date date) {
        return isToday(date.getTime());
    }

    /**
     * Return whether it is today.
     *
     * @param millis The milliseconds.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isToday(final long millis) {
        long wee = getWeeOfToday();
        return millis >= wee && millis < wee + DAY;
    }

    /**
     * Return whether it is leap year.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final String time) {
        return isLeapYear(string2Date(time, DATE_FORMAT_YMDHMS));
    }

    /**
     * Return whether it is leap year.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final String time, @NonNull final String format) {
        return isLeapYear(string2Date(time, format));
    }

    /**
     * Return whether it is leap year.
     *
     * @param date The date.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    /**
     * Return whether it is leap year.
     *
     * @param millis The milliseconds.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final long millis) {
        return isLeapYear(millis2Date(millis));
    }

    /**
     * Return whether it is leap year.
     *
     * @param year The year.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * Return the day of week in Chinese.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the day of week in Chinese
     */
    public static String getChineseWeek(final String time) {
        return getChineseWeek(string2Date(time, DATE_FORMAT_YMDHMS));
    }

    /**
     * Return the day of week in Chinese.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the day of week in Chinese
     */
    public static String getChineseWeek(final String time, @NonNull final String format) {
        return getChineseWeek(string2Date(time, format));
    }

    /**
     * Return the day of week in Chinese.
     *
     * @param date The date.
     * @return the day of week in Chinese
     */
    public static String getChineseWeek(final Date date) {
        return new SimpleDateFormat("E", Locale.CHINA).format(date);
    }

    /**
     * Return the day of week in Chinese.
     *
     * @param millis The milliseconds.
     * @return the day of week in Chinese
     */
    public static String getChineseWeek(final long millis) {
        return getChineseWeek(new Date(millis));
    }

    /**
     * Return the day of week in US.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the day of week in US
     */
    public static String getUSWeek(final String time) {
        return getUSWeek(string2Date(time, DATE_FORMAT_YMDHMS));
    }

    /**
     * Return the day of week in US.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the day of week in US
     */
    public static String getUSWeek(final String time, @NonNull final String format) {
        return getUSWeek(string2Date(time, format));
    }

    /**
     * Return the day of week in US.
     *
     * @param date The date.
     * @return the day of week in US
     */
    public static String getUSWeek(final Date date) {
        return new SimpleDateFormat("EEEE", Locale.US).format(date);
    }

    /**
     * Return the day of week in US.
     *
     * @param millis The milliseconds.
     * @return the day of week in US
     */
    public static String getUSWeek(final long millis) {
        return getUSWeek(new Date(millis));
    }

    /**
     * Return the number for indicating the day of the week.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the number for indicating the day of the week
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     * @deprecated use {@link #getValueByCalendarField(String, int)} instead,
     * the param of field should equals {@link Calendar#DAY_OF_WEEK}
     */
    @Deprecated
    public static int getWeekIndex(final String time) {
        return getWeekIndex(string2Date(time, DATE_FORMAT_YMDHMS));
    }

    /**
     * Return the number for indicating the day of the week.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the number for indicating the day of the week
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     * @deprecated use {@link #getValueByCalendarField(String, String, int)} instead,
     * the param of field should equals {@link Calendar#DAY_OF_WEEK}
     */
    @Deprecated
    public static int getWeekIndex(final String time, @NonNull final String format) {
        return getWeekIndex(string2Date(time, format));
    }

    /**
     * Return the number for indicating the day of the week.
     *
     * @param date The date.
     * @return the number for indicating the day of the week
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     * @deprecated use {@link #getValueByCalendarField(Date, int)} instead,
     * the param of field should equals {@link Calendar#DAY_OF_WEEK}
     */
    @Deprecated
    public static int getWeekIndex(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Return the number for indicating the day of the week.
     *
     * @param millis The milliseconds.
     * @return the number for indicating the day of the week
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     * @deprecated use {@link #getValueByCalendarField(long, int)} instead,
     * the param of field should equals {@link Calendar#DAY_OF_WEEK}
     */
    @Deprecated
    public static int getWeekIndex(final long millis) {
        return getWeekIndex(millis2Date(millis));
    }

    /**
     * Return the number for indicating the week number within the current month.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the number for indicating the week number within the current month
     * @deprecated use {@link #getValueByCalendarField(String, int)} instead,
     * the param of field should equals {@link Calendar#WEEK_OF_MONTH}
     */
    @Deprecated
    public static int getWeekOfMonth(final String time) {
        return getWeekOfMonth(string2Date(time, DATE_FORMAT_YMDHMS));
    }

    /**
     * Return the number for indicating the week number within the current month.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the number for indicating the week number within the current month
     * @deprecated use {@link #getValueByCalendarField(String, String, int)} instead,
     * the param of field should equals {@link Calendar#WEEK_OF_MONTH}
     */
    @Deprecated
    public static int getWeekOfMonth(final String time, @NonNull final String format) {
        return getWeekOfMonth(string2Date(time, format));
    }

    /**
     * Return the number for indicating the week number within the current month.
     *
     * @param date The date.
     * @return the number for indicating the week number within the current month
     * @deprecated use {@link #getValueByCalendarField(Date, int)} instead,
     * the param of field should equals {@link Calendar#WEEK_OF_MONTH}
     */
    @Deprecated
    public static int getWeekOfMonth(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * Return the number for indicating the week number within the current month.
     *
     * @param millis The milliseconds.
     * @return the number for indicating the week number within the current month
     * @deprecated use {@link #getValueByCalendarField(long, int)} instead,
     * the param of field should equals {@link Calendar#WEEK_OF_MONTH}
     */
    @Deprecated
    public static int getWeekOfMonth(final long millis) {
        return getWeekOfMonth(millis2Date(millis));
    }

    /**
     * Return the number for indicating the week number within the current year.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the number for indicating the week number within the current year
     * @deprecated use {@link #getValueByCalendarField(String, int)} instead,
     * the param of field should equals {@link Calendar#WEEK_OF_YEAR}
     */
    @Deprecated
    public static int getWeekOfYear(final String time) {
        return getWeekOfYear(string2Date(time, DATE_FORMAT_YMDHMS));
    }

    /**
     * Return the number for indicating the week number within the current year.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the number for indicating the week number within the current year
     * @deprecated use {@link #getValueByCalendarField(String, String, int)} instead,
     * the param of field should equals {@link Calendar#WEEK_OF_YEAR}
     */
    @Deprecated
    public static int getWeekOfYear(final String time, @NonNull final String format) {
        return getWeekOfYear(string2Date(time, format));
    }

    /**
     * Return the number for indicating the week number within the current year.
     *
     * @param date The date.
     * @return the number for indicating the week number within the current year
     * @deprecated use {@link #getValueByCalendarField(Date, int)} instead,
     * the param of field should equals {@link Calendar#WEEK_OF_YEAR}
     */
    @Deprecated
    public static int getWeekOfYear(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * Return the number for indicating the week number within the current year.
     *
     * @param millis The milliseconds.
     * @return the number for indicating the week number within the current year
     * @deprecated use {@link #getValueByCalendarField(long, int)} instead,
     * the param of field should equals {@link Calendar#WEEK_OF_YEAR}
     */
    @Deprecated
    public static int getWeekOfYear(final long millis) {
        return getWeekOfYear(millis2Date(millis));
    }

    /**
     * Returns the value of the given calendar field.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time  The formatted time string.
     * @param field The given calendar field.
     *              <ul>
     *              <li>{@link Calendar#ERA}</li>
     *              <li>{@link Calendar#YEAR}</li>
     *              <li>{@link Calendar#MONTH}</li>
     *              <li>...</li>
     *              <li>{@link Calendar#DST_OFFSET}</li>
     *              </ul>
     * @return the value of the given calendar field
     */
    public static int getValueByCalendarField(final String time, final int field) {
        return getValueByCalendarField(string2Date(time, DATE_FORMAT_YMDHMS), field);
    }

    /**
     * Returns the value of the given calendar field.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @param field  The given calendar field.
     *               <ul>
     *               <li>{@link Calendar#ERA}</li>
     *               <li>{@link Calendar#YEAR}</li>
     *               <li>{@link Calendar#MONTH}</li>
     *               <li>...</li>
     *               <li>{@link Calendar#DST_OFFSET}</li>
     *               </ul>
     * @return the value of the given calendar field
     */
    public static int getValueByCalendarField(final String time,
                                              @NonNull final String format,
                                              final int field) {
        return getValueByCalendarField(string2Date(time, format), field);
    }

    /**
     * Returns the value of the given calendar field.
     *
     * @param date  The date.
     * @param field The given calendar field.
     *              <ul>
     *              <li>{@link Calendar#ERA}</li>
     *              <li>{@link Calendar#YEAR}</li>
     *              <li>{@link Calendar#MONTH}</li>
     *              <li>...</li>
     *              <li>{@link Calendar#DST_OFFSET}</li>
     *              </ul>
     * @return the value of the given calendar field
     */
    public static int getValueByCalendarField(final Date date, final int field) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(field);
    }

    /**
     * Returns the value of the given calendar field.
     *
     * @param millis The milliseconds.
     * @param field  The given calendar field.
     *               <ul>
     *               <li>{@link Calendar#ERA}</li>
     *               <li>{@link Calendar#YEAR}</li>
     *               <li>{@link Calendar#MONTH}</li>
     *               <li>...</li>
     *               <li>{@link Calendar#DST_OFFSET}</li>
     *               </ul>
     * @return the value of the given calendar field
     */
    public static int getValueByCalendarField(final long millis, final int field) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return cal.get(field);
    }

    private static final String[] CHINESE_ZODIAC =
            {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};

    /**
     * Return the Chinese zodiac.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the Chinese zodiac
     */
    public static String getChineseZodiac(final String time) {
        return getChineseZodiac(string2Date(time, DATE_FORMAT_YMDHMS));
    }

    /**
     * Return the Chinese zodiac.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the Chinese zodiac
     */
    public static String getChineseZodiac(final String time, @NonNull final String format) {
        return getChineseZodiac(string2Date(time, format));
    }

    /**
     * Return the Chinese zodiac.
     *
     * @param date The date.
     * @return the Chinese zodiac
     */
    public static String getChineseZodiac(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return CHINESE_ZODIAC[cal.get(Calendar.YEAR) % 12];
    }

    /**
     * Return the Chinese zodiac.
     *
     * @param millis The milliseconds.
     * @return the Chinese zodiac
     */
    public static String getChineseZodiac(final long millis) {
        return getChineseZodiac(millis2Date(millis));
    }

    /**
     * Return the Chinese zodiac.
     *
     * @param year The year.
     * @return the Chinese zodiac
     */
    public static String getChineseZodiac(final int year) {
        return CHINESE_ZODIAC[year % 12];
    }

    private static final int[] ZODIAC_FLAGS = {20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};
    private static final String[] ZODIAC = {
            "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座",
            "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"
    };

    /**
     * Return the zodiac.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the zodiac
     */
    public static String getZodiac(final String time) {
        return getZodiac(string2Date(time, DATE_FORMAT_YMDHMS));
    }

    /**
     * Return the zodiac.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the zodiac
     */
    public static String getZodiac(final String time, @NonNull final String format) {
        return getZodiac(string2Date(time, format));
    }

    /**
     * Return the zodiac.
     *
     * @param date The date.
     * @return the zodiac
     */
    public static String getZodiac(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return getZodiac(month, day);
    }

    /**
     * Return the zodiac.
     *
     * @param millis The milliseconds.
     * @return the zodiac
     */
    public static String getZodiac(final long millis) {
        return getZodiac(millis2Date(millis));
    }

    /**
     * Return the zodiac.
     *
     * @param month The month.
     * @param day   The day.
     * @return the zodiac
     */
    public static String getZodiac(final int month, final int day) {
        return ZODIAC[day >= ZODIAC_FLAGS[month - 1]
                ? month - 1
                : (month + 10) % 12];
    }

    private static long timeSpan2Millis(final long timeSpan, @Unit final int unit) {
        return timeSpan * unit;
    }

    private static long millis2TimeSpan(final long millis, @Unit final int unit) {
        return millis / unit;
    }

    private static String millis2FitTimeSpan(long millis, int precision) {
        if (precision <= 0) {
            return null;
        }
        precision = Math.min(precision, 5);
        String[] units = {"天", "小时", "分钟", "秒", "毫秒"};
        if (millis == 0) {
            return 0 + units[precision - 1];
        }
        StringBuilder sb = new StringBuilder();
        if (millis < 0) {
            sb.append("-");
            millis = -millis;
        }
        int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
        for (int i = 0; i < precision; i++) {
            if (millis >= unitLen[i]) {
                long mode = millis / unitLen[i];
                millis -= mode * unitLen[i];
                sb.append(mode).append(units[i]);
            }
        }
        return sb.toString();
    }
}

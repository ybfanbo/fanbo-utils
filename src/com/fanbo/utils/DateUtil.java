package com.fanbo.utils;

import com.fanbo.enums.DateOffsetType;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Description: 日期工具
 * @Author: FanBo
 */
public class DateUtil {

    /**
     * 以毫秒表示的时间:天
     */
    private static final long DAY_IN_MILLIS = 24 * 3600 * 1000;
    /**
     * 以毫秒表示的时间:时
     */
    private static final long HOUR_IN_MILLIS = 3600 * 1000;
    /**
     * 以毫秒表示的时间:分
     */
    private static final long MINUTE_IN_MILLIS = 60 * 1000;
    /**
     * 以毫秒表示的时间:秒
     */
    private static final long SECOND_IN_MILLIS = 1000;

    /**
     * yyyy-MM-dd
     */
    public static ThreadLocal<SimpleDateFormat> date_sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    /**
     * yyyy-MM
     */
    public static ThreadLocal<SimpleDateFormat> yyyy_MM = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM"));

    /**
     * MM-dd
     */
    public static ThreadLocal<SimpleDateFormat> MM_dd = ThreadLocal.withInitial(() -> new SimpleDateFormat("MM-dd"));
    /**
     * dd
     */
    public static ThreadLocal<SimpleDateFormat> dd = ThreadLocal.withInitial(() -> new SimpleDateFormat("dd"));
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static ThreadLocal<SimpleDateFormat> datetimeFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    /**
     * HH:mm
     */
    public static ThreadLocal<SimpleDateFormat> short_time_sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("HH:mm"));
    /**
     * yyyy-MM-dd HH:mm
     */
    public static ThreadLocal<SimpleDateFormat> time_sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm"));

    /**
     * yyyyMMdd
     */
    public static ThreadLocal<SimpleDateFormat> yyyyMMdd = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd"));
    /**
     * yyyyMMddHHmmss
     */
    public static ThreadLocal<SimpleDateFormat> yyyymmddhhmmss = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMddHHmmss"));

    /**
     * yyyy年MM月dd日
     */
    public static ThreadLocal<SimpleDateFormat> date_sdf_wz = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy年MM月dd日"));

    //------------------------------------------------------------------------------------------------------------------

    /**
     * 取得指定时间格式
     *
     * @param pattern 时间格式
     * @return SimpleDateFormat
     */
    private static SimpleDateFormat getSDFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 当前时间，格式 yyyy-MM-dd HH:mm:ss
     *
     * @return 当前时间的标准形式字符串
     */
    public static String now() {
        return datetimeFormat.get().format(getCalendar().getTime());
    }

    /**
     * 昨天，格式 yyyy-MM-dd
     *
     * @return 昨天时间的标准形式字符串
     */
    public static String yesterday() {
        return getOffsetDate(getDate(), DateOffsetType.D, "-1", true);
    }

    /**
     * 上个月，格式 yyyy-MM
     *
     * @return 上个月时间的标准形式字符串
     */
    public static String lastMonth() {
        //上个月的今天yyyy-MM-dd格式
        String lastDay = getOffsetDate(getDate(), DateOffsetType.M, "-1", true);
        //返回yyyy-MM
        return lastDay == null ? null : lastDay.substring(0, 7);
    }

    /**
     * 按照偏移类型和偏移量取得偏移后的日期
     *
     * @param strDate 日期字符串
     * @param type    偏移类型
     * @param offset  偏移量
     * @return 计算后的日期（yyyy-MM-dd）
     */
    public static String getOffsetDate(String strDate, DateOffsetType type, String offset, boolean includeStartDay) {
        // 检查日期格式，转为日期类型
        Date date = string2Date(strDate, true);
        if (date == null) {
            date = string2time(strDate, true);
        }
        if (date == null) {
            return strDate;
        }

        return getOffsetDate(date, type, offset, includeStartDay);
    }

    /**
     * String转Date(yyyy-MM-dd HH:mm:ss)
     *
     * @param strDate    日期字符串
     * @param error2null 杰西错误时返回null
     * @return 日期对象
     */
    public static Date string2time(String strDate, boolean error2null) {
        return string2Date(strDate, "yyyy-MM-dd HH:mm:ss", error2null);
    }

    /**
     * String转Date(yyyy-MM-dd)
     *
     * @param strDate    日期字符串
     * @param error2null 杰西错误时返回null
     * @return 日期对象
     */
    public static Date string2Date(String strDate, boolean error2null) {
        return string2Date(strDate, "yyyy-MM-dd", error2null);
    }

    /**
     * String转Date(yyyy-MM-dd HH:mm:ss)
     *
     * @param strDate 日期字符串
     * @return 日期对象
     */
    public static Date string2time(String strDate) {
        return string2Date(strDate, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * String 转 Date
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return 日期对象
     */
    public static Date string2Date(String strDate, String pattern) {
        return string2Date(strDate, pattern, false);
    }

    /**
     * String 转 Date
     *
     * @param strDate
     * @param pattern
     * @return
     * @Modify : Donny
     */
    public static Date string2Date(String strDate, String pattern, boolean error2null) {
        SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
        Date time = new Date();
        try {
            time = formatDate.parse(strDate);
        } catch (Exception e) {
            if (error2null) {
                time = null;
            } else {
                e.printStackTrace();
            }
        }
        return time;
    }

    /**
     * 按照偏移类型和偏移量取得偏移后的日期
     *
     * @param date   日期
     * @param type   偏移类型
     * @param offset 偏移量
     * @return 计算后的日期（yyyy-MM-dd）
     */
    public static String getOffsetDate(Date date, DateOffsetType type, String offset, boolean includeStartDay) {
        // 偏移量
        int intOffset = 0;
        try {
            intOffset = Integer.parseInt(offset);
        } catch (Throwable e) {
        }

        // 按偏移类型和偏移量加减日期
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        if (type == DateOffsetType.M) {
            gc.add(Calendar.MONTH, intOffset);
        } else {
            gc.add(Calendar.DATE, intOffset);
        }
        if (!includeStartDay) {
            gc.add(Calendar.DATE, -1);
        }

        // 计算后的日期（yyyy-MM-dd）
        return date2String(gc.getTime());
    }

    /**
     * Date转String(yyyy-MM-dd)
     *
     * @param dDate 日期对象
     * @return 日期字符串
     */
    public static String date2String(Date dDate) {
        return date2String(dDate, "yyyy-MM-dd");
    }

    /**
     * Date转String(yyyy-MM-dd HH:mm:ss)
     *
     * @param dDate 日期对象
     * @return 日期字符串
     */
    public static String time2String(Date dDate) {
        return date2String(dDate, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Date转String
     *
     * @param dDate   日期对象
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String date2String(Date dDate, String pattern) {
        if (dDate == null) {
            return "";
        }
        SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
        return formatDate.format(dDate);
    }


    /**
     * 当前日历，这里用中国时间表示
     *
     * @return 以当地时区表示的系统当前日历
     */
    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 指定毫秒数表示的日历
     *
     * @param millis 毫秒数
     * @return 指定毫秒数表示的日历
     */
    public static Calendar getCalendar(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date(millis));
        return cal;
    }

    // ////////////////////////////////////////////////////////////////////////////
    // getDate
    // 各种方式获取的Date
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 当前日期
     *
     * @return 系统当前时间
     */
    public static java.util.Date getDate() {
        return new java.util.Date();
    }

    /**
     * 指定毫秒数表示的日期
     *
     * @param millis 毫秒数
     * @return 指定毫秒数表示的日期
     */
    public static java.util.Date getDate(long millis) {
        return new java.util.Date(millis);
    }

    /**
     * 日期转换为字符串
     *
     * @param format 日期格式
     * @return 字符串
     */
    public static String getDate(String format) {
        java.util.Date date = new java.util.Date();
        if (null == date) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 时间戳转换为字符串
     *
     * @param time
     * @return
     */
    public static String timestamptoStr(Timestamp time) {
        Date date = null;
        if (null != time) {
            date = new Date(time.getTime());
        }
        return date2Str(date, date_sdf.get());
    }

    /**
     * 字符串转换时间戳
     *
     * @param str
     * @return
     */
    public static Timestamp str2Timestamp(String str) {
        Date date = str2Date(str, date_sdf.get());
        return new Timestamp(date.getTime());
    }

    /**
     * 转MM-dd
     *
     * @param str
     * @return
     */
    public static String str2StrMd(String str) {
        return MM_dd.get().format(string2Date(str));
    }

    /**
     * 转成dd
     *
     * @param str
     * @return
     */
    public static String str2Strdd(String str) {
        return dd.get().format(string2Date(str));
    }

    /**
     * yyyy-MM-dd 转为yyyy-MM
     *
     * @param str
     * @return
     */
    public static String str2yM(String str) {
        return yyyy_MM.get().format(string2Date(str));
    }

    /**
     * String转Date(yyyy-MM-dd)
     *
     * @param strDate 日期字符串
     * @return 日期对象
     */
    public static Date string2Date(String strDate) {
        return string2Date(strDate, "yyyy-MM-dd");
    }

    /**
     * String 转 Date
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return 日期对象
     */
    public static Date str2Date(String strDate, String pattern) {
        return str2Date(strDate, pattern, false);
    }

    /**
     * String 转 Date
     *
     * @param strDate
     * @param pattern
     * @return
     */
    public static Date str2Date(String strDate, String pattern, boolean error2null) {
        return str2Date(strDate, getSDFormat(pattern), true);
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @param sdf
     * @return
     */
    public static java.util.Date str2Date(String str, SimpleDateFormat sdf) {
        return str2Date(str, sdf, true);
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @param sdf
     * @param error2null
     * @return
     */
    public static java.util.Date str2Date(String str, SimpleDateFormat sdf, boolean error2null) {
        if (sdf == null || "".equals(str) || " ".equals(str)) {
            return null;
        }

        java.util.Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            if (error2null) {
                date = null;
            } else {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * 日期转换为字符串
     *
     * @param date_sdf 日期格式
     * @return 字符串
     */
    public static String date2Str(SimpleDateFormat date_sdf) {
        java.util.Date date = getDate();
        if (null == date) {
            return null;
        }
        return date_sdf.format(date);
    }

    public static String date2Str(java.util.Date date, String pattern) {
        return date2Str(date, getSDFormat(pattern));
    }

    /**
     * 日期转换为字符串
     *
     * @param date     日期
     * @param date_sdf 日期格式
     * @return 字符串
     */
    public static String date2Str(java.util.Date date, SimpleDateFormat date_sdf) {
        if (null == date || date_sdf == null) {
            return null;
        }
        return date_sdf.format(date);
    }

    /**
     * 系统当前的时间戳
     *
     * @return 系统当前的时间戳
     */
    public static Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 指定毫秒数的时间戳
     *
     * @param millis 毫秒数
     * @return 指定毫秒数的时间戳
     */
    public static Timestamp getTimestamp(long millis) {
        return new Timestamp(millis);
    }

    /**
     * 以字符形式表示的时间戳
     *
     * @param time 毫秒数
     * @return 以字符形式表示的时间戳
     */
    public static Timestamp getTimestamp(String time) {
        return new Timestamp(Long.parseLong(time));
    }

    /**
     * 指定日期的时间戳
     *
     * @param date 指定日期
     * @return 指定日期的时间戳
     */
    public static Timestamp getTimestamp(java.util.Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 指定日历的时间戳
     *
     * @param cal 指定日历
     * @return 指定日历的时间戳
     */
    public static Timestamp getTimestamp(Calendar cal) {
        return new Timestamp(cal.getTime().getTime());
    }

    // ////////////////////////////////////////////////////////////////////////////
    // getMillis
    // 各种方式获取的Millis
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 系统时间的毫秒数
     *
     * @return 系统时间的毫秒数
     */
    public static long getMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 指定日历的毫秒数
     *
     * @param cal 指定日历
     * @return 指定日历的毫秒数
     */
    public static long getMillis(Calendar cal) {
        return cal.getTime().getTime();
    }

    /**
     * 指定日期的毫秒数
     *
     * @param date 指定日期
     * @return 指定日期的毫秒数
     */
    public static long getMillis(Date date) {
        return date.getTime();
    }

    /**
     * 指定时间戳的毫秒数
     *
     * @param ts 指定时间戳
     * @return 指定时间戳的毫秒数
     */
    public static long getMillis(Timestamp ts) {
        return ts.getTime();
    }

    // ////////////////////////////////////////////////////////////////////////////
    // formatDate
    // 将日期按照一定的格式转化为字符串
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 默认方式表示的系统当前日期，具体格式：年-月-日
     *
     * @return 默认日期按“年-月-日“格式显示
     */
    public static String formatDate() {
        return date_sdf.get().format(getCalendar().getTime());
    }

    /**
     * 默认方式表示的系统当前日期，具体格式：年-月
     *
     * @return 默认日期按“年-月“格式显示
     */
    public static String formatDate2Ym() {
        return yyyy_MM.get().format(getCalendar().getTime());
    }

    /**
     * 默认方式表示的系统当前日期，具体格式：yyyy-MM-dd HH:mm:ss
     *
     * @return 默认日期按“yyyy-MM-dd HH:mm:ss“格式显示
     */
    public static String formatDateTime() {
        return datetimeFormat.get().format(getCalendar().getTime());
    }

    /**
     * 获取时间字符串
     */
    public static String getDataString(SimpleDateFormat formatstr) {
        return formatstr.format(getCalendar().getTime());
    }

    /**
     * 指定日期的默认显示，具体格式：年-月-日
     *
     * @param cal 指定的日期
     * @return 指定日期按“年-月-日“格式显示
     */
    public static String formatDate(Calendar cal) {
        return date_sdf.get().format(cal.getTime());
    }

    /**
     * 指定日期的默认显示，具体格式：年-月-日
     *
     * @param date 指定的日期
     * @return 指定日期按“年-月-日“格式显示
     */
    public static String formatDate(java.util.Date date) {
        return date_sdf.get().format(date);
    }

    /**
     * 指定毫秒数表示日期的默认显示，具体格式：年-月-日
     *
     * @param millis 指定的毫秒数
     * @return 指定毫秒数表示日期按“年-月-日“格式显示
     */
    public static String formatDate(long millis) {
        return date_sdf.get().format(new java.util.Date(millis));
    }

    /**
     * 默认日期按指定格式显示
     *
     * @param pattern 指定的格式
     * @return 默认日期按指定格式显示
     */
    public static String formatDate(String pattern) {
        return getSDFormat(pattern).format(getCalendar().getTime());
    }

    /**
     * 指定日期按指定格式显示
     *
     * @param cal     指定的日期
     * @param pattern 指定的格式
     * @return 指定日期按指定格式显示
     */
    public static String formatDate(Calendar cal, String pattern) {
        return getSDFormat(pattern).format(cal.getTime());
    }

    /**
     * 指定日期按指定格式显示
     *
     * @param date    指定的日期
     * @param pattern 指定的格式
     * @return 指定日期按指定格式显示
     */
    public static String formatDate(java.util.Date date, String pattern) {
        return getSDFormat(pattern).format(date);
    }

    // ////////////////////////////////////////////////////////////////////////////
    // formatTime
    // 将日期按照一定的格式转化为字符串
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 默认方式表示的系统当前日期，具体格式：年-月-日 时：分
     *
     * @return 默认日期按“年-月-日 时：分“格式显示
     */
    public static String formatTime() {
        return time_sdf.get().format(getCalendar().getTime());
    }

    /**
     * 指定毫秒数表示日期的默认显示，具体格式：年-月-日 时：分
     *
     * @param millis 指定的毫秒数
     * @return 指定毫秒数表示日期按“年-月-日 时：分“格式显示
     */
    public static String formatTime(long millis) {
        return time_sdf.get().format(new java.util.Date(millis));
    }

    /**
     * 指定日期的默认显示，具体格式：年-月-日 时：分
     *
     * @param cal 指定的日期
     * @return 指定日期按“年-月-日 时：分“格式显示
     */
    public static String formatTime(Calendar cal) {
        return time_sdf.get().format(cal.getTime());
    }

    /**
     * 指定日期的默认显示，具体格式：年-月-日 时：分
     *
     * @param date 指定的日期
     * @return 指定日期按“年-月-日 时：分“格式显示
     */
    public static String formatTime(java.util.Date date) {
        return time_sdf.get().format(date);
    }

    // ////////////////////////////////////////////////////////////////////////////
    // formatShortTime
    // 将日期按照一定的格式转化为字符串
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 默认方式表示的系统当前日期，具体格式：时：分
     *
     * @return 默认日期按“时：分“格式显示
     */
    public static String formatShortTime() {
        return short_time_sdf.get().format(getCalendar().getTime());
    }

    /**
     * 指定毫秒数表示日期的默认显示，具体格式：时：分
     *
     * @param millis 指定的毫秒数
     * @return 指定毫秒数表示日期按“时：分“格式显示
     */
    public static String formatShortTime(long millis) {
        return short_time_sdf.get().format(new java.util.Date(millis));
    }

    /**
     * 指定日期的默认显示，具体格式：时：分
     *
     * @param cal 指定的日期
     * @return 指定日期按“时：分“格式显示
     */
    public static String formatShortTime(Calendar cal) {
        return short_time_sdf.get().format(cal.getTime());
    }

    /**
     * 指定日期的默认显示，具体格式：时：分
     *
     * @param date 指定的日期
     * @return 指定日期按“时：分“格式显示
     */
    public static String formatShortTime(java.util.Date date) {
        return short_time_sdf.get().format(date);
    }

    // ////////////////////////////////////////////////////////////////////////////
    // parseDate
    // parseCalendar
    // parseTimestamp
    // 将字符串按照一定的格式转化为日期或时间
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
     *
     * @param src     将要转换的原始字符窜
     * @param pattern 转换的匹配格式
     * @return 如果转换成功则返回转换后的日期
     * @throws ParseException
     */
    public static java.util.Date parseDate(String src, String pattern) throws ParseException {
        return getSDFormat(pattern).parse(src);
    }

    /**
     * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
     *
     * @param src     将要转换的原始字符窜
     * @param pattern 转换的匹配格式
     * @return 如果转换成功则返回转换后的日期
     * @throws ParseException
     */
    public static Calendar parseCalendar(String src, String pattern) throws ParseException {

        java.util.Date date = parseDate(src, pattern);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static String formatAddDate(String src, String pattern, int amount) throws ParseException {
        Calendar cal;
        cal = parseCalendar(src, pattern);
        cal.add(Calendar.DATE, amount);
        return formatDate(cal);
    }

    /**
     * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
     *
     * @param src     将要转换的原始字符窜
     * @param pattern 转换的匹配格式
     * @return 如果转换成功则返回转换后的时间戳
     * @throws ParseException
     */
    public static Timestamp parseTimestamp(String src, String pattern) throws ParseException {
        java.util.Date date = parseDate(src, pattern);
        return new Timestamp(date.getTime());
    }

    // ////////////////////////////////////////////////////////////////////////////
    // dateDiff
    // 计算两个日期之间的差值
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 计算两个时间之间的差值，根据标志的不同而不同
     *
     * @param flag   计算标志，表示按照年/月/日/时/分/秒等计算
     * @param calSrc 减数
     * @param calDes 被减数
     * @return 两个日期之间的差值
     */
    public static int dateDiff(char flag, Calendar calSrc, Calendar calDes) {
        long millisDiff = getMillis(calSrc) - getMillis(calDes);

        if (flag == 'y') {
            return (calSrc.get(Calendar.YEAR) - calDes.get(Calendar.YEAR));
        }

        if (flag == 'd') {
            return (int) (millisDiff / DAY_IN_MILLIS);
        }

        if (flag == 'h') {
            return (int) (millisDiff / HOUR_IN_MILLIS);
        }

        if (flag == 'm') {
            return (int) (millisDiff / MINUTE_IN_MILLIS);
        }

        if (flag == 's') {
            return (int) (millisDiff / SECOND_IN_MILLIS);
        }

        return 0;
    }

    //解析东八区时间格式的日期
    public static Long parseBjTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+08:00");
        Date parse = null;
        try {
            parse = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse == null ? null : parse.getTime();
    }

}

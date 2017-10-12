package com.wmcd.myb.util;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 与当前日期相关的工具类
 *
 * @author DateUtil
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {
    /**
     * The constant weeks.
     */
// 用来全局控制 上一周，本周，下一周的周数变化
    private static int weeks = 0;
    /**
     * The constant MaxDate.
     */
    private static int MaxDate; // 一月最大天数
    /**
     * The constant MaxYear.
     */
    private static int MaxYear; // 一年最大天数

    /**
     * The constant DATETIME_FORMAT.
     */
    public static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * The constant DATE_FORMAT.
     */
    private static String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * The constant DATE_FORMAT_1.
     */
    private static String DATE_FORMAT_1 = "yyyyMMdd";
    /**
     * The constant TIME_FORMAT.
     */
    private static String TIME_FORMAT = "HH:mm";

    /**
     * The constant M_FORMAT.
     */
    public static String M_FORMAT = "M";
    /**
     * The constant M_FORMAT_TWO.
     */
    public static String M_FORMAT_TWO = "MM/dd";
    /**
     * The constant M_FORMAT_THREE.
     */
    public static String M_FORMAT_THREE = "HH:mm";
    /**
     * The constant TIME_ONE_HOUR.
     */
    public static final long TIME_ONE_HOUR = 3600L * 1000L;

    /**
     * The constant TIME_ONE_DAY.
     */
    public static final long TIME_ONE_DAY = 24L * TIME_ONE_HOUR;

    /**
     * The Week name.
     */
    public static String[] weekName = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    /**
     * Gets month days.
     *
     * @param year  the year
     * @param month the month
     * @return the month days
     */
    public static int getMonthDays(int year, int month) {
        if (month > 12) {
            month = 1;
            year += 1;
        } else if (month < 1) {
            month = 12;
            year -= 1;
        }
        int[] arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = 0;

        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            arr[1] = 29; // 闰年2月29天
        }

        try {
            days = arr[month - 1];
        } catch (Exception e) {
            e.getStackTrace();
        }

        return days;
    }

    /**
     * Gets year.
     *
     * @return the year
     */
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * Gets month.
     *
     * @return the month
     */
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * Gets current month day.
     *
     * @return the current month day
     */
    public static int getCurrentMonthDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Gets week day.
     *
     * @return the week day
     */
    public static int getWeekDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Gets hour.
     *
     * @return the hour
     */
    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Gets minute.
     *
     * @return the minute
     */
    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    /**
     * Get week sunday int [ ].
     *
     * @param year     the year
     * @param month    the month
     * @param day      the day
     * @param pervious the pervious
     * @return the int [ ]
     */
    public static int[] getWeekSunday(int year, int month, int day, int pervious) {
        int[] time = new int[3];
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.add(Calendar.DAY_OF_MONTH, pervious);
        time[0] = c.get(Calendar.YEAR);
        time[1] = c.get(Calendar.MONTH) + 1;
        time[2] = c.get(Calendar.DAY_OF_MONTH);
        return time;

    }

    /**
     * Gets week day from date.
     *
     * @param year  the year
     * @param month the month
     * @return the week day from date
     */
    public static int getWeekDayFromDate(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDateFromString(year, month));
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return week_index;
    }

    /**
     * Gets date from string.
     *
     * @param year  the year
     * @param month the month
     * @return the date from string
     */
    @SuppressLint("SimpleDateFormat")
    public static Date getDateFromString(int year, int month) {
        String dateString = year + "-" + (month > 9 ? month : ("0" + month)) + "-01";
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

//	public static boolean isToday(CustomDate date) {
//		return (date.year == DateUtil.getYear() && date.month == DateUtil.getMonth()
//				&& date.day == DateUtil.getCurrentMonthDay());
//	}
//
//	public static boolean isCurrentMonth(CustomDate date) {
//		return (date.year == DateUtil.getYear() && date.month == DateUtil.getMonth());
//	}

    /**
     * 获得当前时间与传入时间是时间差
     *
     * @param date the date
     * @return 描述 string
     */
    public static String timeCha(Date date) {
        SimpleDateFormat df = new SimpleDateFormat();
        // 当前时间
        Calendar instance = Calendar.getInstance();
        // 计算差距时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year_t = instance.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
        int month_t = instance.get(Calendar.MONTH) - calendar.get(Calendar.MONTH);
        int day_t = instance.get(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH);
        int hour_t = instance.get(Calendar.HOUR_OF_DAY) - calendar.get(Calendar.HOUR_OF_DAY);
        int min_t = instance.get(Calendar.MINUTE) - calendar.get(Calendar.MINUTE);
        if (month_t > 0 || year_t > 0) {
            df.applyPattern("yyyy-MM-dd");
            return df.format(date);
        }
        if (day_t > 0) {
            df.applyPattern("MM-dd");
            return df.format(date);
        }
        if (hour_t > 0) {
            df.applyPattern("HH:mm");
            return df.format(date);
        }
        if (min_t > 0) {
            df.applyPattern("HH:mm");
            return df.format(date);
        }
        return "刚刚";
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    @SuppressWarnings("static-access")
    public static void main(String[] args) {

        DateUtil tt = new DateUtil();

        System.out.println("返回某一日期是星期几:" + tt.getWeek("2009-02-01"));
        System.out.println("年份加1:" + tt.getNextYear("2009-02-01"));
        System.out.println("年份(2009-02-01)加1:" + tt.getDate("2009-02-01", "yyyy", 1));
        System.out.println("年份(2009-02-01)减1:" + tt.getDate("2009-02-01", "yyyy", -1));
        System.out.println("月份(2009-02-01)加1:" + tt.getDate("2009-02-01", "MM", 1));
        System.out.println("月份(2009-02-01)减1:" + tt.getDate("2009-02-01", "MM", -1));
        System.out.println("日期(2009-02-01)加1:" + tt.getDate("2009-02-01", "dd", 1));
        System.out.println("日期(2009-02-01)减1:" + tt.getDate("2009-02-01", "dd", -1));
        System.out.println("返回当前系统时间.....:" + tt.getDateTimeD());
        System.out.println("获取当天日期:" + tt.getNowTime("yyyy-MM-dd"));

        System.out.println("获取本周一日期:" + tt.getMondayOFWeek());
        System.out.println("获取本周日的日期~:" + tt.getCurrentWeekday());
        System.out.println("获取上周一日期:" + tt.getPreviousWeekday());
        System.out.println("获取上周日日期:" + tt.getPreviousWeekSunday());
        System.out.println("获取下周一日期:" + tt.getNextMonday());
        System.out.println("获取下周日日期:" + tt.getNextSunday());
        System.out.println("获得相应周的周六的日期:" + tt.getNowTime("yyyy-MM-dd"));
        System.out.println("获取本月第一天日期:" + tt.getFirstDayOfMonth());
        System.out.println("获取本月最后一天日期:" + tt.getDefaultDay());
        System.out.println("获取上月第一天日期:" + tt.getPreviousMonthFirst());
        System.out.println("获取上月最后一天的日期:" + tt.getPreviousMonthEnd());
        System.out.println("获取下月第一天日期:" + tt.getNextMonthFirst());
        System.out.println("获取下月最后一天日期:" + tt.getNextMonthEnd());
        System.out.println("获取本年的第一天日期:" + tt.getCurrentYearFirst());
        System.out.println("获取本年最后一天日期:" + tt.getCurrentYearEnd());
        System.out.println("获取去年的第一天日期:" + tt.getPreviousYearFirst());
        System.out.println("获取去年的最后一天日期:" + tt.getPreviousYearEnd());
        System.out.println("获取明年第一天日期:" + tt.getNextYearFirst());
        System.out.println("获取明年最后一天日期:" + tt.getNextYearEnd());
        System.out.println("获取本季度第一天到最后一天:" + tt.getThisSeasonTime(11));
        System.out.println("获取两个日期之间间隔天数2008-12-1~2008-9.29:" + DateUtil.getTwoDay("2008-12-1", "2008-9-29"));
        System.out.println("取得某天是一年中的多少周:" + tt.getWeekOfYear("2009-05-07"));
        // System.out.println("周+1= " + getWeekOrAdd("200806",1));
        // System.out.println("周-5= " + getWeekOrAdd("200903",-5));

        System.out.println("20090201 - 25个月= " + DateUtil.getDate("20090201", "MM", -25));
        // System.out.println("200902 - 2周= " + getWeekOrAdd("200902", -5));

        System.out.println("返回某周的开始日期=" + getDateByWeekFirst("200918"));
        System.out.println("返回某周的结束日期=" + getDateByWeekLast("200918"));

        System.out.println("根据某一日期返回是星期几=" + getWeek("2009-05-07"));

        System.out.println("查询某月最后一天=" + getDefaultDay("200902"));

        System.out.println("计算某月份的天数=" + getMonthDays("200902"));
        System.out.println("返回两个日期间的相差的月份=" + compareMonth("200908", "200912"));
        System.out.println("返回两个日期间的相差的周=" + getTwoWeek("2009-12-08", "2009-12-01"));

        System.out.println("当前时间" + getDateTime());
        System.out.println("当前时间" + getDateTimeD());

        System.out.println("当前日期" + getCurDate());
        // System.out.println("周运算" + DateUtil.getWeekOrAdd("200906", -(1 +
        // 1)));

        System.out.println("两周相差多少" + compareWeek("200908", "200912"));

        System.out.println("当前时间=" + DateUtil.getDateTime());
        System.out.println("两个日期之间相差的天数=" + DateUtil.compareDay("20151231", "20160430"));
        // System.out.println("日运算=" + DateUtil.getDayOrAdd("2009-09-08", 5));

        String y = DateUtil.getCurDate().replaceAll("-", "");

        String year = y.substring(2, 4);
        String month = y.substring(4, 6);

        System.out.println("两位年" + year);
        System.out.println("两位月" + month);
    }

    /**
     * Instantiates a new Date util.
     */
    private DateUtil() {

    }

    /**
     * Convert date and time to string like "yyyy-MM-dd HH:mm".
     *
     * @param d the d
     * @return the string
     */
    public static String formatDateTime(Date d) {
        return new SimpleDateFormat(DATETIME_FORMAT).format(d);
    }

    /**
     * Convert time to string like "HH:mm".
     *
     * @param d the d
     * @return the string
     */
    public static String formatTime(Date d) {
        return new SimpleDateFormat(TIME_FORMAT).format(d);
    }

    /**
     * Convert date to String like "yyyy-MM-dd".
     *
     * @param d the d
     * @return the string
     */
    public static String formatDate(Date d) {
        return new SimpleDateFormat(DATE_FORMAT).format(d);
    }

    /**
     * Convert date to String like "yyyy-MM-dd".
     *
     * @param d      the d
     * @param format the format
     * @return the string
     */
    public static String formatDate(Date d,String format) {
        return new SimpleDateFormat(format).format(d);
    }

    /**
     * Parse date like "yyyy-MM-dd".
     *
     * @param d the d
     * @return the date
     */
    public static Date parseDate(String d) {
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(d);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * Parse date like "yyyyMMdd".
     *
     * @param d the d
     * @return the date
     */
    public static Date parseDateD(String d) {
        try {
            return new SimpleDateFormat(DATE_FORMAT_1).parse(d);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * Parse date and time like "yyyy-MM-dd HH:mm:ss".
     *
     * @param dt the dt
     * @return the date
     */
    public static Date parseDateTime(String dt) {
        try {
            return new SimpleDateFormat(DATETIME_FORMAT).parse(dt);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 返回某一日期的下一年日期
     *
     * @param sdate 日期格式为yyyy-MM-dd
     * @return 下一年的日期 next year
     */
    public static String getNextYear(String sdate) {

        String str = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, Integer.parseInt(sdate.substring(0, 4)));
        c.set(Calendar.MONTH, Integer.parseInt(sdate.substring(5, 7)) - 1);
        c.set(Calendar.DATE, Integer.parseInt(sdate.substring(8, 10)));
        c.add(Calendar.YEAR, 1);

        str = sdf.format(c.getTime());
        return str;
    }

    /**
     * 返回某一计算后的日期.
     *
     * @param sdate 格式为yyyy-MM-dd或yyyyMMdd
     * @param part  需要改变的部分。yyyy-表示年，MM-表示月,dd-表示日
     * @param i     需要改变的两,可以是负数
     * @return date
     */
    public static String getDate(String sdate, String part, int i) {
        String str = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();

        sdate = sdate.replaceAll("-", "");

        c.set(Calendar.YEAR, Integer.parseInt(sdate.substring(0, 4)));
        c.set(Calendar.MONTH, Integer.parseInt(sdate.substring(4, 6)) - 1);
        c.set(Calendar.DATE, Integer.parseInt(sdate.substring(6, 8)));

        if ("yyyy".equalsIgnoreCase(part)) {
            c.add(Calendar.YEAR, i);
        } else if ("MM".equalsIgnoreCase(part)) {
            c.add(Calendar.MONTH, i);
        } else if ("dd".equalsIgnoreCase(part)) {
            c.add(Calendar.DATE, i);
        }
        str = sdf.format(c.getTime());
        return str;
    }

    /**
     * 返回两个年月之间相差多少个月
     *
     * @param year1 开始年月格式为yyyyMM
     * @param year2 结束年月格式为yyyyMM
     * @return int
     */
    public static int compareMonth(String year1, String year2) {
        int r = 0;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt(year1.replaceAll("-", "").substring(0, 4)));
        c.set(Calendar.MONTH, Integer.parseInt(year1.replaceAll("-", "").substring(4, 6)) - 1);
        c.set(Calendar.DATE, 1);

        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.YEAR, Integer.parseInt(year2.replaceAll("-", "").substring(0, 4)));
        c2.set(Calendar.MONTH, Integer.parseInt(year2.replaceAll("-", "").substring(4, 6)) - 1);
        c2.set(Calendar.DATE, 1);

        r = compare(c2, c, 2);

        return r;
    }

    /**
     * 返回两个年周之间相差多少周
     *
     * @param year1 开始年月格式为yyyyWW
     * @param year2 结束年月格式为yyyyWW
     * @return int
     */
    public static int compareWeek(String year1, String year2) {
        int r = 0;

        String startDate = DateUtil.getDateByWeekFirst(year1).replaceAll("-", "");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt(startDate.substring(0, 4)));
        c.set(Calendar.MONTH, Integer.parseInt(startDate.substring(4, 6)) - 1);
        c.set(Calendar.DATE, Integer.parseInt(startDate.substring(6, 8)));

        String endDate = DateUtil.getDateByWeekFirst(year2).replaceAll("-", "");
        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.YEAR, Integer.parseInt(endDate.substring(0, 4)));
        c2.set(Calendar.MONTH, Integer.parseInt(endDate.substring(4, 6)) - 1);
        c2.set(Calendar.DATE, Integer.parseInt(endDate.substring(6, 8)));

        r = compare(c2, c, Calendar.DATE);
        // 求出天数来，除以7就是周数

        return r / 7;
    }

    /**
     * 返回两个年月之间相差多少个日
     *
     * @param startDate 开始年月格式为yyyyMMdd或yyyy-MM-dd
     * @param endDate   结束年月格式为yyyyMMdd或yyyy-MM-dd
     * @return int
     */
    public static int compareDay(String startDate, String endDate) {
        int r = 0;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt(startDate.replaceAll("-", "").substring(0, 4)));
        c.set(Calendar.MONTH, Integer.parseInt(startDate.replaceAll("-", "").substring(4, 6)) - 1);
        c.set(Calendar.DATE, Integer.parseInt(startDate.replaceAll("-", "").substring(6, 8)));

        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.YEAR, Integer.parseInt(endDate.replaceAll("-", "").substring(0, 4)));
        c2.set(Calendar.MONTH, Integer.parseInt(endDate.replaceAll("-", "").substring(4, 6)) - 1);
        c2.set(Calendar.DATE, Integer.parseInt(endDate.replaceAll("-", "").substring(6, 8)));

        r = compare(c2, c, 5);

        return r;
    }

    /**
     * 计算他们之间相差的年数|月数|天数
     *
     * @param c1   the c 1
     * @param c2   the c 2
     * @param what the what
     * @return int
     */
    public static int compare(Calendar c1, Calendar c2, int what) {
        int number = 0;
        switch (what) {
            case Calendar.YEAR:
                number = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
                break;
            case Calendar.MONTH:
                int years = compare(c1, c2, Calendar.YEAR);
                number = 12 * years + (c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH));
                break;
            case Calendar.DATE:
                number = (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / (1000 * 60 * 60 * 24));
                break;
            default:
                number = (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / (1000 * 60 * 60 * 24));
                break;
        }
        return number;
    }

    /**
     * 月份相增加与减少
     *
     * @param yearMonth 年月，格式为yyyyMM
     * @param part      需要改变的部分。yyyy-表示年，MM-表示月
     * @param i         the
     * @return year month
     */
    public static String getYearMonth(String yearMonth, String part, int i) {
        String str = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, Integer.parseInt(yearMonth.substring(0, 4)));
        c.set(Calendar.MONTH, Integer.parseInt(yearMonth.substring(4, 6)) - 1);
        c.set(Calendar.DATE, Integer.parseInt("5"));

        if ("yyyy".equals(part)) {
            c.add(Calendar.YEAR, i);
        } else if ("MM".equals(part)) {
            c.add(Calendar.MONTH, i);
        }

        str = sdf.format(c.getTime());
        return str;
    }

    /**
     * Gets first day of week.
     *
     * @param year the year
     * @param week the week
     * @return the first day of week
     */
/*
     * 取得某年某周的第一天 对于交叉:2008-12-29到2009-01-04属于2008年的最后一周,2009-01-05为2009年第一周的第一天
     *
     * @param year @param week @return
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar calFirst = Calendar.getInstance();
        calFirst.set(year, 0, 7);
        Date firstDate = getFirstDayOfWeek(calFirst.getTime());

        Calendar firstDateCal = Calendar.getInstance();
        firstDateCal.setTime(firstDate);

        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, firstDateCal.get(Calendar.DATE));

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, (week - 1) * 7);
        firstDate = getFirstDayOfWeek(cal.getTime());

        return firstDate;
    }

    /**
     * 取得某年某周的最后一天
     * 对于交叉:2008-12-29到2009-01-04属于2008年的最后一周,2009-01-04为2008年最后一周的最后一天
     *
     * @param year the year
     * @param week the week
     * @return last day of week
     */
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar calLast = Calendar.getInstance();
        calLast.set(year, 0, 7);
        Date firstDate = getLastDayOfWeek(calLast.getTime());

        Calendar firstDateCal = Calendar.getInstance();
        firstDateCal.setTime(firstDate);

        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, firstDateCal.get(Calendar.DATE));

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, (week - 1) * 7);
        Date lastDate = getLastDayOfWeek(cal.getTime());

        return lastDate;
    }

    /**
     * 返回某年周的开始日期
     *
     * @param yearweek 格式为:yyyyWW
     * @return date by week first
     */
    public static String getDateByWeekFirst(String yearweek) {
        if (yearweek == null)
            return null;

        if (yearweek.length() != 6) {
            return null;
        }

        int year = Integer.parseInt(yearweek.substring(0, 4));
        int ww = Integer.parseInt(yearweek.substring(4, 6));

        Date d = getFirstDayOfWeek(year, ww);
        return DateUtil.formatDate(d);
    }

    /**
     * 返回某年周的接受日期
     *
     * @param yearweek 格式为:yyyyWW
     * @return date by week last
     */
    public static String getDateByWeekLast(String yearweek) {
        if (yearweek == null)
            return null;

        if (yearweek.length() != 6) {
            return null;
        }

        int year = Integer.parseInt(yearweek.substring(0, 4));
        int ww = Integer.parseInt(yearweek.substring(4, 6));
        Date d = getFirstDayOfWeek(year, ww);

        return DateUtil.getDate(DateUtil.formatDate(d), "dd", 6);
    }

    /**
     *
     * 周运算，可对周进行加或减
     *
     * @param sdate
     *            日期格式为:yyyyWW
     * @param n需要增加或减的周数
     * @return
     *
     */

    // public static String getWeekOrAdd(String sdate, int n) {
    //
    // String yyyy = sdate.substring(0, 4);
    // String ww = sdate.substring(4, 6);
    // // 返回yyyyWW所在周的星期一
    //
    // Date d = getFirstDayOfWeek(Integer.parseInt(yyyy), Integer.parseInt(ww));
    //
    // // 对周进行运算
    // Date dW = DateUtils.addWeeks(d, n);
    //
    // // System.out.println(DateUtil.formatDate(dW));
    //
    // String yearWeek = DateUtil.getWeekOfYear(DateUtil.formatDate(dW));
    //
    // return yearWeek;
    // }

    /**
     * //	 *
     * //	 * 日加或减
     * //	 *
     * //	 * @param sdate
     * //	 *            日期格式为:yyyyWWdd或yyyy-MM-dd
     * //	 * @param n需要增加或减的周数
     * //	 * @return
     * //	 *
     * //
     *
     * @param date the date
     * @param num  the num
     * @return the another date
     */
// public static String getDayOrAdd(String sdate, int n) {
    // sdate = sdate.replaceAll("-", "");
    // sdate = sdate.substring(0, 4) + "-" + sdate.substring(4, 6) + "-" +
    // sdate.substring(6, 8);
    // Date d = DateUtil.parseDate(sdate);
    // Date sd = DateUtils.addDays(d, n);
    // String date = DateUtil.formatDate(sd);
    // return date;
    // }

	/*
     * 取得某天相加(减)後的那一天 @param date @param num(可正可负) @return
	 */
    public static Date getAnotherDate(Date date, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, num);
        return c.getTime();
    }

    /**
     * /** 取得某天是一年中的多少周
     *
     * @param sdate 格式为yyyy-MM-dd
     * @return 返回的格式为yyyyWW week of year
     */
    public static String getWeekOfYear(String sdate) {
        String str = null;

        Calendar c = new GregorianCalendar();

        c.set(Calendar.YEAR, Integer.parseInt(sdate.substring(0, 4)));
        c.set(Calendar.MONTH, Integer.parseInt(sdate.substring(5, 7)) - 1);
        c.set(Calendar.DATE, Integer.parseInt(sdate.substring(8, 10)));
        c.setFirstDayOfWeek(Calendar.MONDAY);

        c.setMinimalDaysInFirstWeek(7);

        int week = c.get(Calendar.WEEK_OF_YEAR);

        int lastDay = Integer.parseInt(sdate.substring(8));

        // 最后两位数小于7，而且周数已经大于51周
        if (lastDay < 7 && week > 51) {
            // 说明本周范围是从去年开始的
            sdate = DateUtil.getDate(sdate, "yyyy", -1);
            str = sdate.substring(0, 4) + String.valueOf(week);
        } else {
            str = sdate.substring(0, 4);
            str += week < 10 ? "0" + week : String.valueOf(week);
        }

        return str;
    }

    /** */
    /**
     * 取得某天所在周的第一天
     *
     * @param date the date
     * @return first day of week
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return c.getTime();
    }

    /** */
    /**
     * 取得某天所在周的最后一天
     *
     * @param date the date
     * @return last day of week
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
        return c.getTime();
    }

    /**
     * 获取当前日期
     *
     * @return 返回当前日期, 格式为yyyy-MM-dd
     */
    public static String getCurDate() {
        String str = null;
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        str = myFormatter.format(c.getTime());
        return str;
    }

    /**
     * 获取当前日期时间
     *
     * @return 返回当前日期, 格式为yyyy-MM-dd hh:mm:ss
     */
    public static String getCurDateTime() {
        return DateUtil.getDateTime();
    }

    /**
     * 获取当前日期
     *
     * @return 返回当前日期 cur date d
     */
    public static Date getCurDateD() {
        Date d = null;
        Calendar c = Calendar.getInstance();
        d = c.getTime();
        return d;
    }

    /**
     * 得到二个日期间的间隔天数
     *
     * @param sj1 the sj 1
     * @param sj2 the sj 2
     * @return the two day
     */
    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 得到二个日期间的间隔周数
     *
     * @param sj1 the sj 1
     * @param sj2 the sj 2
     * @return the two week
     */
    public static String getTwoWeek(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (7 * 24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate the sdate
     * @return week
     */
    public static String getWeek(String sdate) {
        // 再转换为时间
        Date date = DateUtil.strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        String week = new SimpleDateFormat("EEEE").format(c.getTime());
        if ("Monday".equals(week)) {
            week = "星期一";
        }
        if ("Tuesday".equals(week)) {
            week = "星期二";
        }
        if ("Wednesday".equals(week)) {
            week = "星期三";
        }
        if ("Thursday".equals(week)) {
            week = "星期四";
        }
        if ("Friday".equals(week)) {
            week = "星期五";
        }
        if ("Saturday".equals(week)) {
            week = "星期六";
        }
        if ("Sunday".equals(week)) {
            week = "星期日";
        }
        return week;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate the str date
     * @return date
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 两个时间之间的天数
     *
     * @param date1 the date 1
     * @param date2 the date 2
     * @return days
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * Gets default day.
     *
     * @return the default day
     */
// 计算当月最后一天,返回字符串
    public static String getDefaultDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获取某年月的最后一天
     *
     * @param yearMM 年月，格式yyyy-MM或yyyyMM
     * @return yyyy -MM-dd
     */
    public static String getDefaultDay(String yearMM) {

        yearMM = yearMM.replaceAll("-", "").substring(0, 6);
        yearMM = yearMM.substring(0, 4) + "-" + yearMM.substring(4, 6) + "-01";

        String str = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, Integer.parseInt(yearMM.substring(0, 4)));
        c.set(Calendar.MONTH, Integer.parseInt(yearMM.substring(5, 7)) - 1);
        c.set(Calendar.DATE, Integer.parseInt(yearMM.substring(8, 10)));

        c.set(Calendar.DATE, 1);// 设为当前月的1号
        c.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        c.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

        str = sdf.format(c.getTime());
        return str;
    }

    /**
     * 返回某月份的天数
     *
     * @param yearMM 格式为yyyy-MM-dd或yyyy-MM或yyyyMM
     * @return month days
     */
    public static int getMonthDays(String yearMM) {

        String date = yearMM.replaceAll("-", "").substring(0, 6);
        String d = getDefaultDay(date);
        int days = Integer.parseInt(d.substring(8, 10));
        return days;
    }

    /**
     * Gets previous month first.
     *
     * @return the previous month first
     */
// 上月第一天
    public static String getPreviousMonthFirst() {
        String str = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
        // lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天

        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * Gets first day of month.
     *
     * @return the first day of month
     */
// 获取当月第一天
    public static String getFirstDayOfMonth() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * Gets current weekday.
     *
     * @return the current weekday
     */
// 获得本周星期日的日期
    public static String getCurrentWeekday() {
        weeks = 0;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * Gets now time.
     *
     * @param dateformat the dateformat
     * @return the now time
     */
// 获取当天时间
    public static String getNowTime(String dateformat) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
        String hehe = dateFormat.format(now);
        return hehe;
    }

    /**
     * Gets monday plus.
     *
     * @return the monday plus
     */
// 获得当前日期与本周日相差的天数
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    /**
     * Gets monday of week.
     *
     * @return the monday of week
     */
// 获得本周一的日期
    public static String getMondayOFWeek() {
        weeks = 0;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * Gets saturday.
     *
     * @return the saturday
     */
// 获得相应周的周六的日期
    public static String getSaturday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * Gets previous week sunday.
     *
     * @return the previous week sunday
     */
// 获得上周星期日的日期
    public static String getPreviousWeekSunday() {
        weeks = 0;
        weeks--;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * Gets previous weekday.
     *
     * @return the previous weekday
     */
// 获得上周星期一的日期
    public static String getPreviousWeekday() {
        weeks--;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * Gets next monday.
     *
     * @return the next monday
     */
// 获得下周星期一的日期
    public static String getNextMonday() {
        weeks++;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * Gets next sunday.
     *
     * @return the next sunday
     */
// 获得下周星期日的日期
    public static String getNextSunday() {

        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * Gets month plus.
     *
     * @return the month plus
     */
    @SuppressWarnings("unused")
    private static int getMonthPlus() {
        Calendar cd = Calendar.getInstance();
        int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
        cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        MaxDate = cd.get(Calendar.DATE);
        if (monthOfNumber == 1) {
            return -MaxDate;
        } else {
            return 1 - monthOfNumber;
        }
    }

    /**
     * Gets previous month end.
     *
     * @return the previous month end
     */
// 获得上月最后一天的日期
    public static String getPreviousMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, -1);// 减一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * Gets next month first.
     *
     * @return the next month first
     */
// 获得下个月第一天的日期
    public static String getNextMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 减一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * Gets next month end.
     *
     * @return the next month end
     */
// 获得下个月最后一天的日期
    public static String getNextMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 加一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * Gets next year end.
     *
     * @return the next year end
     */
// 获得明年最后一天的日期
    public static String getNextYearEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);// 加一个年
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        lastDate.roll(Calendar.DAY_OF_YEAR, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * Gets next year first.
     *
     * @return the next year first
     */
// 获得明年第一天的日期
    public static String getNextYearFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);// 加一个年
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        str = sdf.format(lastDate.getTime());
        return str;

    }

    /**
     * Gets max year.
     *
     * @return the max year
     */
// 获得本年有多少天
    public static int getMaxYear() {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        return MaxYear;
    }

    /**
     * Gets year plus.
     *
     * @return the year plus
     */
    private static int getYearPlus() {
        Calendar cd = Calendar.getInstance();
        int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
        cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        if (yearOfNumber == 1) {
            return -MaxYear;
        } else {
            return 1 - yearOfNumber;
        }
    }

    /**
     * Gets current year first.
     *
     * @return the current year first
     */
// 获得本年第一天的日期
    public static String getCurrentYearFirst() {
        int yearPlus = getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus);
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        return preYearDay;
    }

    /**
     * Gets current year end.
     *
     * @return the current year end
     */
// 获得本年最后一天的日期 *
    public static String getCurrentYearEnd() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        return years + "-12-31";
    }

    /**
     * Gets previous year first.
     *
     * @return the previous year first
     */
// 获得上年第一天的日期 *
    public static String getPreviousYearFirst() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);
        years_value--;
        return years_value + "-1-1";
    }

    /**
     * Gets previous year end.
     *
     * @return the previous year end
     */
// 获得上年最后一天的日期
    public static String getPreviousYearEnd() {
        weeks--;
        int yearPlus = getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks + (MaxYear - 1));
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        getThisSeasonTime(11);
        return preYearDay;
    }

    /**
     * Gets this season time.
     *
     * @param month the month
     * @return the this season time
     */
// 获得本季度
    public static String getThisSeasonTime(int month) {
        int array[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        int start_month = array[season - 1][0];
        int end_month = array[season - 1][2];

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);

        int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
        int end_days = getLastDayOfMonth(years_value, end_month);
        String seasonDate = years_value + "-" + start_month + "-" + start_days + ";" + years_value + "-" + end_month
                + "-" + end_days;
        return seasonDate;

    }

    /**
     * 获取某年某月的最后一天
     *
     * @param year  年
     * @param month 月
     * @return 最后一天 last day of month
     */
    private static int getLastDayOfMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 0;
    }

    /**
     * 是否闰年
     *
     * @param year 年
     * @return boolean
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * 返回系统当前日期和时间
     *
     * @return date time
     */
    public static String getDateTime() {
        String nowTime = "";
        Date dt = new Date();// 如果不需要格式,可直接用dt,dt就是当前系统时间
        DateFormat df = new SimpleDateFormat(DATETIME_FORMAT);// 设置显示格式
        nowTime = df.format(dt);
        return nowTime;
    }

    /**
     * 返回系统当前日期和时间
     *
     * @return date time d
     */
    public static Date getDateTimeD() {
        Date d = null;
        Calendar c = Calendar.getInstance();
        d = c.getTime();
        return d;
    }

    /**
     * 返回 N天,N小时,N分钟 后的日期格式
     *
     * @param dateYMDHMS the date ymdhms
     * @param format     the format
     * @param unit       the unit
     * @param amount     the amount
     * @return teset addDate("20110615 17:25:00", "yyyyMMdd HH:mm:ss", "days", 1);
     */
    public static String addDate(String dateYMDHMS, String format, String unit, int amount) {
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            calendar.setTime(sdf.parse(dateYMDHMS));
            if (unit.equals("day") || unit.equals("days")) {
                calendar.add(Calendar.DAY_OF_YEAR, amount);
            } else if (unit.equals("hour") || unit.equals("hours")) {
                calendar.add(Calendar.HOUR_OF_DAY, amount);
            } else if (unit.equals("minute") || unit.equals("minutes")) {
                calendar.add(Calendar.MINUTE, amount);
            }
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * To date and time str string.
     *
     * @param c the c
     * @return the string
     */
    public static String toDateAndTimeStr(Calendar c) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
    }

    /**
     * 返回2位年月 年 11>>A,12>>B 月 10>>A,11>>B,12>>C 此参照关系由表BarCodeYearMonth设置
     *
     * @param date
     * @return
     */
    // public static String getTwoBitBarCodeYearAndMonth() {
    // String yearmonth = "";
    //
    // ArrayList list = null;
    // Session hs = null;
    //
    // Connection conn = null;
    // try {
    // hs = HibernateUtil.getSession();
    // conn = hs.connection();
    //
    // String y = DateUtil.getCurDate().replaceAll("-", "");
    //
    // String year = y.substring(2, 4);
    // String month = y.substring(5, 6);
    // // 1.年月
    // String sqlQuery = " select dbo.fn_GetTwoBitBarCodeYearAndMonth('" + year
    // + "','" + month + "' ) as ym";
    // System.out.println(sqlQuery);
    // // sqlQuery = " select
    // // dbo.fn_GetTwoBitBarCodeYearAndMonth('2011','06' ) as ym";
    // System.out.println(sqlQuery);
    //
    // DataOperation op = new DataOperation();
    // op.setCon(hs.connection());
    // List barcodelist = op.getSPList(sqlQuery);
    // if (null != barcodelist && !barcodelist.isEmpty()) {
    // Map map = (Map) barcodelist.get(0);
    // String Twobitbarcodeyearandmonth = (String) map.get("ym");
    // yearmonth = Twobitbarcodeyearandmonth;
    // }
    // } catch (HibernateException hex) {
    // DebugUtil.print(hex, "HibernateUtil：Hibernate Session 打开出错！");
    // } catch (SQLException e) {
    // DebugUtil.print(e, "HibernateUtil：ResultSet 打开出错！");
    // } finally {
    // try {
    // hs.close();
    // } catch (HibernateException hex) {
    // DebugUtil.print(hex, "HibernateUtil：Hibernate Session 关闭出错！");
    // }
    // }
    //
    // return yearmonth;
    // }


    /**
     * 通过日期得到某月,日，时间
     *
     * @param str    the str
     * @param format the format
     * @return the str by date
     */
    public static String getStrByDate(String str, String format) {
        Date dateFm =null;
        try {
            dateFm = new SimpleDateFormat(DATETIME_FORMAT).parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SimpleDateFormat sdf0 = new SimpleDateFormat(format);
        return sdf0.format(dateFm);
    }

    /**
     * 得到当前日期
     *
     * @param format the format
     * @return the current date
     */
    public static String getCurrentDate(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);// 设置日期格式
        return df.format(new Date());
    }

    /**
     * 通过str得到当前日期
     *
     * @param str    the str
     * @param format the format
     * @return the month
     */
    public static String getMonth(String str,String format) {
        int month = Integer.parseInt(getStrByDate(str, format));
        int currentMonut = Integer.parseInt(getStrByDate(getCurrentDate(DATETIME_FORMAT),format));
        if (month == currentMonut)
            return "本月";
        switch (month) {
            case 1:
                return "一月";
            case 2:
                return "二月";
            case 3:
                return "三月";
            case 4:
                return "四月";
            case 5:
                return "五月";
            case 6:
                return "六月";
            case 7:
                return "七月";
            case 8:
                return "八月";
            case 9:
                return "九月";
            case 10:
                return "十月";
            case 11:
                return "十一月";
            case 12:
                return "十二月";
        }
        return "";
    }

    /**
     * 通过str得到星期
     *
     * @param str    the str
     * @param format the format
     * @return the week of date
     */
    public static String getWeekOfDate(String str,String format) {
        SimpleDateFormat dateFm = new SimpleDateFormat(format);// 定义日期格式
        Date date = null;
        try {
            date = dateFm.parse(str);// 将字符串转换为日期
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        dateFm.format(date);
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}

// the ends

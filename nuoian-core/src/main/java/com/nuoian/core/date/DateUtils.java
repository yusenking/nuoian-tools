package com.nuoian.core.date;

import com.alibaba.fastjson2.JSONObject;
import com.nuoian.core.constants.GlobalConstants;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @Author: 吴宇森
 * @Date: 2022/4/2 15:31
 * @Description: 时间工具类
 * @Package: com.nuoian.core.date
 * @Version: 1.0
 */
@Slf4j
public class DateUtils {

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String TIME_FORMAT_HHMM = "HH:mm";
    public static final String TIME_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String TIME_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String DATE_YEAR = "yyyy";
    public static final String MMDDYYYY_HHMM = "yyyy-MM-dd HH:mm";
    public static final String MMDDYYYY_HH = "yyyy-MM-dd HH";
    public static final String MMYYYY = "yyyyMM";
    public static final String MM_YYYY = "yyyy-MM";
    public static final String MMDD = "MM-dd";
    public static final String MMDDYYYY_HHMM2 = "yyyyMMdd_HH:mm";
    public static final String RETURN_FORMAT = "yyyy年MM月dd日";

    public static DateFormat getDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * 功能描述:
     * 〈格式化日期时间 yyyy-MM-dd HH:mm:ss〉
     * @param timestamp 时间戳
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 13:48
     */
    public static String formatDateTime(long timestamp) {
        return getDateFormat(DATETIME_FORMAT).format(new Date(timestamp));
    }

    /**
     * 功能描述:
     * 〈获取当前日期时间文本〉
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 13:48
     */
    public static String getDateTimeText() {
        return getDateFormat(DATETIME_FORMAT).format(getNowDateTime());
    }

    /**
     * 功能描述:
     * 〈根据格式转换时间戳〉
     * @param timestamp 时间戳
     * @param format 格式
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 13:52
     */
    public static String formatDate(long timestamp,String format){
        return getDateFormat(format).format(new Date(timestamp));
    }

    /**
     * 格式化日期
     */
    public static String formatDate(long timestamp) {
        return getDateFormat(DATE_FORMAT).format(new Date(timestamp));
    }

    /**
     * 格式化日期与时间
     */
    public static String formatDatetime(long timestamp) {
        return getDateFormat(DATETIME_FORMAT).format(new Date(timestamp));
    }

    /**
     * 功能描述:
     * 〈字符串时间转指定格式〉
     * @param dateStr 字符串时间
     * @param format 格式
     * @return: java.lang.String
     * @author: 吴宇森
     * @date: 2022/2/16 13:54
     */
    public static String formatString(String dateStr,String format) {
        try {
            Date date = new SimpleDateFormat(format).parse(dateStr);
            return new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            log.error("字符串转换指定格式日期失败",e);
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈将时间字符串转换为对应的Date格式〉
     * @param dateStr 时间字符串
     * @param format 格式
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 13:57
     */
    public static Date parseDate(String dateStr,String format) {
        try {
            return getDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            log.error("解析日期字符串出错"+format+" "+e.getMessage(),e);
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈将时间字符串转换为对应的Date格式(yyyy-MM-dd)〉
     * @param str 时间字符串
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 13:57
     */
    public static Date parseDate(String str) {
        Date date = null;
        try {
            date = getDateFormat(DATE_FORMAT).parse(str);
        } catch (ParseException e) {
            log.warn("解析日期字符串出错！格式：yyyy-MM-dd - {}", e.getMessage(), e);
        }
        return date;
    }

    /**
     * 功能描述:
     * 〈获取当前日期时间 yyyy-MM-dd〉
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 13:48
     */
    public static String getCurrentDate() {
        return getDateFormat(DATE_FORMAT).format(new Date());
    }

    /**
     * 功能描述:
     * 〈获取当前日期时间 yyyy-MM-dd HH:mm:ss〉
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 13:48
     */
    public static Date getNowDateTime() {
        SimpleDateFormat bjSdf = new SimpleDateFormat(DATETIME_FORMAT);
        bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String dt = bjSdf.format(new Date());
        try {
            return bjSdf.parse(dt);
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * 功能描述:
     * 〈获取当前时间 HH:mm〉
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 13:48
     */
    public static String getCurrentTimeByHm() {
        return getDateFormat(TIME_FORMAT_HHMM).format(new Date());
    }

    /**
     * 功能描述:
     * 〈获取当前时间 HH:mm:ss〉
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 13:48
     */
    public static String getCurrentTime() {
        return getDateFormat(TIME_FORMAT).format(new Date());
    }

    /**
     * 功能描述:
     * 〈获取指定日期 yyyyMM〉
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 13:48
     */
    public static String getDatetimeByYm(Date date) {
        return getDateFormat(MMYYYY).format(date);
    }

    /**
     * 功能描述:
     * 〈获取指定日期 yyyy-MM〉
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 13:48
     */
    public static String getDatetimeByYM(Date date) {
        return getDateFormat(MM_YYYY).format(date);
    }

    /**
     * 功能描述:
     * 〈获取指定日期 MM-dd〉
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 13:48
     */
    public static String getDatetimeByMd(Date date) {
        return getDateFormat(MMDD).format(date);
    }

    /**
     * 功能描述:
     * 〈日期减去N秒〉
     * @param date 日期
     * @param second 秒
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 14:00
     */
    public static Date getDateSubtractSecond(Date date, int second) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.SECOND, now.get(Calendar.SECOND) - second);
        return now.getTime();
    }

    /**
     * 功能描述:
     * 〈日期减去N分钟〉
     * @param date 日期
     * @param minute 分钟
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 14:00
     */
    public static Date getDateSubtractMinute(Date date, int minute) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) - minute);
        return now.getTime();
    }

    /**
     * 功能描述:
     * 〈日期减去N天〉
     * @param date 日期
     * @param day 天
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 14:01
     */
    public static Date getDateSubtractDay(Date date, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 功能描述:
     * 〈日期减去N月〉
     * @param date 日期
     * @param month 月
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 14:05
     */
    public static Date getDateSubtractMonth(Date date, int month) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) - month);
        return now.getTime();
    }

    /**
     * 功能描述:
     * 〈日期减去N年〉
     * @param date 日期
     * @param year 年
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 14:05
     */
    public static Date getDateSubtractYear(Date date, int year) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.YEAR, now.get(Calendar.YEAR) - year);
        return now.getTime();
    }


    /**
     * 功能描述:
     * 〈日期加N秒〉
     * @param date 日期
     * @param second 秒
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 14:00
     */
    public static Date getDateAddingSecond(Date date, int second) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.SECOND, now.get(Calendar.SECOND) + second);
        return now.getTime();
    }

    /**
     * 功能描述:
     * 〈日期加N分钟〉
     * @param date 日期
     * @param minute 分钟
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 14:00
     */
    public static Date getDateAddingMinute(Date date, int minute) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + minute);
        return now.getTime();
    }

    /**
     * 功能描述:
     * 〈日期加N小时〉
     * @param date 日期
     * @param hour 小时
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 14:00
     */
    public static Date getDateAddingHour(Date date, int hour) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR, now.get(Calendar.HOUR) + hour);
        return now.getTime();
    }

    /**
     * 功能描述:
     * 〈日期加N天〉
     * @param date 日期
     * @param day 天
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 14:01
     */
    public static Date getDateAddingDay(Date date, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 功能描述:
     * 〈日期加N月〉
     * @param date 日期
     * @param month 月
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 14:05
     */
    public static Date getDateAddingMonth(Date date, int month) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) + month);
        return now.getTime();
    }

    /**
     * 功能描述:
     * 〈日期加N年〉
     * @param date 日期
     * @param year 年
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 14:05
     */
    public static Date getDateAddingYear(Date date, int year) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.YEAR, now.get(Calendar.YEAR) + year);
        return now.getTime();
    }

    /**
     * 功能描述:
     * 〈获取日期中的年份〉
     * @param date 日期
     * @return: java.lang.Integer
     * @author: 吴宇森
     * @date: 2022/2/16 14:19
     */
    public static Integer getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 功能描述:
     * 〈获取日期中的月份〉
     * @param date 日期
     * @return: java.lang.Integer
     * @author: 吴宇森
     * @date: 2022/2/16 14:20
     */
    public static Integer getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述:
     * 〈获取日期的天〉
     * @param date 日期
     * @return: java.lang.Integer
     * @author: 吴宇森
     * @date: 2022/2/16 14:20
     */
    public static Integer getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 功能描述:
     * 〈获取日期中第几周〉
     * @param date 日期
     * @return: java.lang.Integer
     * @author: 吴宇森
     * @date: 2022/2/16 14:20
     */
    public static Integer getWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 功能描述:
     * 〈获取日期中的小时〉
     * @param date 日期
     * @return: java.lang.Integer
     * @author: 吴宇森
     * @date: 2022/2/16 14:21
     */
    public static Integer getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述:
     * 〈获取指定时间年的第一天〉
     * @param date 日期
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 14:23
     */
    public static Date getFirstDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    /**
     * 功能描述:
     * 〈获取指定时间年的最后一天〉
     * @param date 日期
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 14:23
     */
    public static Date getLastDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        return calendar.getTime();
    }

    /**
     * 功能描述:
     * 〈获取指定时间月的第一天〉
     * @param date 日期
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 14:23
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 功能描述:
     * 〈获取指定时间月的最后一天〉
     * @param date 日期
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 14:23
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 功能描述:
     * 〈获取日期周第一天〉
     * @param date 日期
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 14:24
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //将每周第一天设为星期一，默认是星期天
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }


    /**
     * 功能描述:
     * 〈获取日期周最后一天〉
     * @param date 1
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 14:24
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //将每周第一天设为星期一，默认是星期天
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return cal.getTime();
    }


    /**
     * 功能描述:
     * 〈获取指定时间的那天 0:0:0 的时间〉
     * @param date 日期
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 14:26
     */
    public static Date getDayBegin(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, GlobalConstants.NUMBER_0);
        c.set(Calendar.MINUTE, GlobalConstants.NUMBER_0);
        c.set(Calendar.SECOND, GlobalConstants.NUMBER_0);
        c.set(Calendar.MILLISECOND, GlobalConstants.NUMBER_0);
        return c.getTime();
    }

    /**
     * 功能描述:
     * 〈获取指定时间的那天 23:59:59.999 的时间〉
     * @param date 日期
     * @return: java.util.Date
     * @author: 吴宇森
     * @date: 2022/2/16 14:26
     */
    public static Date getDayEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, GlobalConstants.NUMBER_23);
        c.set(Calendar.MINUTE, GlobalConstants.NUMBER_59);
        c.set(Calendar.SECOND, GlobalConstants.NUMBER_59);
        c.set(Calendar.MILLISECOND, GlobalConstants.NUMBER_999);
        return c.getTime();
    }

    /**
     * 功能描述:
     * 〈判断日期是否为今天〉
     * @param date 日期
     * @return: 是：true 不是：false
     * @author: 吴宇森
     * @date: 2022/2/16 14:29
     */
    public static boolean isToday(Date date) {
        long timestamp=date.getTime();
        //Calendar使用单例，多次调用不重复创建对象
        Calendar calendar = Calendar.getInstance();
        //使用System获取当前时间
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, GlobalConstants.NUMBER_0);
        calendar.set(Calendar.MINUTE, GlobalConstants.NUMBER_0);
        calendar.set(Calendar.SECOND, GlobalConstants.NUMBER_0);
        calendar.set(Calendar.MILLISECOND, GlobalConstants.NUMBER_0);
        long today = calendar.getTimeInMillis();
        if (timestamp - today < GlobalConstants.HOUR_MS * GlobalConstants.NUMBER_24 && timestamp - today > GlobalConstants.NUMBER_0) {
            return true;
        }
        return false;
    }

    /**
     * 功能描述:
     * 〈获取两个时间的时间差〉
     * @param endDate 开始时间
     * @param nowDate 结束时间
     * @return: java.util.Map<java.lang.String,java.lang.Long>
     * @author: 吴宇森
     * @date: 2022/2/16 14:42
     */
    public static JSONObject getTimeDifference(Date endDate, Date nowDate) {
        JSONObject result = new JSONObject();
        long nd = GlobalConstants.NUMBER_1000.longValue() * GlobalConstants.NUMBER_24.longValue() * GlobalConstants.NUMBER_60.longValue() * GlobalConstants.NUMBER_60.longValue();
        long nh = GlobalConstants.NUMBER_1000 * GlobalConstants.NUMBER_60.longValue() * GlobalConstants.NUMBER_60.longValue();
        long nm = GlobalConstants.NUMBER_1000 * GlobalConstants.NUMBER_60.longValue();
        long ns = GlobalConstants.NUMBER_1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒
        long sec = diff % nd % nh % nm / ns;
        result.put("day", day);
        result.put("hour", hour);
        result.put("min", min);
        result.put("sec", sec);
        return result;
    }

    /**
     * 功能描述:
     * 〈获取日期年的每个月〉
     * @param date 日期
     * @param format 时间格式
     * @return: java.util.List<java.util.Date>
     * @author: 吴宇森
     * @date: 2022/2/16 14:56
     */
    public static List<Date> getYearEachMonth(Date date, String format){
        String year=formatDate(date.getTime(),DATE_YEAR);
        List<Date> datas=new ArrayList<>();
        for (int i = GlobalConstants.NUMBER_1; i <GlobalConstants.NUMBER_13 ; i++) {
            String m=i<GlobalConstants.NUMBER_10?"0"+i:i+"";
            String data=year+"-"+m+"-"+formatDate(date.getTime(),"dd "+TIME_FORMAT);
            datas.add(parseDate(data,format));
        }
        return datas;
    }

    /**
     * 功能描述:
     * 〈获取日期月的每一天〉
     * @param date 日期
     * @param format 格式
     * @return: java.util.List<java.util.Date>
     * @author: 吴宇森
     * @date: 2022/2/16 14:57
     */
    public static List<Date> getMonthEachDay(Date date,String format){
        Date lastDay=getLastDayOfMonth(date);
        int dayI=Integer.parseInt(formatDate(lastDay.getTime(),"dd"));
        int monthI=Integer.parseInt(formatDate(lastDay.getTime(),"MM"));
        String m=monthI<GlobalConstants.NUMBER_10?"0"+monthI:monthI+"";
        List<Date> datas=new ArrayList<>();
        for (int integer = GlobalConstants.NUMBER_1; integer < (dayI+GlobalConstants.NUMBER_1); integer++) {
            String d=integer<GlobalConstants.NUMBER_10?"0"+integer:integer+"";
            String data=formatDate(date.getTime(),DATE_YEAR)+"-"+m+"-"+d+" "+formatDate(date.getTime(),TIME_FORMAT);
            datas.add(parseDate(data,format));
        }
        return datas;
    }

    /**
     * 功能描述:
     * 〈获取两个时间之间的区间日期〉
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @return: java.util.List<java.lang.String>
     * @author: 吴宇森
     * @date: 2021/11/5 11:17
     */
    public static List<String> getBetweenMonthForDay(String beginDate,String endDate,String formatStr){
        return getBetweenDateTime(beginDate,endDate,formatStr,Calendar.DATE);
    }


    public static List<String> getBetweenYearForMonth(String beginDate,String endDate,String formatStr){
        return getBetweenDateTime(beginDate,endDate,formatStr,Calendar.MONTH);
    }

    /**
     * 功能描述:
     * 〈获取两个时间之间的区间日期〉
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @return: java.util.List<java.lang.String>
     * @author: 吴宇森
     * @date: 2021/11/5 11:17
     */
    private static List<String> getBetweenDateTime(String beginDate,String endDate,String formatStr,int calendar){
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        List<String> betweenList = new ArrayList<String>();

        try{
            Calendar startDay = Calendar.getInstance();
            startDay.setTime(format.parse(beginDate));
            startDay.add(calendar, GlobalConstants.LOSE_NUMBER_1);
            String oldEnd=formatString(endDate,formatStr);
            boolean flag=true;
            while(flag){
                startDay.add(calendar, GlobalConstants.NUMBER_1);
                String newEnd=format.format(startDay.getTime());
                betweenList.add(newEnd);
                flag=!newEnd.equals(oldEnd);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return betweenList;
    }

    /**
     * 功能描述:
     * 〈指定时间字符串和当前时间比较〉
     *
     * @param validTime 指定时间 yyyy-MM-dd HH:mm:ss
     * @return: boolean true: 大于 false：小于
     * @author: LeiZiLin
     * @date: 2021/4/14 15:02
     */
    public static boolean compareNowTime(String validTime) {
        Date date1 = parseDatetime(validTime);
        Date nowDate = new Date();
        return date1.getTime() > nowDate.getTime();
    }

    /**
     * 解析日期与时间
     */
    public static Date parseDatetime(String str) {
        Date date = null;
        try {
            date = getDateFormat(DATETIME_FORMAT).parse(str);
        } catch (ParseException e) {
            log.warn("解析日期字符串出错！格式：yyyy-MM-dd HH:mm:ss - {}", e.getMessage(), e);
        }
        return date;
    }

    /**
     * 功能描述:
     * 〈获取当前时间的指定时间格式〉
     *
     * @param str 1
     * @return: java.lang.String
     */
    public static String getAppointTimeFormat(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        return sdf.format(new Date());
    }

    /**
     * 格式化指定日期(格式：YMD_H:M)
     */
    public static String sdfYMDHMTimeByDate2(Date date) {
        return getDateFormat(MMDDYYYY_HHMM2).format(date);
    }

    /**
     * 在指定的时间上减几分钟
     *
     * @param data
     * @param minute
     * @return
     */
    public static Date reduceMinute(Date data, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.MINUTE, -minute);
        return calendar.getTime();
    }

    /**
     * 功能描述 :
     * 〈获取指定格式时间的前多少天〉
     *
     * @param format 格式
     * @param date   时间
     * @param day    多少天
     * @return : java.util.List<java.lang.String>
     * @author : 吴宇森
     * @date : 2021/04/15 14:57
     */
    public static List<String> getFrontNumberDates(String format, Date date, int day) {
        List<String> allDate = new ArrayList<>();
        for (int i = 1; i < (day + 1); i++) {
            Date d1 = getDateBefore(date, (day - i));
            String time = formatDate(d1.getTime(), format);
            allDate.add(time);
        }
        return allDate;
    }

    /**
     * 获取日期前几天的日期
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    public static Date addMonthDate(Date d, int month) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.add(Calendar.MONTH, month);
        return now.getTime();
    }

    /**
     * 格式化指定日期(格式：Y-M-D H:M)
     */
    public static String sdfYMDHMTimeByDate(Date date) {
        return getDateFormat(MMDDYYYY_HHMM).format(date);
    }

    /**
     * 获取指定日期与时间
     */
    public static String getDatetimeByYMDHM(Date date) {
        return getDateFormat(MMDDYYYY_HHMM).format(date);
    }

    /**
     * 功能描述:
     * 〈获取当前时间之前的月区间〉
     *
     * @param num 区间数
     * @return: java.util.List<java.lang.String>
     * @author: LeiZiLin
     * @date: 2021/4/15 14:27
     */
    public static List<String> getAgoMonthSection(int num) {
        List<String> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        for (int i = num; i > 0; i--) {
            list.add(sdf.format(dateRoll(new Date(), Calendar.MONTH, -i)));
        }
        // 当前时间
        list.add(sdf.format(new Date()));
        return list;
    }

    public static Date dateRoll(Date date, int i, int d) {
        // 获取Calendar对象并以传进来的时间为准
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 将现在的时间滚动固定时长,转换为Date类型赋值
        calendar.add(i, d);
        // 转换为Date类型再赋值
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取 +30分钟 当前日期与时间 格式 yyyyMMddHHmmss
     *
     * @return
     */
    public static String getAddTimeCurrentDatetime() {
        return getDateFormat(TIME_FORMAT_YYYYMMDDHHMMSS).format(addMinute(new Date(), 30));
    }

    /**
     * 在指定的时间上加多少分钟
     *
     * @param data
     * @param minute
     * @return
     */
    public static Date addMinute(Date data, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 获取前几月的日期
     *
     * @param d
     * @param m
     * @return
     */
    public static Date getMonthBefore(Date d, int m) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.add(Calendar.MONTH, m);
        return now.getTime();
    }

    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能描述:
     * 〈获取当前时间之前的小时区间(时间格式为自定义)〉
     *
     * @param num 区间数
     * @param timeFormat 区间数
     * @param hourInterval 小时间隔
     * @return: java.util.List<java.lang.String>
     * @author: LeiZiLin
     * @date: 2021/4/15 14:27
     */
    public static List<String> getAgoHourSection(int num, String timeFormat, int hourInterval) {
        List<String> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        for (int i = num; i >= hourInterval; i -= hourInterval) {
            list.add(sdf.format(dateRoll(new Date(), Calendar.HOUR, -i)));
        }
        return list;
    }

    public static Date getDateAfterHour(Date d, int hour) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.HOUR, now.get(Calendar.HOUR) + hour);
        return now.getTime();
    }

    /**
     * 功能描述:
     * 〈根据指定格式获取时间字符串〉
     *
     * @param format 1
     * @return: java.lang.String
     * @author: LeiZiLin
     * @date: 2020/11/24 15:40
     */
    public static String getNowTimeByFormat(String format) {
        return getDateFormat(format).format(new Date());
    }

    public static String getGapTime(long time){
        long hours = time / (1000 * 60 * 60);
        long minutes = (time-hours*(1000 * 60 * 60 ))/(1000* 60);
        long second = (time-hours*(1000 * 60 * 60 )-minutes*(1000 * 60 ))/1000;
        String diffTime="";
        if(minutes<10){
            diffTime=hours+":0"+minutes;
        }else{
            diffTime=hours+":"+minutes;
        }
        if(second<10){
            diffTime=diffTime+":0"+second;
        }else{
            diffTime=diffTime+":"+second;
        }
        return diffTime;
    }

    /**
     * 功能描述:
     * 〈获取年月日信息合集〉
     * @param type 1：日 2：月 3：年
     * @return: java.util.List<java.lang.String>
     * @author: 吴宇森
     * @date: 2021/11/2 15:43
     */
    public static List<String> getDateTimeInfo(int type,Date date) {
        List<String> datas = new ArrayList<>();
        //日
        if (type == 1) {
            List<String> times = new ArrayList<>();
            for (int i = 0; i < 25; i++) {
                String time=i<10?"0"+i+":00":i+":00";
                times.add(time);
            }
            for (int i = 0; i < times.size(); i++) {
                if(i%2==0){
                    datas.add(times.get(i));
                }
            }
        }
        //月
        else if (type == 2) {
            datas.addAll(getAgoDaySectionYd(15));
        }
        //年
        else {
            datas.addAll(getYearEachMonth(date));
        }
        return datas;
    }

    /**
     * 功能描述:
     * 〈获取当前时间之前的天区间〉
     *
     * @param num 区间数
     * @return: java.util.List<java.lang.String>
     * @author: LeiZiLin
     * @date: 2021/4/15 14:27
     * @return : 月日
     */
    public static List<String> getAgoDaySectionYd(int num) {
        List<String> list = new ArrayList<>();
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd");
        for (int i = num; i > 0; i--) {
            LocalDateTime now = LocalDateTime.now();
            now = now.minus(i, ChronoUnit.DAYS);
            list.add(sdf.format(now));
        }
        // 当前时间
        list.add(sdf2.format(new Date()));
        return list;
    }

    public static List<String> getYearEachMonth(Date date){
        String year=formatDate(date.getTime(),DATE_YEAR);
        List<String> datas=new ArrayList<>();
        for (int i = 1; i <13 ; i++) {
            String m=i<10?"0"+i:i+"";
            String data=year+"-"+m;
            datas.add(data);
        }
        return datas;
    }

    /**
     * 功能描述:
     * 〈格式化时间〉
     * @param date 时间
     * @return: java.lang.String
     * @author: LeiZiLin
     * @date: 2022/11/18 18:21
     */
    public static String formatTime(String date){
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
        return sdf.format(date);
    }

    /**
     * 获取日期后几天的日期
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 获取指定日期与时间
     */
    public static String getDatetimeByYMDH(Date date) {
        return getDateFormat(MMDDYYYY_HH).format(date);
    }

    /**
     * 获取当前日期与时间 格式 yyyyMMddHHmmss
     *
     * @return
     */
    public static String getCurrentDateTime() {
        return getDateFormat(TIME_FORMAT_YYYYMMDDHHMMSS).format(new Date());
    }

    public static List<JSONObject> getNowDayTime() {
        String nowDate = getAppointTimeFormat(DATE_FORMAT);
        Date d1 = getDateBefore(new Date(), 1);
        String oldDate = formatDate(d1.getTime(), DATE_FORMAT);
        List<String> times = get24Hours();
        int is = 0;
        List<JSONObject> los = new ArrayList<>();
        for (int i = 0; i < (times.size() / 2); i++) {
            JSONObject obj = new JSONObject();
            is += 2;
            String a = times.get(is - 2);
            String b = times.get(is - 1);
            obj.put("nowBegin", nowDate + " " + a);
            obj.put("nowEnd", nowDate + " " + b);
            obj.put("oldBegin", oldDate + " " + a);
            obj.put("oldEnd", oldDate + " " + b);
            los.add(obj);
        }
        return los;
    }

    public static List<JSONObject> getDayTimeSection(String dateTime){
        List<JSONObject> res = new ArrayList<>();
        String dt=formatString(dateTime,DATE_FORMAT);
        List<String> times = get24Hours();
        int is = 0;
        for (int i = 0; i < (times.size() / 2); i++) {
            is += 2;
            JSONObject obj = new JSONObject();
            String a = times.get(is - 2);
            String b = times.get(is - 1);
            obj.put("nowBegin", dt + " " + a);
            obj.put("nowEnd", dt + " " + b);
            res.add(obj);
        }
        return res;
    }

    private static List<String> get24Hours(){
        List<String> times = new ArrayList<>();
        for (int i = 0; i < (24); i++) {
            String a = String.valueOf(i);
            if (a.length() == 1) {
                a = "0" + a;
            }
            if (i % 2 == 0) {
                a = a + ":00:00";
            } else {
                a = a + ":59:59";
            }
            times.add(a);
        }
        return times;
    }
}

package com.nuoian.tools.date;

import com.alibaba.fastjson.JSONObject;
import com.nuoian.tools.constants.Constants;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: 吴宇森
 * @Date: 2022/2/16 13:45
 * @Description: 时间工具
 * @Package: com.nuoian.tools.date
 * @Version: 1.0
 */
@Slf4j
public class DateUtil {

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
            return null;
        }
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
        c.set(Calendar.HOUR_OF_DAY, Constants.NUMBER_0);
        c.set(Calendar.MINUTE, Constants.NUMBER_0);
        c.set(Calendar.SECOND, Constants.NUMBER_0);
        c.set(Calendar.MILLISECOND, Constants.NUMBER_0);
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
        c.set(Calendar.HOUR_OF_DAY, Constants.NUMBER_23);
        c.set(Calendar.MINUTE, Constants.NUMBER_59);
        c.set(Calendar.SECOND, Constants.NUMBER_59);
        c.set(Calendar.MILLISECOND, Constants.NUMBER_999);
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
        calendar.set(Calendar.HOUR_OF_DAY, Constants.NUMBER_0);
        calendar.set(Calendar.MINUTE, Constants.NUMBER_0);
        calendar.set(Calendar.SECOND, Constants.NUMBER_0);
        calendar.set(Calendar.MILLISECOND, Constants.NUMBER_0);
        long today = calendar.getTimeInMillis();
        if (timestamp - today < Constants.HOUR_MS * Constants.NUMBER_24 && timestamp - today > Constants.NUMBER_0) {
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
        long nd = Constants.NUMBER_1000.longValue() * Constants.NUMBER_24.longValue() * Constants.NUMBER_60.longValue() * Constants.NUMBER_60.longValue();
        long nh = Constants.NUMBER_1000 * Constants.NUMBER_60.longValue() * Constants.NUMBER_60.longValue();
        long nm = Constants.NUMBER_1000 * Constants.NUMBER_60.longValue();
        long ns = Constants.NUMBER_1000;
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
    public static List<Date> getYearEachMonth(Date date,String format){
        String year=formatDate(date.getTime(),DATE_YEAR);
        List<Date> datas=new ArrayList<>();
        for (int i = Constants.NUMBER_1; i <Constants.NUMBER_13 ; i++) {
            String m=i<Constants.NUMBER_10?"0"+i:i+"";
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
        int dayI=Integer.parseInt(DateUtil.formatDate(lastDay.getTime(),"dd"));
        int monthI=Integer.parseInt(DateUtil.formatDate(lastDay.getTime(),"MM"));
        String m=monthI<Constants.NUMBER_10?"0"+monthI:monthI+"";
        List<Date> datas=new ArrayList<>();
        for (int integer = Constants.NUMBER_1; integer < (dayI+Constants.NUMBER_1); integer++) {
            String d=integer<Constants.NUMBER_10?"0"+integer:integer+"";
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
    public static List<String> getBetweenDate(String beginDate,String endDate,String formatStr){
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        List<String> betweenList = new ArrayList<String>();

        try{
            Calendar startDay = Calendar.getInstance();
            startDay.setTime(format.parse(beginDate));
            startDay.add(Calendar.DATE, Constants.LOSE_NUMBER_1);

            while(true){
                startDay.add(Calendar.DATE, Constants.NUMBER_1);
                Date newDate = startDay.getTime();
                String newend=format.format(newDate);
                betweenList.add(newend);
                if(endDate.equals(newend)){
                    break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return betweenList;
    }

}

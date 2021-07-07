package com.pkg.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

    public static final String CT_M = "yyyy-MM";
    public static final String CT_D = "yyyy-MM-dd";
    public static final String CT_H = "yyyy-MM-dd HH";
    public static final String CT_MM = "yyyy-MM-dd HH:mm";
    public static final String CT_S = "yyyy-MM-dd HH:mm:ss";
    public static final String CT_SS = "HH:mm:ss";
    public static final String CP_M = "yyyyMM";
    public static final String CP_D = "yyyyMMdd";
    public static final String CP_H = "yyyyMMddHH";
    public static final String CP_MM = "yyyyMMddHHmm";
    public static final String CP_S = "yyyyMMddHHmmss";
    public static final String CP_SS = "HHmmss";
    public static final String CN_M = "yyyy年MM月";
    public static final String CN_D = "yyyy年MM月dd日";
    public static final String CN_H = "yyyy年MM月dd日 HH时";
    public static final String CN_MM = "yyyy年MM月dd日 HH时mm分";
    public static final String CN_S = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String CN_SS = "HH时mm分ss秒";

    public static String formatDateStr(Date date, String formatStr) {
        if(null==formatStr){
            formatStr = "";
        }
        DateFormat dateformat = new SimpleDateFormat(formatStr);
        return dateformat.format(date);
    }

    public static String formatTimestampStr(Timestamp timestamp, String formatStr) {
        return formatDateStr(timestamp, formatStr);
    }

    public static Date formatStrDate(String str, String formatStr) throws ParseException {
        Date rst = null;
        try{
            if(StringUtils.isBlank(str)){
                throw new Exception("转换格式不能为空");
            }
            DateFormat dateformat = new SimpleDateFormat(formatStr);
            rst = dateformat.parse(str);
        } catch (Exception e) {
            int offset = 0;
            if(e instanceof ParseException){
                offset = ((ParseException) e).getErrorOffset();
            }
            throw new ParseException(str+"转换Date异常："+e.getMessage(), offset);
        }
        return rst;
    }

    public static Timestamp formatStrTimestamp(String str, String formatStr) throws ParseException {
        return new Timestamp(formatStrDate(str, formatStr).getTime());
    }

    public static int[] getYMWD(Date date) {
        int[] rst = new int[3];
        Calendar calendar = Calendar.getInstance();
        // 设置一周的第一天为周一
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        // 处理跨年
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        int yearBefore = calendar.get(Calendar.YEAR);
        int weekBefore = calendar.get(Calendar.WEEK_OF_YEAR);
        if (week < weekBefore) {
            yearBefore += 1;
            year = yearBefore;
        }
        rst[0] = year;
        rst[1] = month;
        rst[2] = week;
        return rst;
    }

    public static Timestamp getMonthFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 获取某月最小天数
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最小天数
        calendar.set(Calendar.DAY_OF_MONTH, firstDay);
        return new Timestamp(calendar.getTime().getTime());
    }

    public static Timestamp getMonthLastDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获取某月最大天数
        int lastDay=0;
        //2月的平年瑞年天数
        if(date.getMonth()==1) {
            lastDay = cal.getLeastMaximum(Calendar.DAY_OF_MONTH);
        }else {
            lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        return new Timestamp(cal.getTime().getTime());
    }

    public static Timestamp getWeekFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        //设置年份
        calendar.set(Calendar.YEAR, getYMWD(date)[0]);
        //设置周数
        calendar.set(Calendar.WEEK_OF_YEAR, getYMWD(date)[2]);
        // 获取某周最小天数
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_WEEK);
        //设置日历中指定周的最小天数
        calendar.set(Calendar.DAY_OF_WEEK, firstDay);
        return new Timestamp(calendar.getTime().getTime());
    }

    public static Timestamp getWeekLastDay(Date date) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, getYMWD(date)[0]);
        //设置周数
        cal.set(Calendar.WEEK_OF_YEAR, getYMWD(date)[2]);
        // 获取某周最大天数
        int lastDay = cal.getLeastMaximum(Calendar.DAY_OF_WEEK);
        //设置日历中指定周的最大天数
        cal.set(Calendar.DAY_OF_WEEK, lastDay);
        return new Timestamp(cal.getTime().getTime());
    }

    //两日期之间相差天数去除时间，仅按照日期算
    public static int countBetweenDays(Date d1, Date d2){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        if(d1.getTime()<=d2.getTime()){
            cal1.setTime(d1);
            cal2.setTime(d2);
        }else{
            cal1.setTime(d2);
            cal2.setTime(d1);
        }
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {//不同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {//闰年
                    timeDistance += 366;
                } else {//不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else {//同年
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }
}

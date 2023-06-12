package com.relyme.linkOccupation.utils.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date geLastWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }

    /**
     * 获取本周星期一日期
     *
     * @param date
     * @return
     */
    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 获取下周星期一
     *
     * @param date
     * @return
     */
    public static Date getNextWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }


    /**
     * 根据当前日期获取前几周星期的周一和周日日期
     *
     * @param days 为7 的倍数 当day 为7 时获取上周的周一和周日
     * @return
     */
    public static LocalDate[] getWeekMondayAndSundayLast(int days) {
        LocalDate now = LocalDate.now();
        // 求这个日期上一周的周一、周日
        LocalDate todayOfLastWeek = now.minusDays(days);
        LocalDate monday = todayOfLastWeek.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1);
        LocalDate sunday = todayOfLastWeek.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).minusDays(1);
        LocalDate[] localDates = new LocalDate[]{monday, sunday};
        return localDates;
    }

    /**
     * 获取当月第一天和最后一天日期
     *
     * @return LocalDate[]
     */
    public static LocalDate[] getThisMonthFirstAndEndDays() {
        LocalDate localDate = LocalDate.now();
        //本月第一天
        LocalDate firstday = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), 1);
        //本月的最后一天
        LocalDate lastDay = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return new LocalDate[]{firstday, lastDay};
    }


    /**
     * 获取当月第一天和最后一天日期 带时间范围 00:00:00 23:59:59
     *
     * @return LocalDate[]
     */
    public static LocalDateTime[] getThisMonthFirstAndEndDaysWithRange() {
        LocalDate localDate = LocalDate.now();
        //本月第一天
        LocalDate firstday = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), 1);
        //本月的最后一天
        LocalDate lastDay = localDate.with(TemporalAdjusters.lastDayOfMonth());

        LocalDateTime firstDateTime = LocalDateTime.of(firstday.getYear(), firstday.getMonth(), firstday.getDayOfMonth(), 00, 00, 00);
        LocalDateTime endDateTime = LocalDateTime.of(lastDay.getYear(), lastDay.getMonth(), lastDay.getDayOfMonth(), 23, 59, 59);

        return new LocalDateTime[]{firstDateTime, endDateTime};
    }

    /**
     * 获取上月的第一天和最后一天日期
     *
     * @return LocalDate[]
     */
    public static LocalDate[] getLastMonthFirstAndEndDays() {
        LocalDate localDate = LocalDate.now();
        LocalDate lastMonth;
        if (localDate.getMonthValue() == 1) {
            lastMonth = LocalDate.of(localDate.getYear() - 1, 12, 1);
        } else {
            lastMonth = LocalDate.of(localDate.getYear(), localDate.getMonthValue() - 1, 1);
        }
        LocalDate lastDay = lastMonth.with(TemporalAdjusters.lastDayOfMonth());
        return new LocalDate[]{lastMonth, lastDay};
    }


    /**
     * 获取上月的第一天和最后一天日期 带时间范围 00:00:00 23:59:59
     *
     * @return LocalDate[]
     */
    public static LocalDateTime[] getLastMonthFirstAndEndDaysWithRange() {
        LocalDate[] localDates = getLastMonthFirstAndEndDays();
        LocalDate firstDate = localDates[0];
        LocalDate endDate = localDates[1];

        LocalDateTime firstDateTime = LocalDateTime.of(firstDate.getYear(), firstDate.getMonth(), firstDate.getDayOfMonth(), 00, 00, 00);
        LocalDateTime endDateTime = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), 23, 59, 59);
        return new LocalDateTime[]{firstDateTime, endDateTime};
    }


    /**
     * 根据当前日期获取前几周星期的周一和周日日期 带时分秒范围 00:00:00 23:59:59
     *
     * @param days 为7 的倍数 当day 为7 时获取上周的周一和周日
     * @return
     */
    public static LocalDateTime[] getWeekMondayAndSundayLastWithRange(int days) {
        LocalDate[] localDates = getWeekMondayAndSundayLast(days);
        LocalDate startDate = localDates[0];
        LocalDate endDate = localDates[1];
        LocalDateTime begin = LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), 00, 00, 00);
        LocalDateTime end = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), 23, 59, 59);
        return new LocalDateTime[]{begin, end};
    }
}

package com.heart.ticket.demo;

import java.util.Calendar;
import java.util.Date;

/**
 * @author wfli
 * @since 2024/1/30 11:07
 */
public class MonthDemo {

    public static void main(String[] args) {
        // 如果不设置时间，则默认为当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        System.out.println("现在时刻：" + calendar.getTime());


        int year = calendar.get(Calendar.YEAR); // 获取当前年份
        System.out.println("现在是" + year + "年 ");
        int month = calendar.get(Calendar.MONTH) + 1; // 获取当前月份（月份从 0 开始，所以加 1）
        System.out.print(month + "月 ");
        int day = calendar.get(Calendar.DATE); // 获取日
        System.out.print(day + "日 ");
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 获取今天星期几（以星期日为第一天）
        System.out.print("星期" + week + " ");
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // 获取当前小时数（24 小时制）
        System.out.print(hour + "时 ");
        int minute = calendar.get(Calendar.MINUTE); // 获取当前分钟
        System.out.print(minute + "分 ");
        int second = calendar.get(Calendar.SECOND); // 获取当前秒数
        System.out.print(second + "秒 ");
        int millisecond = calendar.get(Calendar.MILLISECOND); // 获取毫秒数
        System.out.print(millisecond + "毫秒 ");
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); // 获取今天是本月第几天
        System.out.println("今天是本月的第 " + dayOfMonth + " 天");
        int dayOfWeekInMonth = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH); // 获取今天是本月第几周
        System.out.println("今天是本月第 " + dayOfWeekInMonth + " 周");
        int many = calendar.get(Calendar.DAY_OF_YEAR); // 获取今天是今年第几天
        System.out.println("今天是今年第 " + many + " 天");
        Calendar c = Calendar.getInstance();
        c.set(2035, Calendar.AUGUST, 8); // 设置年月日，时分秒将默认采用当前值
        System.out.println("设置日期为 2035-8-8 后的时间：" + c.getTime()); // 输出时间


        calendar.setTime(new Date());
        calendar.set(2024, Calendar.JANUARY, 27);
        System.out.println("2024-01-27 星期" + (calendar.get(Calendar.DAY_OF_WEEK) - 1) + "是1月第 " + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH) + " 周");
        calendar.set(2024, Calendar.JANUARY, 28);
        System.out.println("2024-01-28 星期" + (calendar.get(Calendar.DAY_OF_WEEK) - 1) + "是1月第 " + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH) + " 周");
        calendar.set(2024, Calendar.JANUARY, 29);
        System.out.println("2024-01-29 星期" + (calendar.get(Calendar.DAY_OF_WEEK) - 1) + "是1月第 " + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH) + " 周");
    }
}

package cn.xzxy.lewy.date.chineseDate;

import cn.hutool.core.date.ChineseDate;
import cn.xzxy.lewy.date.javaTimeDate.LocalDateUtils;

import java.time.LocalDate;

/**
 * 中国农历（阴历）工具类
 * based one Hutools
 */
public class ChineseDateTest {

    public static void main(String[] args) {
        //通过公历构建
        // ChineseDate date = new ChineseDate(LocalDateUtils.DATE_NOW);
        LocalDate localDate = LocalDateUtils.string_yyyy_MM_dd_ConversionLocalDate("2021-06-05");
        ChineseDate date = new ChineseDate(LocalDateUtils.localDateToDate(localDate));
        System.out.println(date.getChineseMonth()); // 一月
        System.out.println(date.getChineseMonthName()); // 正月
        System.out.println(date.getChineseDay()); // 初一
        System.out.println(date.getCyclical()); // 辛丑
        System.out.println(date.getChineseZodiac()); // 生肖：牛
        System.out.println(date.getFestivals()); // 传统节日（部分支持，逗号分隔）：春节
        System.out.println(date.toString()); // 辛丑牛年 正月初一
        System.out.println(date.getCyclicalYMD()); // 辛丑年庚寅月辛卯日
        System.out.println(date.getChineseYear()); // 2021
        System.out.println(date.toStringNormal()); // 2021-01-01 == 春节
        System.out.println(date.getDay()); // 1 == 春节 正月第一天
        System.out.println(date.getMonth()); // 1 == 春节 正月第一天
    }

}

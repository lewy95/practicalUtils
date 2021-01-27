package cn.xzxy.lewy.date.javaUtilDate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class DateUtils {

    /**
     * 将 Date对象 转换为 yyyy-MM-dd HH:mm:ss的字符串
     *
     * @param date rq
     * @return str
     */
    public static String getLongDateText(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 去除日期中的时分秒，只保留年月日
     */
    public static String sliceRQ(String date) {
        String formattedDate = "";
        if (date != null && date.length() >= 10) {
            formattedDate = date.substring(0, 10);
        }
        return formattedDate;
    }

    /**
     * 获得当前时间的时间戳字符串：“191115165637”
     */
    public static String getTimeStamp(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        return sdf.format(date);
    }

    /**
     * 将时间字符串解析为日期对象
     * 需要自定义字符串格式，如 yyyy/MM/dd
     *
     * @param dateStr 日期字符串
     */
    public static Date parse2Date(String dateStr) {
        try {
            Date date = null;
            if (dateStr != null && !"".equals(dateStr)) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                date = format.parse(dateStr);
            }
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更常见的，根据自定义格式将字符串转化成日期
     *
     * @param strDate str
     * @return date
     */
    public static Date parse2DateByFormat(String strDate, String dateFormat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            return sdf.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * datetype:
     * day：     获取过去一天
     * week：    获取当前时间减7天的时间；
     * month：   获取当前时间减一个月的时间
     * quarter   获取当前时间减三个月的时间
     * halfYear：获取当前时间减6个月的时间
     * year：    获取当前时间减1年的时间
     *
     * @param date 操作日期
     * @param dateType 日期类型
     * @return str
     */
    public static String getPastDateStr(Date date, String dateType) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        if (dateType.equals("minute")) {
            c.add(Calendar.MINUTE, -3);
        }
        // 过去一天
        if (dateType.equals("day")) {
            c.add(Calendar.DATE, -1);
        }
        // 过去七天
        if (dateType.equals("week")) {
            c.add(Calendar.DATE, -7);
        }
        // 过去一月
        if (dateType.equals("month")) {
            c.add(Calendar.MONTH, -1);
        }
        // 过去三个月 季度
        if (dateType.equals("quarter")) {
            c.add(Calendar.MONTH, -3);
        }
        // 过去六个月 half a year
        if (dateType.equals("halfYear")) {
            c.add(Calendar.MONTH, -6);
        }
        // 过去一年
        if (dateType.equals("year")) {
            c.add(Calendar.YEAR, -1);
        }
        Date d = c.getTime();
        return format.format(d);
    }

    /**
     * 根据出生日期算年龄
     *
     * @param birthDay 出生日期
     * @return 年龄
     */
    public static int birthConvertToAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth + 1;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }

}


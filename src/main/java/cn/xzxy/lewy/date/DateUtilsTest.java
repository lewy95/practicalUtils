package cn.xzxy.lewy.date;

import org.junit.Test;

import java.util.Date;

public class DateUtilsTest {

    @Test
    public void testGetLongDateText() {
        Date date = new Date();
        String longDateText = DateUtils.getLongDateText(date);
        System.out.println(longDateText);
    }

    @Test
    public void testSliceRQ() {
        Date date = new Date();
        String longDateText = DateUtils.getLongDateText(date);
        String rq = DateUtils.sliceRQ(longDateText);
        System.out.println(rq);
    }

    @Test
    public void testGetTimeStamp() {
        Date date = new Date();
        System.out.println(DateUtils.getTimeStamp(date));
    }

    @Test
    public void parse2Date() {
        String dateStr = "2019-11-11";
        Date date = DateUtils.parse2DateByFormat(dateStr, "yyyy-MM-dd");
        System.out.println(date);
    }

    @Test
    public void testGetPastDateStr() throws Exception {
        Date date = new Date();
        String pastDateStr = DateUtils.getPastDateStr(date, "year");
        System.out.println(pastDateStr);
    }

    @Test
    public void testBirth2Age() throws Exception {
        String dateStr = "1995/11/11";
        Date date = DateUtils.parse2Date(dateStr);
        int age = DateUtils.birthConvertToAge(date);
        System.out.println(age);
    }

    @Test
    public void testCalendarAdd() {

    }
}

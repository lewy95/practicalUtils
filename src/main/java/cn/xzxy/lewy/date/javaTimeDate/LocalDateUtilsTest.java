package cn.xzxy.lewy.date.javaTimeDate;


import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateUtilsTest {

    @Test
    public void getLocalDate() {
        System.out.println(LocalDate.now());
        LocalDateTime localDateTime = LocalDateUtils.stringConversionLocalDateTime("2021-03-14 11:22:33");
        System.out.println(LocalDateUtils.localDateTimeConversiontimestamp(localDateTime));
    }

}

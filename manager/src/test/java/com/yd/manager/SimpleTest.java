package com.yd.manager;

import com.yd.manager.config.hibernate.PhpTimeConvert;
import org.junit.Test;

import java.time.LocalDate;

public class SimpleTest {

    @Test
    public void test1() throws Exception {
        LocalDate now = LocalDate.now();
        System.out.println(now);
        System.out.println(now.plusDays(1));
        System.out.println(now.minusDays(1));

        System.out.println(now.minusWeeks(1));
        System.out.println(now.minusDays(6));

        System.out.println(now.minusMonths(1));
    }

    @Test
    public void time() {
        System.out.println(new PhpTimeConvert().convertToEntityAttribute(1510635985L));
    }
}

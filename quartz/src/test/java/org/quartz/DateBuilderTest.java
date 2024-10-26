package org.quartz;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.quartz.DateBuilder.*;

/**
 * Unit test for JobDetail.
 */
class DateBuilderTest {

    void testBasicBuilding() {
        Date t = dateOf(10, 30, 0, 1, 7, 2013);  // july 1 10:30:00 am

        Calendar vc = Calendar.getInstance();
        vc.set(Calendar.YEAR, 2013);
        vc.set(Calendar.MONTH, Calendar.JULY);
        vc.set(Calendar.DAY_OF_MONTH, 1);
        vc.set(Calendar.HOUR_OF_DAY, 10);
        vc.set(Calendar.MINUTE, 30);
        vc.set(Calendar.SECOND, 0);
        vc.set(Calendar.MILLISECOND, 0);

        Date v = vc.getTime();
        assertEquals(t, v, "DateBuilder-produced date is not as expected.");
    }

    @Test
    void testBuilder() {
        Calendar vc = Calendar.getInstance();
        vc.set(Calendar.YEAR, 2013);
        vc.set(Calendar.MONTH, Calendar.JULY);
        vc.set(Calendar.DAY_OF_MONTH, 1);
        vc.set(Calendar.HOUR_OF_DAY, 10);
        vc.set(Calendar.MINUTE, 30);
        vc.set(Calendar.SECOND, 0);
        vc.set(Calendar.MILLISECOND, 0);

        Date bd = newDate().inYear(2013).inMonth(JULY).onDay(1).atHourOfDay(10).atMinute(30).atSecond(0).build();
        assertEquals(vc.getTime(), bd, "DateBuilder-produced date is not as expected.");

        bd = newDate().inYear(2013).inMonthOnDay(JULY, 1).atHourMinuteAndSecond(10, 30, 0).build();
        assertEquals(vc.getTime(), bd, "DateBuilder-produced date is not as expected.");

        TimeZone tz = TimeZone.getTimeZone("GMT-4:00");
        Locale lz = Locale.TAIWAN;
        vc = Calendar.getInstance(tz, lz);
        vc.set(Calendar.YEAR, 2013);
        vc.set(Calendar.MONTH, Calendar.JUNE);
        vc.set(Calendar.DAY_OF_MONTH, 1);
        vc.set(Calendar.HOUR_OF_DAY, 10);
        vc.set(Calendar.MINUTE, 33);
        vc.set(Calendar.SECOND, 12);
        vc.set(Calendar.MILLISECOND, 0);

        bd = newDate().inYear(2013).inMonth(JUNE).onDay(1).atHourOfDay(10).atMinute(33).atSecond(12).inTimeZone(tz).inLocale(lz).build();
        assertEquals(vc.getTime(), bd, "DateBuilder-produced date is not as expected.");

        bd = newDateInLocale(lz).inYear(2013).inMonth(JUNE).onDay(1).atHourOfDay(10).atMinute(33).atSecond(12).inTimeZone(tz).build();
        assertEquals(vc.getTime(), bd, "DateBuilder-produced date is not as expected.");

        bd = newDateInTimezone(tz).inYear(2013).inMonth(JUNE).onDay(1).atHourOfDay(10).atMinute(33).atSecond(12).inLocale(lz).build();
        assertEquals(vc.getTime(), bd, "DateBuilder-produced date is not as expected.");

        bd = newDateInTimeZoneAndLocale(tz, lz).inYear(2013).inMonth(JUNE).onDay(1).atHourOfDay(10).atMinute(33).atSecond(12).build();
        assertEquals(vc.getTime(), bd, "DateBuilder-produced date is not as expected.");
    }

    @Test
    void testEvensBuilders() {
        Calendar vc = Calendar.getInstance();
        vc.set(Calendar.YEAR, 2013);
        vc.set(Calendar.MONTH, Calendar.JUNE);
        vc.set(Calendar.DAY_OF_MONTH, 1);
        vc.set(Calendar.HOUR_OF_DAY, 10);
        vc.set(Calendar.MINUTE, 33);
        vc.set(Calendar.SECOND, 12);
        vc.set(Calendar.MILLISECOND, 0);

        Calendar rd = (Calendar) vc.clone();

        Date bd = newDate().inYear(2013).inMonth(JUNE).onDay(1).atHourOfDay(10).atMinute(33).atSecond(12).build();
        assertEquals(vc.getTime(), bd, "DateBuilder-produced date is not as expected.");

        rd.set(Calendar.MILLISECOND, 13);
        bd = evenSecondDateBefore(rd.getTime());
        assertEquals(vc.getTime(), bd, "DateBuilder-produced date is not as expected.");

        vc.set(Calendar.SECOND, 13);
        rd.set(Calendar.MILLISECOND, 13);
        bd = evenSecondDate(rd.getTime());
        assertEquals(vc.getTime(), bd, "DateBuilder-produced date is not as expected.");

        vc.set(Calendar.SECOND, 0);
        vc.set(Calendar.MINUTE, 34);
        rd.set(Calendar.SECOND, 13);
        bd = evenMinuteDate(rd.getTime());
        assertEquals(vc.getTime(), bd, "DateBuilder-produced date is not as expected.");

        vc.set(Calendar.SECOND, 0);
        vc.set(Calendar.MINUTE, 33);
        rd.set(Calendar.SECOND, 13);
        bd = evenMinuteDateBefore(rd.getTime());
        assertEquals(vc.getTime(), bd, "DateBuilder-produced date is not as expected.");

        vc.set(Calendar.SECOND, 0);
        vc.set(Calendar.MINUTE, 0);
        vc.set(Calendar.HOUR_OF_DAY, 11);
        rd.set(Calendar.SECOND, 13);
        bd = evenHourDate(rd.getTime());
        assertEquals(vc.getTime(), bd, "DateBuilder-produced date is not as expected.");

        vc.set(Calendar.SECOND, 0);
        vc.set(Calendar.MINUTE, 0);
        vc.set(Calendar.HOUR_OF_DAY, 10);
        rd.set(Calendar.SECOND, 13);
        bd = evenHourDateBefore(rd.getTime());
        assertEquals(vc.getTime(), bd, "DateBuilder-produced date is not as expected.");

        Date td = new Date();
        bd = evenHourDateAfterNow();
        vc.setTime(bd);
        assertEquals(0, vc.get(Calendar.MINUTE), "DateBuilder-produced date is not as expected.");
        assertEquals(0, vc.get(Calendar.SECOND), "DateBuilder-produced date is not as expected.");
        assertEquals(0, vc.get(Calendar.MILLISECOND), "DateBuilder-produced date is not as expected.");
        assertTrue(bd.after(td), "DateBuilder-produced date is not as expected.");

        vc.set(Calendar.SECOND, 54);
        vc.set(Calendar.MINUTE, 13);
        vc.set(Calendar.HOUR_OF_DAY, 8);
        bd = nextGivenMinuteDate(vc.getTime(), 15);
        vc.setTime(bd);
        assertEquals(8, vc.get(Calendar.HOUR_OF_DAY), "DateBuilder-produced date is not as expected.");
        assertEquals(15, vc.get(Calendar.MINUTE), "DateBuilder-produced date is not as expected.");
        assertEquals(0, vc.get(Calendar.SECOND), "DateBuilder-produced date is not as expected.");
        assertEquals(0, vc.get(Calendar.MILLISECOND), "DateBuilder-produced date is not as expected.");
    }

    @Test
    void testGivenBuilders() {
        Calendar vc = Calendar.getInstance();

        vc.set(Calendar.SECOND, 54);
        vc.set(Calendar.MINUTE, 13);
        vc.set(Calendar.HOUR_OF_DAY, 8);
        Date bd = nextGivenMinuteDate(vc.getTime(), 45);
        vc.setTime(bd);
        assertEquals(8, vc.get(Calendar.HOUR_OF_DAY), "DateBuilder-produced date is not as expected.");
        assertEquals(45, vc.get(Calendar.MINUTE), "DateBuilder-produced date is not as expected.");
        assertEquals(0, vc.get(Calendar.SECOND), "DateBuilder-produced date is not as expected.");
        assertEquals(0, vc.get(Calendar.MILLISECOND), "DateBuilder-produced date is not as expected.");

        vc.set(Calendar.SECOND, 54);
        vc.set(Calendar.MINUTE, 46);
        vc.set(Calendar.HOUR_OF_DAY, 8);
        bd = nextGivenMinuteDate(vc.getTime(), 45);
        vc.setTime(bd);
    }
}
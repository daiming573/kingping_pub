//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.common.util.date;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class DateUtil {
    public static final String TIME_FORMAT_ALL = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";
    public static final String TIME_FORMAT_T = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String TIME_FORMAT_ZONE = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public static final String FORMAT_MEDIUM_TZ = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String DATE_FORMAT_yyyy_MM = "yyyy-MM";
    public static final String DATE_FORMAT_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ";
    public static final String DATE_FORMAT_yyyy = "yyyy";
    public static final String IIMEZONE_GMT8 = "GMT+8";
    public static final String FORMAT_DATE_MONTH_CHINESE = "MM月";
    public static final String FORMAT_DATE_CHINESE = "yyyy年MM月dd日";
    public static final String FORMAT_DATE_YEAR_MONTH_CHINESE = "yyyy年MM月";
    public static final String FORMAT_DATETIME_MILLISECOND = "yyyyMMddHHmmssSSS";
    public static final String TIME_DATE_HOUR_FORMAT = "yyyy-MM-dd HH:00:00";
    public static final String FORMAT_DATE_SLASH = "yyyy/MM/dd";
    public static final String FORMAT_DATETIME_SLASH = "yyyy/MM/dd HH:mm:ss";
    public static final String TIME_FORMAT_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String TIME_FORMAT_ISO = "EEE, dd MMM yyyy HH:mm:ss 'GMT'";
    public static final Integer[] SEASON_BEGIN = new Integer[]{0, 3, 6, 9};
    private static final ZoneId defZoneId = ZoneId.systemDefault();
    private static volatile long latestTime = 0L;
    private static final Object lock = new Object();

    public DateUtil() {
    }

    public static Date getUniqueNowDate() {
        synchronized (lock) {
            long now = System.currentTimeMillis();
            latestTime = latestTime < now ? now : latestTime + 1L;
            return new Date(latestTime);
        }
    }

    public static Date parseISODateTime2Date(String dateStr) {
        if (dateStr.equalsIgnoreCase("0000-00-00T00:00:00Z")) {
            return new Date();
        } else {
            DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTimeParser();
            return dateTimeFormatter.parseDateTime(dateStr).toDate();
        }
    }

    public static String getTimeFormatIso(Date date) {
        TimeZone tz = TimeZone.getDefault();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        df.setTimeZone(tz);
        return df.format(date);
    }

    public static String getUTCFormatTz(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);
        return df.format(date);
    }

    public static String getFormatTime(long time) {
        return getFormatTime(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getFormatTime(long time, String format) {
        return getFormatTime(Instant.ofEpochMilli(time), format);
    }

    public static String getFormatTime(Date time) {
        return getFormatTime(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getFormatTime(Date time, String format) {
        return getFormatTime(time.toInstant(), format);
    }

    public static String getFormatTime(Instant instant, String format) {
        return java.time.format.DateTimeFormatter.ofPattern(format).withZone(defZoneId).format(instant);
    }

    public static String getFormatTime(Instant instant, String format, ZoneId zoneId, Locale locale) {
        return java.time.format.DateTimeFormatter.ofPattern(format).withZone(zoneId).withLocale(locale).format(instant);
    }

    public static Date getPreDate(Date date) {
        return Date.from(date.toInstant().plus(1L, ChronoUnit.DAYS));
    }

    public static Date getLastDate(Date date) {
        return Date.from(date.toInstant().minus(1L, ChronoUnit.DAYS));
    }

    public static boolean compareStartEndDate(Date startDate, Date endDate) {
        return startDate != null && endDate != null && startDate.getTime() <= endDate.getTime();
    }

    public static Date getDateByStr(String str) {
        return getDateByStr(str, "yyyy-MM-dd HH24:mm:ss");
    }

    public static Date getDateByStr(String str, String format) {
        if (str != null && !"".equals(str)) {
            DateFormat dateFormat = new SimpleDateFormat(format);
            Date date = null;

            try {
                date = dateFormat.parse(str);
            } catch (ParseException var5) {
                var5.printStackTrace();
            }

            return date;
        } else {
            return null;
        }
    }

    public static Date getDayOfMinTime(long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), defZoneId);
        return Date.from(ZonedDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN, defZoneId).toInstant());
    }

    public static Date getDayOfMinTime(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), defZoneId);
        return Date.from(ZonedDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN, defZoneId).toInstant());
    }

    public static Date getDayOfMaxTime(long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), defZoneId);
        return Date.from(ZonedDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX, defZoneId).toInstant());
    }

    public static Date getDayOfMaxTime(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), defZoneId);
        return Date.from(ZonedDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX, defZoneId).toInstant());
    }

    public static Timestamp getSecondScaleTime(long time) {
        return new Timestamp(time / 1000L * 1000L);
    }

    public static int getCurrentHour(long time) {
        return Instant.ofEpochMilli(time).get(ChronoField.HOUR_OF_DAY);
    }

    public static Date getMonthFistDayDate(long time) {
        return Date.from(Instant.ofEpochSecond(time).with(TemporalAdjusters.firstDayOfNextMonth()));
    }

    public static List<Date> getDayDateList(Date startDate, Date endDate) {
        List<Date> dateList = new ArrayList();
        Calendar eachCalendar = Calendar.getInstance();
        eachCalendar.setTime(startDate);

        while (eachCalendar.getTime().compareTo(endDate) <= 0) {
            dateList.add(eachCalendar.getTime());
            eachCalendar.add(5, 1);
        }

        return dateList;
    }

    public static List<Date> getMonthDateList(long startTime, long endTime) {
        List<Date> dateList = new ArrayList();
        Date startDateTime = getMonthFistDayDate(startTime);
        Date endDateTime = getMonthFistDayDate(endTime);
        Calendar eachCalendar = Calendar.getInstance();
        eachCalendar.setTime(startDateTime);

        while (eachCalendar.getTime().compareTo(endDateTime) <= 0) {
            dateList.add(eachCalendar.getTime());
            eachCalendar.add(2, 1);
        }

        return dateList;
    }

    public static Date addMinutes(Date date, int amount) {
        return Date.from(date.toInstant().plus((long) amount, ChronoUnit.MINUTES));
    }

    public static Date addDays(Date date, int amount) {
        return Date.from(date.toInstant().plus((long) amount, ChronoUnit.DAYS));
    }

    public static Date addMonths(Date date, int amount) {
        return Date.from(date.toInstant().plus((long) amount, ChronoUnit.MONTHS));
    }

    public static Date addYears(Date date, int amount) {
        return Date.from(date.toInstant().plus((long) amount, ChronoUnit.YEARS));
    }

    public static long getBetweenHour(long startTime, long endTime) {
        Instant startInstant = Instant.ofEpochMilli(startTime);
        Instant endInstant = Instant.ofEpochMilli(endTime);
        return Duration.between(startInstant, endInstant).toHours();
    }

    public static Date getYearFistDayDate(long time) {
        return Date.from(Instant.ofEpochMilli(time).with(TemporalAdjusters.firstDayOfYear()));
    }

    public static int getWeekOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(2);
        calendar.setTime(date);
        return calendar.get(3);
    }

    public static int getYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(date);
        return Integer.parseInt(year);
    }

    public static Date[] getDateArrays(Date start, Date end) {
        List<Date> ret = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        Date tmpDate = calendar.getTime();

        for (long endTime = end.getTime(); tmpDate.before(end) || tmpDate.getTime() == endTime; tmpDate = calendar.getTime()) {
            ret.add(calendar.getTime());
            calendar.add(5, 1);
        }

        Date[] dates = new Date[ret.size()];
        return (Date[]) ((Date[]) ret.toArray(dates));
    }

    public static String[] getMonthsStr(Date start, Date end) {
        List<String> ret = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        int year = calendar.get(1);
        int tmpMonth = calendar.get(2) + 1;
        calendar.setTime(end);

        for (long endMonth = (long) (calendar.get(2) + 1); (long) tmpMonth <= endMonth; ++tmpMonth) {
            if (tmpMonth < 10) {
                ret.add(year + "-0" + tmpMonth);
            } else {
                ret.add(year + "-" + tmpMonth);
            }
        }

        String[] months = new String[ret.size()];
        return (String[]) ((String[]) ret.toArray(months));
    }

    public static Date getNowYearFirstDay(int year) {
        LocalDate localDate = LocalDate.of(year, Month.JANUARY, 1);
        return Date.from(localDate.atStartOfDay().atZone(defZoneId).toInstant());
    }

    public static Date getNowWeekBegin(Date date) {
        return Date.from(date.toInstant().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)));
    }

    public static Date getNowMonthFirstDay(Date date) {
        return Date.from(date.toInstant().with(TemporalAdjusters.firstDayOfMonth()));
    }

    public static Date getNowMonthLastDay(Date date) {
        return Date.from(date.toInstant().with(TemporalAdjusters.lastDayOfMonth()));
    }

    public static Date getSeasonFirstDay(int year, int season) {
        try {
            --season;
            Calendar cal = Calendar.getInstance();
            cal.set(1, year);
            cal.set(2, SEASON_BEGIN[season]);
            cal.set(5, 1);
            cal.set(11, 0);
            cal.set(12, 0);
            cal.set(13, 0);
            return cal.getTime();
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static Date getSeasonLastDay(int year, int season) {
        try {
            int mon = season * 3 - 1;
            Calendar cal = Calendar.getInstance();
            cal.set(1, year);
            cal.set(2, mon);
            cal.set(5, cal.getActualMaximum(5));
            cal.set(11, 23);
            cal.set(12, 59);
            cal.set(13, 59);
            return cal.getTime();
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static Date getNowYearLastDay(Date date) {
        return Date.from(date.toInstant().with(TemporalAdjusters.lastDayOfYear()));
    }

    public static List<Date> getYearDateList(long startTime, long endTime) {
        List<Date> dateList = new ArrayList();
        Date startDateTime = getYearFistDayDate(startTime);
        Date endDateTime = getYearFistDayDate(endTime);
        Calendar eachCalendar = Calendar.getInstance();
        eachCalendar.setTime(startDateTime);

        while (eachCalendar.getTime().compareTo(endDateTime) <= 0) {
            dateList.add(eachCalendar.getTime());
            eachCalendar.add(1, 1);
        }

        return dateList;
    }

    public static String getFormatTimeHour(long time) {
        return getFormatTime(time, "yyyy-MM-dd HH:00:00");
    }

    public static String getMonthStrChinese(String dateStr) {
        Date date = getDateByStr(dateStr, "yyyy-MM");
        return null == date ? "" : getFormatTime(date, "MM月");
    }

    public static String getFormatDateYMDdefaultEmpty(Date date) {
        return getFormatDateYMD(date, "");
    }

    public static String getFormatDateYMD(Date date, String defaultStr) {
        return date == null ? defaultStr : getFormatTime(date, "yyyy-MM-dd");
    }

    public static String getFormatTimeNoMsDefaultEmpty(Date time) {
        return getFormatTimeNoMs(time, "");
    }

    public static String getFormatTimeNoMs(Date time, final String defaultStr) {
        return time == null ? defaultStr : getFormatTime(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date getMonthFirstDay(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return Date.from(yearMonth.atDay(1).atStartOfDay(defZoneId).toInstant());
    }

    public static String getNowTimeWithTimeZone(String timeFormat, String zoneStr, Locale locale) {
        return getFormatTime(Instant.now(), timeFormat, ZoneId.of(zoneStr), locale);
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String getCurrentTimeSssStr() {
        return getFormatTime(new Date(), DateUtil.FORMAT_DATETIME_MILLISECOND);
    }


}

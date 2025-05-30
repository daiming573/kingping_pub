//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.common.util.date;

import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
/**
 * 自定义时间转换格式
 * 兼容以下格式：
 * 1.ISO8601时间格式:
 *     yyyy-MM-dd'T'HH:mm:ss.SSSXXX -->2004-05-03T17:30:08+08:00
 * 	   yyyy-MM-dd'T'HH:mm:ss.SSSZ -->2004-05-03T17:30:08.000Z
 * 	   yyyy-MM-dd'T'HH:mm:ssZ -->2004-05-03T17:30:08Z
 * 2.普通时间格式：
 *      yyyy-MM-dd HH:mm:ss
 *      yyyy-MM-dd
 * 3.long类型的时间
 * 4.RFC1123格式
 *     EEE, dd MMM yyyy HH:mm:ss zzz
 * @author  dm
 * @since 1.0.0
 */
public class DateFormat extends StdDateFormat implements Cloneable {
    private static final char T = 'T';
    private static final char HYPHEN = '-';
    private static final char COLON = ':';

    /**
     * 默认设置：时区为当前时区，locale为简体中文，宽松时间转换，格式化tz
     * @author dm
     * @since 1.0.0
     */
    public DateFormat() {
        super(TimeZone.getDefault(), Locale.SIMPLIFIED_CHINESE, true, true);
    }

    public DateFormat(TimeZone tz, Locale locale) {
        super(tz, locale, true, true);
    }

    @Override
    public Date parse(String dateStr) throws ParseException {
        dateStr = dateStr.trim();
        ParsePosition pos = new ParsePosition(0);
        Date dt = this.localParseDate(dateStr, pos);
        if (dt != null) {
            return dt;
        } else {
            StringBuilder sb = new StringBuilder();
            String[] var5 = ALL_FORMATS;
            int var6 = var5.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                String f = var5[var7];
                if (sb.length() > 0) {
                    sb.append("\", \"");
                } else {
                    sb.append('"');
                }

                sb.append(f);
            }

            sb.append('"');
            throw new ParseException(String.format("Cannot parse date \"%s\": not compatible with any of standard forms (%s)", dateStr, sb.toString()), pos.getErrorIndex());
        }
    }

    @Override
    public Date parse(String dateStr, ParsePosition pos) {
        try {
            return this.localParseDate(dateStr, pos);
        } catch (ParseException var4) {
            return null;
        }
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        TimeZone tz = this._timezone;
        if (tz == null) {
            tz = TimeZone.getDefault();
        }

        this._format(tz, this._locale, date, toAppendTo);
        return toAppendTo;
    }

    @Override
    public DateFormat clone() {
        super.clone();
        return new DateFormat(this._timezone, this._locale);
    }

    private Date localParseDate(String dateStr, ParsePosition pos) throws ParseException {
        if (dateStr.length() >= 19 && dateStr.charAt(10) == 'T') {
            return this.parseISO8601(dateStr, pos);
        } else if (dateStr.length() == 10 && dateStr.charAt(4) == '-' && dateStr.charAt(7) == '-') {
            return DateUtil.getDateByStr(dateStr, "yyyy-MM-dd");
        } else if (dateStr.length() == 19 && dateStr.charAt(4) == '-' && dateStr.charAt(7) == '-' && dateStr.charAt(13) == ':') {
            return DateUtil.getDateByStr(dateStr);
        } else {
            int i = dateStr.length();

            char ch;
            do {
                --i;
                if (i < 0) {
                    break;
                }

                ch = dateStr.charAt(i);
            }
            while (ch >= '0' && ch <= '9' || i <= 0 && ch == '-');

            return i >= 0 || dateStr.charAt(0) != '-' && !NumberInput.inLongRange(dateStr, false) ? this.parseAsRFC1123(dateStr, pos) : this.parseDateFromLong(dateStr, pos);
        }
    }

    private Date parseISO8601(String dateStr, ParsePosition pos) {
        DateTimeFormatter formatter = ISODateTimeFormat.dateTimeParser();
        return formatter.parseDateTime(dateStr).toDate();
    }

    private Date parseDateFromLong(String longStr, ParsePosition pos) throws ParseException {
        long ts;
        try {
            ts = NumberInput.parseLong(longStr);
        } catch (NumberFormatException var6) {
            throw new ParseException(String.format("Timestamp value %s out of 64-bit value range", longStr), pos.getErrorIndex());
        }

        return new Date(ts);
    }

    public static void main(String[] args) throws ParseException {
        String str = "1546876800000";
        DateFormat dateFormat = new DateFormat();
        Date date = dateFormat.parse(str);
        System.out.println(dateFormat.format(date));
    }
}

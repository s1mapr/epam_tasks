package com.epam.esm.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ISO8601TimeFormatter {

    public static String getFormattedDate(Date date) {
        TimeZone tz = TimeZone.getTimeZone("Europe/Kiev");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        df.setTimeZone(tz);
        return df.format(date);
    }
}

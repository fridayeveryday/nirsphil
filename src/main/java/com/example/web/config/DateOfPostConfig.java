package com.example.web.config;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateOfPostConfig {
    public static String datePattern = "E, dd MMM yyyy, HH:mm:ss, ZZ";

    public static String getDate(long input_date){
        Date date = new Date(input_date);
        if (input_date == 0){
            date = new Date(System.currentTimeMillis());
        }
        return new SimpleDateFormat(datePattern).format(date);
    }
}

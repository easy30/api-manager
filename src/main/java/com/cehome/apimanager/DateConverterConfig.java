package com.cehome.apimanager;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
 
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * requst string to date
 */
@Component
public class DateConverterConfig implements Converter<String, Date> {

    private static String[][] formats={
            {"^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$","yyyy-MM-dd hh:mm:ss"},
            {"^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$","yyyy-MM-dd hh:mm"},
            {"^\\d{4}-\\d{1,2}-\\d{1,2}$","yyyy-MM-dd"},
            {"^\\d{4}-\\d{1,2}$","yyyy-MM"},

    };

 
    @Override
    public Date convert(String source) {
        String value = source.trim();
        if ("".equals(value)) {
            return null;
        }
        for(String[] fomrat :formats){
            if(source.matches(fomrat[0])) return parseDate(source,fomrat[1]);
        }

       throw new IllegalArgumentException("Invalid date value '" + source + "'");

    }
 

    public  Date parseDate(String dateStr, String format) {
        Date date=null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {
 
        }
        return date;
    }
 
}

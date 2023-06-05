package com.iot.sh.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {


    public static Date getTimestamp(String fmtDateTime,String fmt) throws ParseException {
        String timeZone="Asia/Shanghai";
        return getTimestamp(fmtDateTime,fmt,timeZone);
    }

    /**
     *
     * @param fmtDateTime 2022-12-27T14:20:34+08:00
     * @param fmt  yyyy-MM-dd'T'HH:mm:ss 或 yyyy-MM-dd'T'HH:mm:ssXXX
     * @param timeZone
     * @return
     */
    public static Date getTimestamp(String fmtDateTime,String fmt,String timeZone) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        if(timeZone!=null) {
            TimeZone tz = TimeZone.getTimeZone(timeZone);
            sdf.setTimeZone(tz);
        }
        return sdf.parse(fmtDateTime);
    }

    public static String getFmtCurDateTimeMills(){
       String fmt="yyyyMMdd_HHmmss_SSS";
       Date curDate=new Date();
       return getFmtDateTime(curDate,fmt);
    }

    public static String getFmtCurDateTimeSeconds(){
        String fmt="yyyyMMdd_HHmmss";
        Date curDate=new Date();
        return getFmtDateTime(curDate,fmt);
    }

    public static String getFmtDateTime(Date date,String fmt){
        if(date==null) return null;
        SimpleDateFormat sdf=new SimpleDateFormat(fmt);
        return sdf.format(new Date());
    }

}
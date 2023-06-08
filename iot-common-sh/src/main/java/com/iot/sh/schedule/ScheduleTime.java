package com.iot.sh.schedule;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Data
@Slf4j
public class ScheduleTime {
    public static final long oneDay = Duration.ofDays(1).toMillis();

    /**
     *  获取延迟时间
     * @param hourMinTime 格式："hh:MM"
     * @return
     */
    private static long getDelayMillis(String hourMinTime){
        return getDelayMillis(hourMinTime,oneDay);
    }
    /**
     *  获取延迟时间
     * @param hourMinTime 格式："hh:MM"
     * @return
     */
    private static long getDelayMillis(String hourMinTime,long oneDay){
        hourMinTime=hourMinTime+":00";
        long initDelay=getTimeMillis(hourMinTime) - System.currentTimeMillis();
        initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
        return initDelay;
    }

    private   static long getTimeMillis(String time) {
        DateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date=dayFormat.format(new Date());
        return getTimeMillis(date,time);
    }

    private  static long getTimeMillis(String date,String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = dateFormat.parse(date+" "+time);
            return curDate.getTime();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return 0;
    }
}

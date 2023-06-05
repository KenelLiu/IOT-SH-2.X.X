package com.iot.sh.util.date;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
public class CalendarUtil {
	/**
	 * 获取当前日期所属的月份的第一天的日期
	 * @param date
	 * @throws ParseException 
	 */
	public static Date getFirstDayOfMonth(Date date){
	      return getFirstDay(date,0);
	}		
	/**
	 * 获取当前日期几个月后的所属月份的第一天日期
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date getFirstDay(Date date,int month){
	      Calendar cale = Calendar.getInstance();  
	      cale.setTime(date);
	      cale.add(Calendar.MONTH, month);  
	      cale.set(Calendar.DAY_OF_MONTH, 1);  	      
	      return cale.getTime();
	}	
	/**
	 * 获取当前日期所属的月份的最后一天的日期
	 * @param date
	 */
	public static Date getLastDayOfMonth(Date date){
	      return getLastDay(date,0);
	}

	/**
	 * 获取当前日期几个月后的所属月份的最后一天日期
	 * @param date
	 * @param month
	 * @return
	 * @throws ParseException
	 */
	public static Date getLastDay(Date date,int month){
	      Calendar cale = Calendar.getInstance();  
	      cale.setTime(date);
	      cale.add(Calendar.MONTH, month+1);  
	      cale.set(Calendar.DAY_OF_MONTH, 0);    	      
	      return cale.getTime();
	}
	/**
	 * 当前日期 +year后的日期
	 * @param date 日期
	 * @param year 年
	 * @return
	 * @throws ParseException
	 */
	public static Date getAddYear(Date date,int year){
		return getAddDate(date,year,0,0);
	}

	/**
	 * 当前日期 +month后的日期
	 * @param date 日期
	 * @param month 月
	 * @return
	 * @throws ParseException
	 */
	public static Date getAddMonth(Date date,int month){
	      return getAddDate(date,0,month,0);
	}	

	/**
	 * 当前日期 +days后的日期
	 * @param date 日期
	 * @param days 天数
	 * @return
	 * @throws ParseException
	 */
	public static Date getAddDay(Date date,int days){
	      return getAddDate(date,0,0,days);
	}	
	
	/**
	 * 当前日期 +hours后的日期
	 * @param date 日期
	 * @param hours 小时
	 * @return
	 */
	public static Date getAddHours(Date date,int hours){
	      return getAddDate(date,0,0,0,hours);
	}	
	
	/**
	 * 当前日期 +minitues后的日期
	 * @param date 日期
	 * @param minitues 分钟
	 * @return
	 */
	public static Date getAddMinitues(Date date,int minitues){
	      return getAddDate(date,0,0,0,0,minitues);
	}
	/**
	 * 当前日期 +seconds后的日期
	 * @param date 日期
	 * @param seconds 秒
	 * @return
	 */
	public static Date getAddSeconds(Date date,int seconds){
	      return getAddDate(date,0,0,0,0,0,seconds);
	}
	
	/**
	 * 当前日期 +year+month+days后的日期
	 * @param date
	 * @param year
	 * @param month
	 * @param days
	 * @return
	 */
	public static Date getAddDate(Date date,int year,int month,int days) {
	      return getAddDate(date,year,month,days,0,0,0);
	}
	/**
	 * 当前日期 +year+month+days+hours后的日期
	 * @param date
	 * @param year
	 * @param month
	 * @param days
	 * @return
	 */
	public static Date getAddDate(Date date,int year,int month,int days,int hours){
	      return getAddDate(date,year,month,days,hours,0,0);
	}
	/**
	 * 当前日期 +year+month+days+hours+minitues后的日期
	 * @param date
	 * @param year
	 * @param month
	 * @param days
	 * @return
	 */
	public static Date getAddDate(Date date,int year,int month,int days,int hours,int minitues){
		 return getAddDate(date,year,month,days,hours,minitues,0);
	}
	/**
	 * 当前日期 +year+month+days+hours+minitues+seconds后的日期
	 * @param date
	 * @param year
	 * @param month
	 * @param days
	 * @return
	 */
	public static Date getAddDate(Date date,int year,int month,int days,int hours,int minitues,int seconds) {
	      Calendar cale = Calendar.getInstance();  
	      cale.setTime(date);
	      cale.add(Calendar.YEAR, year);
	      cale.add(Calendar.MONTH, month);  
	      cale.add(Calendar.DAY_OF_MONTH, days); 
	      cale.add(Calendar.HOUR_OF_DAY, hours);
	      cale.add(Calendar.MINUTE, minitues);
	      cale.add(Calendar.SECOND, seconds);
	      return cale.getTime();
	}
	
}

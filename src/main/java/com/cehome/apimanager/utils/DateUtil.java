/**
 * <h3>Class description</h3>
 * <h4>鏃ユ湡澶勭悊绫?/h4>
 *
 * <h4>Special Notes</h4>
 *
 *
 * @ver 0.1
 * @author lihengjun
 * 2008-8-11
 */
package com.cehome.apimanager.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil
{
	/**
	 * 一天中的天数
	 */
	public static long millionSecondsOfDay = 86400000;
	/**
	 * 一天中的小时
	 */
	public static long millionSecondsOfHour = 3600000;

	/**日期格式**/
	public static final String FORMATTER_OF_DATE = "yyyy-MM-dd";
	/**时间格式**/
	public static final String FORMATTER_OF_TIME = "HH:mm:ss";
	/**日期时间格式**/
	public static final String FORMATTER_OF_DATE_AND_TIME = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 今天
	 * @return 今天date
	 */
	public static Date getToday() {
		return new Date();

	}
	/**
	 * 得到两个日期之间的天数,两头不算,取出数据后，可以根据需要再加
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getDay(Date date1, Date date2) {
		Long d2 = date2.getTime();
		Long d1 = date1.getTime();
		return (int) ((d2 - d1) / millionSecondsOfDay);
	}

	/**
	 *
	 * 计算2个时间之间的相差小时和分钟数，返回XX小时XX分
	 * 注意date1格式为yyyy-MM-dd
	 * 注意date2格式为yyyy-MM-dd
	 * 注意time1格式为HH:mm
	 * 注意time2格式为HH:mm
	 * date1<date2
	 * @param date1
	 * @param time1
	 * @param date2
	 * @param time2
	 * @return
	 */
	public static String getHourAndMinute(String date1,String time1, String date2,String time2) {
		Date dateTime1_tmp = DateUtil.parse(date1+" "+time1, "yyyy-MM-dd HH:mm");
		Date dateTime2_tmp = DateUtil.parse(date2+" "+time2, "yyyy-MM-dd HH:mm");
		Long d2 = dateTime2_tmp.getTime();
		Long d1 = dateTime1_tmp.getTime();
		int hours = (int) ((d2 - d1) / millionSecondsOfHour);
		int mins = (int) ((d2 - d1) % millionSecondsOfHour);
		mins=mins/60000;
		return String.valueOf(hours)+"小时"+String.valueOf(mins)+"分";
	}
	/**
	 * 计算日期加天数
	 *
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDay(Date date , int days){
		Calendar c = new GregorianCalendar();
		c.setTime( date );
		c.add(Calendar.DAY_OF_MONTH,days);
		return c.getTime();
	}
	public static Date addMinutes(Date date , int minutes){
		Calendar c = new GregorianCalendar();
		c.setTime( date );
		c.add(Calendar.MINUTE,minutes);
		return c.getTime();
	}

	/**
	 * 根据指定日期格式格式化日期
	 *
	 * @param date
	 * @param formater
	 * @return
	 */
	public static String format(Date date, String formater){
		if(date==null)
			return null;

		SimpleDateFormat sdf = new SimpleDateFormat( formater );
		return sdf.format(date);
	}

	/**
	 * 格式化日期
	 *
	 * @param date
	 * @param formater
	 * @return
	 */
	public static Date parse(String date, String formater) {
		if(date==null||"".equals(date))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(formater);
		Date result = null;
		try {
			result = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据日期取出是星期几
	 *
	 * @param date
	 *            Date
	 * @return int 返回1-7
	 */
	public static int getWeekOfDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return (calendar.get(Calendar.DAY_OF_WEEK) - 1) == 0 ? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	public static String getWeekTextOfDate(Date date){
		String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六" };
		int t=getWeekOfDate(date);
		if(t==7) t=0;
		return dayNames[t];
	}

	public static String getWeekTextOfDate(String strDate, String format){
		String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六" };
		int t=getWeekOfDate( parse(strDate, format) );
		if(t==7) t=0;
		return dayNames[t];
	}

	/**
	 * format "yyyy-MM-dd HH:mm:ss"
	 */
	public static String dateToString(Date date,String format) {
		if (date == null)
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date).trim();

	}

}

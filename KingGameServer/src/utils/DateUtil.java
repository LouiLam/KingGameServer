package utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
	public static String getDateByTimestamp(int myqslTimestamp) {
		Timestamp ts = new Timestamp(myqslTimestamp * 1000l);
		return ts.toString();
	}
	public static String getDateByTimestamp(long Timestamp) {
		Timestamp ts = new Timestamp(Timestamp);
		return ts.toString();
	}
	public static String getCurDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		return df.format(ts);
	}
	public static String getCurDateFile() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		return df.format(ts);
	}
	public static void main(String[] args) {
		
		System.out.println(getDateByTimestamp(1398412774) );
//		System.out.println(getSecondsBetween(getTimesnight(),System.currentTimeMillis())%(1440*60));
		
	}
	/**
	 * 天数差值
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getDaysBetween(long startDate, long endDate) {
		
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTimeInMillis(startDate);
		int fromDay=fromCalendar.get(Calendar.DAY_OF_YEAR);
		// fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
		// fromCalendar.set(Calendar.MINUTE, 0);
		// fromCalendar.set(Calendar.SECOND, 0);
		// fromCalendar.set(Calendar.MILLISECOND, 0);
		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTimeInMillis(endDate);
		int toDay=toCalendar.get(Calendar.DAY_OF_YEAR);
		return (toDay -fromDay);
	

	}
	/**
	 * 天数差值
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long _getDaysBetween(long startDate, long endDate) {
	
			Calendar fromCalendar = Calendar.getInstance();
			fromCalendar.setTimeInMillis(startDate);
			// fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
			// fromCalendar.set(Calendar.MINUTE, 0);
			// fromCalendar.set(Calendar.SECOND, 0);
			// fromCalendar.set(Calendar.MILLISECOND, 0);

			Calendar toCalendar = Calendar.getInstance();
			toCalendar.setTimeInMillis(endDate);
			
			return (toCalendar.getTimeInMillis() - fromCalendar
					.getTimeInMillis()) / (1000 * 60 * 60 * 24);
		

	}
	//获得当天24点时间  (单位小时) 指定时间表示间隔3天
	public static long getTimesnight(){ 
//		2014-04-13 23:59:59  cal.set(2014, 3, 13);
		Calendar cal = Calendar.getInstance(); 
		cal.set(2014, 3, 13);
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.SECOND, 0); 
		cal.set(Calendar.MINUTE, 0); 
		cal.set(Calendar.MILLISECOND, 0); 
		return cal.getTimeInMillis(); 
		} 
	/**
	 * 分钟差值
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getMinutesBetween(long startDate, long endDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTimeInMillis(startDate);
		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTimeInMillis(endDate);
		return (toCalendar.getTimeInMillis() - fromCalendar
				.getTimeInMillis()) / (1000 * 60 );
	
	}
	/**
	 * 秒钟差值
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getSecondsBetween(long startDate, long endDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTimeInMillis(startDate);
		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTimeInMillis(endDate);
		return (toCalendar.getTimeInMillis() - fromCalendar
				.getTimeInMillis()) / (1000 );
	
	}
	/**
	 * 小时差值
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getHoursBetween(long startDate, long endDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTimeInMillis(startDate);
		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTimeInMillis(endDate);
		return (toCalendar.getTimeInMillis() - fromCalendar
				.getTimeInMillis()) / (1000 * 60*60 );
	}
}

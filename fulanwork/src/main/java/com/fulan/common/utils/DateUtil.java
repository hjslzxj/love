package com.fulan.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class DateUtil {

	
	public static String YEAR = "year";
	public static String MONTH = "month";
	public static String DAY = "day";
	public static String HOUR = "hour";
	public static String MINUTE = "minute";
	public static String SECOND = "second";
	public static String MILL_SECOND = "mill-second";
	
	/**
	 *  两个日期比较
	 * 
	 * @date 2016年2月18日 下午6:04:04
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareDate(Date date1, Date date2) {
		if(date1 == null || date2 == null){
			return false;
		}
		return date1.getTime() > date2.getTime() ? true : false;
	}
	
	/**
	 * 两个时间相差的秒数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long dateSubDate(Date date1, Date date2) {
		if(date1 == null || date2 == null){
			return 0l;
		}
		return (date1.getTime() - date2.getTime())/1000;
	}
	

	/**
	 * 两个时间相差的天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long dateSubDateDay(Date date1, Date date2) {
		if(date1 == null || date2 == null){
			return 0l;
		}
		return (date1.getTime() - date2.getTime())/(1000*3600*24);
	}
	
	/**
	 * 把某一天时间，转换成一天开始的时间
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date strToDayStartDate(String dateString) {
		if (dateString == null || "".equals(dateString)) {
			return null;
		}
		try {
			String date = DateUtil.formatDate(DateUtil.parseDate(dateString),
					"yyyy-MM-dd");
			date = date + " 00:00:00";
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			return dateFormat.parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 把某一天时间，转换成一天开始的时间
	 * 
	 * @param dateString
	 * @return
	 */
	public static String strToDayStartStr(String dateString) {
		if (dateString == null || "".equals(dateString)) {
			return null;
		}
		try {
			String date = DateUtil.formatDate(DateUtil.parseDate(dateString),
					"yyyy-MM-dd");
			date = date + " 00:00:00";
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 把某一天时间，转换成一天结束的时间
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date strToDayEndDate(String dateString) {
		if (dateString == null || "".equals(dateString)) {
			return null;
		}
		try {
			String date = DateUtil.formatDate(DateUtil.parseDate(dateString),
					"yyyy-MM-dd");
			date = date + " 23:59:59";
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			return dateFormat.parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 把某一天时间，转换成一天结束的时间
	 * 
	 * @param dateString
	 * @return
	 */
	public static String strToDayEndStr(String dateString) {
		if (dateString == null || "".equals(dateString)) {
			return null;
		}
		try {
			String date = DateUtil.formatDate(DateUtil.parseDate(dateString),
					"yyyy-MM-dd");
			date = date + " 23:59:59";
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	public static String dateToStr(Date date) {
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = dateFormat.format(date);
			return dateStr;
		}
		return null;
	}

	public static String customDateToStr(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			String dateStr = dateFormat.format(date);
			return dateStr;
		}
		return null;
	}

	public static Date getStandardDate(String source, String pattern) {
		if (source == null || source.trim().length() == 0)
			return null;
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		try {
			return sf.parse(source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期相加
	 * 
	 * @param date 日期
	 * @param day 天数
	 * @return 返回相加后的日期
	 */
	public static java.util.Date addDate(java.util.Date date, int day) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}

	public static long getMillis(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	public static String getDateStr() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		String dateStr = dateFormat.format(date);
		return dateStr;
	}

	public static String getTimeStr() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("hhmmss");
		String dateStr = dateFormat.format(date);
		return dateStr;
	}

	public static Date getSysDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * 获取当月第一天的日期
	 */
	public static Date getFirstDateOfMonth() {
		Calendar ca = Calendar.getInstance();
		ca.setTime(getSysDate()); // someDate 为你要获取的那个月的时间
		ca.set(Calendar.DAY_OF_MONTH, 1);
		return ca.getTime();
	}

	public static Date preMonthFirstDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return strToDayStartDate(sdf.format(cal.getTime()));
	}
	
	/**
	 * 根据日期得到日期的年份
	 * 
	 * @param date
	 * @return int
	 * @author gaojian
	 * @date 2-13-9-14
	 */
	public static int getYearTimeOfDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 根据日期得到月份
	 * 
	 * @param date
	 * @return
	 * @author gaojian
	 * @date 2013-9-14
	 */
	public static int getMonthTimeOfDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH);
	}

	/**
	 * 
	 * 格式化日期类为yyyy-MM-dd格式日期类 
	 * 
	 * @param datetime 需要转化的日期
	 * @return 转化后的日期
	 */
	public static Date parseDate(Date datetime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		if (datetime == null) {
			return null;
		} else {
			try {
				return formatter.parse(formatter.format(datetime));
			} catch (ParseException e) {
				return null;
			}
		}
	}

	/**
	 * 
	 * 2012-8-7 得到当前时间的格式化字符串
	 * 
	 * @param type 日期格式
	 * @return 指定日期格式的当前时间的字符串
	 */
	public static String getFormatDate(String type) {
		return (formatDate(now(), type));
	}

	/**
	 * 
	 * 2012-8-7 得到当前时间的格式化字符串
	 * 
	 * @return yyyy-MM-dd HH:mm:ss格式的当前时间的字符串
	 */
	public static String getDefaultDate() {
		return (formatDate(now(), "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 
	 * 2012-8-7 得到当前时间的格式化字符串
	 * 
	 * @return yyyy-MM-dd格式的当前时间的字符串
	 */
	public static String getFormatDate() {
		return (formatDate(now(), "yyyy-MM-dd"));
	}

	/**
	 * 
	 * 2012-8-7 得到当前时间的格式化字符串
	 * 
	 * @return yyyyMM格式的当前时间的字符串
	 */
	public static String getFormatDate1() {
		return (formatDate(now(), "yyyyMM"));
	}

	/**
	 * 
	 * 2012-8-7 根据传入的日期类型格式化日期
	 * 
	 * @param date
	 *            需要格式化的日期
	 * @param pattern
	 *            日期格式
	 * @return 格式化后的日期字符串
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null)
			return "";
		if (pattern == null)
			pattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return (sdf.format(date));
	}

	/**
	 * 
	 * 2012-8-7 根据传入的日期类型格式化日期字符串
	 * 
	 * @param dateStr
	 *            需要格式化的日期字符串
	 * @param pattern
	 *            日期格式
	 * @return 格式化后的日期
	 */
	public static Date formatDate(String dateStr, String pattern) {
		if (pattern == null || "".equals(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		Date d = null;
		try {
			if (dateStr == null) {
				d = null;
			} else {
				d = sdf.parse(dateStr);
			}
		} catch (ParseException e) {
			return null;
		}
		return d;
	}

	/**
	 * 
	 * 2012-8-7 将传入的日期格式化为yyyy-MM-dd HH:mm:ss格式字符串
	 * 
	 * @param date
	 *            需要格式化的日期
	 * @return 格式化后的日期字符串
	 */
	public static String formatDateTime(Date date) {
		return (formatDate(date, "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 
	 * 2012-8-7 将当前日期格式化为yyyy-MM-dd HH:mm:ss格式字符串
	 * 
	 * @return 格式化后的日期字符串
	 */
	public static String formatDateTime() {
		return (formatDate(now(), "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 
	 * 2012-8-7 返回当前日期
	 * 
	 * @return 当前日期
	 */
	public static Date now() {
		return (new Date());
	}

	/**
	 * 
	 * 2012-8-7 将字符串转换成日期类(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param datetime
	 *            需要转化的日期字符串
	 * @return 转化后的日期
	 */
	public static Date parseDateTime(String datetime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if ((datetime == null) || (datetime.equals(""))) {
			return null;
		} else {
			try {
				return formatter.parse(datetime);
			} catch (ParseException e) {
				return parseDate(datetime);
			}
		}
	}

	/**
	 * 
	 * 2012-8-7 将字符串转换成日期类(yyyy-MM-dd)
	 * 
	 * @param date
	 *            需要转化的日期字符串
	 * @return 转化后的日期
	 */
	public static Date parseDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		if ((date == null) || (date.equals(""))) {
			return null;
		} else {
			try {
				return formatter.parse(date);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	/**
	 * 
	 * 2012-8-7 给时间加上或减去指定毫秒，秒，分，时，天、月或年等，返回变动后的时间
	 * 
	 * @param date
	 *            要加减前的时间，如果不传，则为当前日期
	 * @param field
	 *            时间域，有Calendar.MILLISECOND,Calendar.SECOND,Calendar.MINUTE,<br>
	 *            Calendar.HOUR,Calendar.DATE, Calendar.MONTH,Calendar.YEAR
	 * @param amount
	 *            按指定时间域加减的时间数量，正数为加，负数为减
	 * @return 变动后的时间
	 */
	public static Date add(Date date, int field, int amount) {
		if (date == null) {
			date = new Date();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);

		return cal.getTime();
	}

	/**
	 * 
	 * 2012-8-7 返回所传时间加上微秒的时间结果
	 * 
	 * @param date
	 *            要加减前的时间，如果不传，则为当前日期
	 * @param amount
	 *            按指定时间域加减的时间数量，正数为加，负数为减
	 * @return
	 */
	public static Date addMilliSecond(Date date, int amount) {
		return add(date, Calendar.MILLISECOND, amount);
	}

	/**
	 * 
	 * 2012-8-7 返回所传时间加上秒的时间结果
	 * 
	 * @param date
	 *            要加减前的时间，如果不传，则为当前日期
	 * @param amount
	 *            按指定时间域加减的时间数量，正数为加，负数为减
	 * @return
	 */
	public static Date addSecond(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	/**
	 * 
	 * 2012-8-7 返回所传时间加上分钟的时间结果
	 * 
	 * @param date
	 *            要加减前的时间，如果不传，则为当前日期
	 * @param amount
	 *            按指定时间域加减的时间数量，正数为加，负数为减
	 * @return
	 */
	public static Date addMiunte(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}

	/**
	 * 
	 * 2012-8-7 返回所传时间加上小时的时间结果
	 * 
	 * @param date
	 *            要加减前的时间，如果不传，则为当前日期
	 * @param amount
	 *            按指定时间域加减的时间数量，正数为加，负数为减
	 * @return
	 */
	public static Date addHour(Date date, int amount) {
		return add(date, Calendar.HOUR, amount);
	}

	/**
	 * 
	 * 2012-8-7 返回所传时间加上天的时间结果
	 * 
	 * @param date
	 *            要加减前的时间，如果不传，则为当前日期
	 * @param amount
	 *            按指定时间域加减的时间数量，正数为加，负数为减
	 * @return
	 */
	public static Date addDay(Date date, int amount) {
		return add(date, Calendar.DATE, amount);
	}

	/**
	 * 
	 * 2012-8-7 返回所传时间加上月的时间结果
	 * 
	 * @param date
	 *            要加减前的时间，如果不传，则为当前日期
	 * @param amount
	 *            按指定时间域加减的时间数量，正数为加，负数为减
	 * @return
	 */
	public static Date addMonth(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	/**
	 * 
	 * 2012-8-7 返回所传时间加上年的时间结果
	 * 
	 * @param date
	 *            要加减前的时间，如果不传，则为当前日期
	 * @param amount
	 *            按指定时间域加减的时间数量，正数为加，负数为减
	 * @return
	 */
	public static Date addYear(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}

	/**
	 * 
	 * 2012-8-7 返回格式化为yyyy-MM-dd HH:mm:ss的字符串的当前日期
	 * 
	 * @return 格式化后的日期
	 */
	public static Date getDateTime() {
		return parseDateTime(formatDate(now(), "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 
	 * 2012-8-7 返回格式化的字符串转化成的日期类
	 * 
	 * @param d
	 *            需要转化的日期
	 * @param pattern
	 *            日期格式
	 * @return 格式化后的日期
	 */
	public static Date getDateTime(Date d, String pattern) {
		if (pattern == null) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		return parseDateTime(formatDate(d, pattern));
	}
	/**
	 * 
	 * 2012-8-7 判断所传入的字符串是否是数字，不是：true；是：false
	 * 
	 * @param num
	 *            需要判断的字符串
	 * @return 是否是数字
	 */
	public static boolean isNaN(String num) {
		String number = "1234567890";
		for (int i = 0; i < num.length(); i++) {
			if (number.indexOf(num.charAt(i)) == -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 2012-8-7 验证yyyyMMddHHmmss型字符串是否为合法的时间
	 * @param time 需要验证的日期字符
	 * @return 验证结果
	 */
	public static boolean isTrueTime(String time) {

		boolean b = true;

		// 判断是否全为数字
		if (isNaN(time))
			return false;

		// 根据位数做不同的验证
		int length = time.length();
		if (length == 12) {// 不包含秒
			b = isTrueTimeExceptSecond(time);
		} else if (length == 14) {
			boolean bSecond = true;
			int second = Integer.parseInt(time.substring(12, 14));// 获取秒
			if (second < 0 || second > 59) {// 秒超出范围
				bSecond = false;
			}
			b = isTrueTimeExceptSecond(time) && bSecond;
		} else {
			b = false;
		}
		return b;
	}

	/**
	 * 
	 * 2012-8-7 验证yyyyMMddHHmm型字符串是否为合法的时间
	 * 
	 * @param time
	 *            需要验证的日期字符
	 * @return 验证结果
	 */
	private static boolean isTrueTimeExceptSecond(String time) {

		// 年月日 时 分
		int year = Integer.parseInt(time.substring(0, 4));
		int month = Integer.parseInt(time.substring(4, 6));
		int day = Integer.parseInt(time.substring(6, 8));
		int hour = Integer.parseInt(time.substring(8, 10));
		int minute = Integer.parseInt(time.substring(10, 12));

		if (month < 1 || month > 12 || day < 1 || day > 31) {// 月份和日期超出范围
			return false;
		}

		if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {// 时分超出范围
			return false;
		}

		if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {// 闰年
			return checkDay(true, month, day);
		} else {
			return checkDay(false, month, day);
		}

	}

	/**
	 * 
	 * 2012-8-7 验证小月天数是否超出范围
	 * 
	 * @param isLeapYear
	 *            是否为闰年
	 * @param month
	 *            月份
	 * @param day
	 *            日期
	 * @return
	 */
	private static boolean checkDay(boolean isLeapYear, int month, int day) {
		boolean b = true;
		int count = 28;
		if (isLeapYear == true) {
			count = 29;
		}
		switch (month) {
		case 2:
			if (day > count) {
				b = false;
			}
			break;
		case 4:
			if (day > 30) {
				b = false;
			}
			break;
		case 6:
			if (day > 30) {
				b = false;
			}
			break;
		case 9:
			if (day > 30) {
				b = false;
			}
			break;
		case 11:
			if (day > 30) {
				b = false;
			}
			break;
		default:
			break;
		}
		return b;
	}

	/**
	 * 计算日期时间之间的差值， date1得时间必须大于date2的时间
	 * 
	 * @param date1
	 * @param date2
	 * @return {@link java.util.Map} Map的键分别为, day(天),
	 *         hour(小时),minute(分钟)和second(秒)。
	 */
	public static Map<String, Long> timeDifference(final Date date1,
			final Date date2) {
		if (date1 == null || date2 == null) {
			throw new NullPointerException("date1 and date2 can't null");
		}
		long mim1 = date1.getTime();
		long mim2 = date2.getTime();
		if (mim1 < mim2) {
			mim1 = mim2;//new Date().getTime();
		}
		long m = (mim1 - mim2 + 1) / 1000l;
		long mday = 24 * 3600;
		final Map<String, Long> map = new HashMap<String, Long>();
		map.put(DAY, m / mday);
		m = m % mday;
		map.put(HOUR, (m) / 3600);
		map.put(MINUTE, (m % 3600) / 60);
		map.put(SECOND, (m % 3600 % 60));
		return map;
	}
	
	/**
	 * 计算日期时间之间的差值， date1得时间可以小于date2的时间
	 * @author 禹研超
	 * @date 2014-8-28 上午10:46:22
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Map<String, Long> timeDifference2(final Date date1,
			final Date date2) {
		if (date1 == null || date2 == null) {
			throw new NullPointerException("date1 and date2 can't null");
		}
		long mim1 = date1.getTime();
		long mim2 = date2.getTime();
		long m = (mim1 - mim2 + 1) / 1000l;
		long mday = 24 * 3600;
		final Map<String, Long> map = new HashMap<String, Long>();
		map.put(DAY, m / mday);
		m = m % mday;
		map.put(HOUR, (m) / 3600);
		map.put(MINUTE, (m % 3600) / 60);
		map.put(SECOND, (m % 3600 % 60));
		return map;
	}
	
	/**
	 * 得到不同的类型
	 * @author 禹研超
	 * @date 2013-12-31 上午9:42:33
	 * @param date1
	 * @param date2
	 * @param diff
	 * @return
	 */
	public static Long timeDifference(final Date date1,
			final Date date2, String diff) {
		final Map<String, Long> map = timeDifference(date1, date2);
		return map.get(diff);
	}

	public static Map<String, Integer> compareTo(final Date date1,
			final Date date2) {
		if (date1 == null || date2 == null) {
			return null;
		}
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		long time = Math.max(time1, time2) - Math.min(time1, time2);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put(YEAR,
				(calendar.get(Calendar.YEAR) - 1970) > 0 ? (calendar
						.get(Calendar.YEAR) - 1970) : 0);
		map.put(MONTH,
				(calendar.get(Calendar.MONTH) - 1) > 0 ? (calendar
						.get(Calendar.MONTH) - 1) : 0);
		map.put(DAY,
				(calendar.get(Calendar.DAY_OF_MONTH) - 1) > 0 ? (calendar
						.get(Calendar.DAY_OF_MONTH) - 1) : 0);
		map.put(HOUR,
				(calendar.get(Calendar.HOUR_OF_DAY) - 8) > 0 ? (calendar
						.get(Calendar.HOUR_OF_DAY) - 8) : 0);
		map.put(MINUTE,
				calendar.get(Calendar.MINUTE) > 0 ? calendar
						.get(Calendar.MINUTE) : 0);
		map.put(SECOND,
				calendar.get(Calendar.SECOND) > 0 ? calendar
						.get(Calendar.SECOND) : 0);
		map.put(MILL_SECOND,
				calendar.get(Calendar.MILLISECOND) > 0 ? calendar
						.get(Calendar.MILLISECOND) : 0);
		return map;
	}
	
	/**
	 * 得到时间差异
	 * @author 禹研超
	 * @date 2013-12-13 下午5:37:40
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String getDiffTimeDesc(Date startDate, Date endDate) {
		Map<String, Integer> map = compareTo(endDate, startDate);
		StringBuffer desc = new StringBuffer();
		if (map.get(DAY) > 0) {
			desc.append(map.get(DAY) + "天");
		}
		if (map.get(HOUR) > 0) {
			desc.append(map.get(HOUR) + "小时");
		}
		if (map.get(MINUTE) > 0) {
			desc.append(map.get(MINUTE) + "分钟");
		}
		if (map.get(SECOND) > 0) {
			desc.append(map.get(SECOND) + "秒");
		}
		if (map.get(MILL_SECOND) > 0) {
			desc.append(map.get(MILL_SECOND) + "毫秒");
		}
		return desc.toString();
	}

	/**
	 * 时间差得到时间差(小时)
	 * @author 禹研超
	 * @date 2015-1-17 上午12:59:32
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getDateDiffHours(Date startTime, Date endTime) {
		Map<String, Long> dateMap = timeDifference2(startTime, endTime);
		long days = dateMap.get(DAY);
		int hours = (int) (days * 24 + dateMap.get(HOUR));
		return hours;
	}

	public static int getDateDiffMinutes(Date startTime, Date endTime) {
		Map<String, Long> dateMap = timeDifference2(startTime, endTime);
		long days = dateMap.get(DAY);
		int hours = (int) ((days * 24 + dateMap.get(HOUR)) * 60 + dateMap.get(MINUTE));
		return hours;
	}

	public static int getDateDiffSecond(Date startTime, Date endTime) {
		if(startTime == null || endTime == null){
			return 0;
		}
		long time = endTime.getTime() - startTime.getTime();
		return  (int) (time / 1000l);
	}
	
	/**
	 * 获取日期是周几
	 * @param date
	 * @return
	 */
	public static int getWeekOfDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if(week <= 0){
			return 7;
		}
		return week;
	}
	
	public static String [] week = {"","星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
	
	/**
	 * 判断时间，是否在指定时间段里
	 * @param date
	 * @param canUseHour
	 * @return
	 */
	/*public static boolean compareTime(Date date,String canUseHour){
		if(Utils.isEmpty(date) || Utils.isEmpty(canUseHour)){
			return false;
		}
		String dateStr = formatDate(date, "yyyy-MM-dd");
		String[] hour = canUseHour.split("\\|");
		if(Utils.isEmpty(hour) || hour.length != 2){
			return false;
		}
		Date startTime = parseDateTime(dateStr+" "+hour[0]+":00");
		Date endTime = parseDateTime(dateStr+" "+hour[1]+":00");
		if(compareDate(date,startTime) && compareDate(endTime,date)){
			return true;
		}
		return false;
	}*/
	
	/**
	 * 离今天结束还有多少秒
	 * @return
	 */
	public static long getTodaySecond(){
		Date now = new Date();
		Date date = strToDayEndDate(formatDate(now, null));
		long time = date.getTime() - now.getTime();
		return (time/1000);
	}
}

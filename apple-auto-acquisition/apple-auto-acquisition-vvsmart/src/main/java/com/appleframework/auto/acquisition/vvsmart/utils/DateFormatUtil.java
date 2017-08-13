package com.appleframework.auto.acquisition.vvsmart.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class DateFormatUtil {
	
	protected final static Logger logger = Logger.getLogger(DateFormatUtil.class);

	public static String pattern19  = "yyyy-MM-dd HH:mm:ss";
	public static String pattern14  = "yyyyMMddHHmmss";
	public static String pattern10  = "yyyy-MM-dd";
	public static String pattern8   = "yyyyMMdd";
	public static String pattern6   = "HHmmss";
	
	private static final Map<String, ThreadLocal<SimpleDateFormat>> pool 
		= new HashMap<String, ThreadLocal<SimpleDateFormat>>();
	
	private static final Object lock = new Object();

	public static SimpleDateFormat getDateFormat(String pattern) {
		ThreadLocal<SimpleDateFormat> tl = pool.get(pattern);
		if (tl == null) {
			synchronized (lock) {
				tl = pool.get(pattern);
				if (tl == null) {
					final String p = pattern;
					tl = new ThreadLocal<SimpleDateFormat>() {
						protected synchronized SimpleDateFormat initialValue() {
							return new SimpleDateFormat(p);
						}
					};
					pool.put(p, tl);
				}
			}
		}
		return tl.get();
	}

	public static Date toDate(String dateStr, String pattern) {
		try {
			return getDateFormat(pattern).parse(dateStr);
		} catch (ParseException e) {
			logger.error(e);
		}
		return null;
	}
	
	public static String toString(Date date, String pattern) {
		return getDateFormat(pattern).format(date);
	}
	
	public static String nowString(String pattern) {
		return getDateFormat(pattern).format(new Date());
	}
}


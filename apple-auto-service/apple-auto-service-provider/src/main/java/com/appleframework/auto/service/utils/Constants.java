package com.appleframework.auto.service.utils;

public interface Constants {
	
	/*
	 * type 
	 * 0:google转百度,
	 * 1:google转gps,
	 * 2:百度转google,
	 * 3:百度转gps，
	 * 4：gps转google，
	 * 5：gps转百度
	 */
	public static int MAP_CONVERT_GOOGLE_TO_BAIDU  = 0;
	public static int MAP_CONVERT_GOOGLE_TO_GPS    = 1;
	public static int MAP_CONVERT_BAIDU_TO_GOOGLE  = 2;
	public static int MAP_CONVERT_BAIDU_TO_GPS     = 3;
	public static int MAP_CONVERT_GPS_TO_GOOGLE    = 4;
	public static int MAP_CONVERT_GPS_TO_BAIDU     = 5;
		
	public static String KEY_LATITUDE  = "latitude";
	public static String KEY_LONGITUDE = "longitude";

}

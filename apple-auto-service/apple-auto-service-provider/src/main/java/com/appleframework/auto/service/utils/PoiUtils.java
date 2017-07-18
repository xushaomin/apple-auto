package com.appleframework.auto.service.utils;


import com.appleframework.auto.bean.fence.Point;
import com.appleframework.auto.bean.location.Location;
import com.appleframework.util.map.utility.GpsUtility;

public class PoiUtils {
	
	public static void fixPoi(Location location, int mapType) {
		Double latitude = location.getLatitude();
		Double longitude = location.getLongitude();
		String[] ps = GpsUtility.convert2(latitude, longitude, mapType);
		location.setLatitude(Double.parseDouble(ps[0]));
		location.setLongitude(Double.parseDouble(ps[1]));
	}
	
	public static void fixPoi(Point point, int mapType) {
		Double latitude = point.getLatitude();
		Double longitude = point.getLongitude();
		String[] ps = GpsUtility.convert2(latitude, longitude, mapType);
		point.setLatitude(Double.parseDouble(ps[0]));
		point.setLongitude(Double.parseDouble(ps[1]));
	}

}


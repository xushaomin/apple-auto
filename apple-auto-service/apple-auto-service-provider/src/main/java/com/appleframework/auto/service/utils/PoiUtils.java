package com.appleframework.auto.service.utils;


import java.util.Map;

import com.appleframework.bean.location.Location;
import com.appleframework.util.map.utility.GpsUtility;


public class PoiUtils {

	public static void fixPoi(Map<String, String> map, int mapType) {
		String longitude = map.get(Constants.KEY_LONGITUDE);
		String latitude = map.get(Constants.KEY_LATITUDE);
		
		Double longitudeD = Double.parseDouble(longitude);
		Double latitudeD = Double.parseDouble(latitude);

		if(mapType == Constants.MAP_BAIDU) {
			String[] baidus = GpsUtility.convert2(latitudeD, longitudeD, 5);
			map.put("maplon", baidus[1]);
			map.put("maplat", baidus[0]);
		}
		else if(mapType == Constants.MAP_GOOGLE) {
			String[] googles = GpsUtility.convert2(latitudeD, longitudeD, 4);
			map.put("maplon", googles[1]);
			map.put("maplat", googles[0]);
		}
		else {
			String[] googles = GpsUtility.convert2(latitudeD, longitudeD, 4);
			String[] baidus = GpsUtility.convert2(latitudeD, longitudeD, 5);
			map.put("googlelon", googles[1]);
			map.put("googlelat", googles[0]);

			map.put("baidulon", baidus[1]);
			map.put("baidulat", baidus[0]);
		}
	}
	
	public static void fixPoi(Location location, int mapType) {
		Double latitude = location.getLatitude();
		Double longitude = location.getLongitude();

		if(mapType == Constants.MAP_BAIDU) {
			String[] baidus = GpsUtility.convert2(latitude, longitude, 5);
			location.setLatitude(Double.parseDouble(baidus[0]));
			location.setLongitude(Double.parseDouble(baidus[1]));
		}
		else if(mapType == Constants.MAP_GOOGLE) {
			String[] googles = GpsUtility.convert2(latitude, longitude, 4);
			location.setLatitude(Double.parseDouble(googles[0]));
			location.setLongitude(Double.parseDouble(googles[1]));
		}
		else {
			return;
		}
	}

}


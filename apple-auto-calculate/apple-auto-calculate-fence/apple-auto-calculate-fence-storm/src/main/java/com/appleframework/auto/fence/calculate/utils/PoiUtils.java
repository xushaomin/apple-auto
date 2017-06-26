package com.appleframework.auto.fence.calculate.utils;

import com.appleframework.auto.bean.fence.Point;
import com.appleframework.util.map.utility.GpsUtility;

public class PoiUtils {

	public static void fixPoi(Point point, int mapType) {
		Double latitude = point.getLatitude();
		Double longitude = point.getLongitude();

		if (mapType == Constants.MAP_BAIDU) {
			String[] baidus = GpsUtility.convert2(latitude, longitude, 3);
			point.setLatitude(Double.parseDouble(baidus[0]));
			point.setLongitude(Double.parseDouble(baidus[1]));
		} else if (mapType == Constants.MAP_GOOGLE) {
			String[] googles = GpsUtility.convert2(latitude, longitude, 1);
			point.setLatitude(Double.parseDouble(googles[0]));
			point.setLongitude(Double.parseDouble(googles[1]));
		} else {
			return;
		}
	}

}

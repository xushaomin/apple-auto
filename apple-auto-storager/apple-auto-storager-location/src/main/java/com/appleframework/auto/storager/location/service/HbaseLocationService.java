package com.appleframework.auto.storager.location.service;

import com.appleframework.bean.location.Location;
import com.appleframework.bean.location.LocationProto;

public interface HbaseLocationService {

	public void save(Location location);
	
	public void save(LocationProto.Model model);
	
}

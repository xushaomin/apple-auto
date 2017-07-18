package com.appleframework.auto.service.fence.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.fence.CircleFence;
import com.appleframework.auto.bean.fence.Point;
import com.appleframework.auto.bean.fence.PolygonFence;
import com.appleframework.auto.bean.fence.RectangleFence;
import com.appleframework.auto.entity.fence.FenceEntityWithBLOBs;
import com.appleframework.auto.service.fence.FenceCacheService;
import com.appleframework.auto.service.fence.FenceInfoService;
import com.appleframework.auto.service.utils.ListUtil;

@Service("fenceInfoService")
public class FenceInfoServiceImpl implements FenceInfoService {

	protected final static Logger logger = Logger.getLogger(FenceInfoServiceImpl.class);
	
	@Resource
	private FenceCacheService fenceCacheService;

	public void create(FenceEntityWithBLOBs entity) {
		try {
			if (entity.getFenceType() == 1) {
				CircleFence fence = new CircleFence();
				fence.setId(entity.getId().toString());
				fence.setName(entity.getName());
				Double latitude = Double.parseDouble(entity.getLatitudes());
				Double longitude = Double.parseDouble(entity.getLongitudes());
				Point point = new Point(latitude, longitude);
				fence.setPoint(point);
				fence.setRadius(entity.getRadius());

				fenceCacheService.create(fence);
				
			} else if (entity.getFenceType() == 2 && entity.getLongitudes().length() == 2) {
				RectangleFence fence = new RectangleFence();
				fence.setId(entity.getId().toString());
				fence.setName(entity.getName());

				String latitudes = entity.getLatitudes();
				String longitudes = entity.getLongitudes();

				String[] latitudeArray = latitudes.split(",");
				String[] longitudeArray = longitudes.split(",");

				Point pointA = new Point(latitudeArray[0] + "," + longitudeArray[0]);
				Point pointB = new Point(latitudeArray[1] + "," + longitudeArray[1]);

				fence.setPointA(pointA);
				fence.setPointB(pointB);

				fenceCacheService.create(fence);
			} else {

				PolygonFence fence = new PolygonFence();
				fence.setId(entity.getId().toString());
				fence.setName(entity.getName());

				String latitudes = entity.getLatitudes();
				String longitudes = entity.getLongitudes();

				List<Double> polygonXA = ListUtil.string2DoubleList(longitudes);
				List<Double> polygonYA = ListUtil.string2DoubleList(latitudes);

				fence.setPolygonXA(polygonXA);
				fence.setPolygonYA(polygonYA);

				fenceCacheService.create(fence);
			}
		} catch (Exception e) {
			logger.error(e);
		}

	}

}
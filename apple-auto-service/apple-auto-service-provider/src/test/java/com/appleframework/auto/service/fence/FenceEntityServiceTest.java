package com.appleframework.auto.service.fence;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appleframework.auto.bean.fence.CircleFence;
import com.appleframework.auto.bean.fence.Point;
import com.appleframework.auto.bean.fence.PolygonFence;
import com.appleframework.auto.bean.fence.RectangleFence;
import com.appleframework.auto.entity.fence.FenceEntityWithBLOBs;
import com.appleframework.exception.ServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/spring-*.xml" })
public class FenceEntityServiceTest {

	@Resource
	private FenceInfoService fenceInfoService;
	
	@Resource
	private FenceEntityService fenceEntityService;
	
	@Test
	public void get() {
		try {
			fenceInfoService.clear();
			List<FenceEntityWithBLOBs> list = fenceEntityService.findAll();
			for (FenceEntityWithBLOBs entity : list) {
				System.out.println(entity.toString());
				
				if(entity.getFenceType() == 1) {
					CircleFence fence = new CircleFence();
					fence.setId(entity.getId().toString());
					fence.setName(entity.getName());
					Double latitude = Double.parseDouble(entity.getLatitudes());
					Double longitude = Double.parseDouble(entity.getLongitudes());
					Point point = new Point(latitude, longitude);
					fence.setPoint(point);
					fence.setRadius(entity.getRadius());
					
					fenceInfoService.create(fence);
				}
				else if(entity.getFenceType() == 2) {
					RectangleFence fence = new RectangleFence();
					fence.setId(entity.getId().toString());
					fence.setName(entity.getName());
					
					String latitudes = entity.getLatitudes();
					String longitudes = entity.getLongitudes();
					
					String[] latitudeArray = latitudes.split(",");
					String[] longitudeArray = longitudes.split(",");
					
					Point pointA = null;
					Point pointB = null;
					if(latitudeArray.length == 2) {
						pointA = new Point(latitudeArray[0] + "," + longitudeArray[0]);
						pointB = new Point(latitudeArray[1] + "," + longitudeArray[1]);
					} else if(latitudeArray.length == 4) {
						pointA = new Point(latitudeArray[0] + "," + longitudeArray[0]);
						pointB = new Point(latitudeArray[2] + "," + longitudeArray[2]);
					} else {
						continue;
					}
					
					fence.setPointA(pointA);
					fence.setPointB(pointB);
					
					fenceInfoService.create(fence);
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
					
					fenceInfoService.create(fence);
				}
			}
			System.out.println("------------------------------------");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	

}

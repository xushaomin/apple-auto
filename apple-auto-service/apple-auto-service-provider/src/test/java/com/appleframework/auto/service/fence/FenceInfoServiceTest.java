package com.appleframework.auto.service.fence;

import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appleframework.auto.bean.fence.CircleFence;
import com.appleframework.auto.bean.fence.Point;
import com.appleframework.auto.service.fence.FenceInfoService;
import com.appleframework.auto.service.utils.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/spring-*.xml" })
public class FenceInfoServiceTest {

	@Resource
	private FenceInfoService fenceInfoService;

	@Test
	public void testAddOpinion1() {
		try {
			double longitude = 113.96645;
			double latitude = 22.538192;
			
			CircleFence fence = new CircleFence();
			Point point = new Point();
			/*point.setLatitude(latitude);
			point.setLongitude(longitude);
			PoiUtils.fixPoi(point, Constants.MAP_BAIDU);
			fence.setPoint(point);
			fence.setRadius(200d);
			fence.setId(UUID.randomUUID().toString());
			
			fenceInfoService.create(fence);

			longitude = 113.956497;
			latitude = 22.53866;
			
			fence = new CircleFence();
			point = new Point();
			point.setLatitude(latitude);
			point.setLongitude(longitude);
			PoiUtils.fixPoi(point, Constants.MAP_BAIDU);
			fence.setPoint(point);
			fence.setRadius(200d);
			fence.setId(UUID.randomUUID().toString());
			
			fenceInfoService.create(fence);
			
			longitude = 113.960818;
			latitude = 22.540969;
			
			fence = new CircleFence();
			point = new Point();
			point.setLatitude(latitude);
			point.setLongitude(longitude);
			PoiUtils.fixPoi(point, Constants.MAP_BAIDU);
			fence.setPoint(point);
			fence.setRadius(200d);
			fence.setId(UUID.randomUUID().toString());
			
			fenceInfoService.create(fence);

			longitude = 113.933131;
			latitude = 22.530473;
			
			fence = new CircleFence();
			point = new Point();
			point.setLatitude(latitude);
			point.setLongitude(longitude);
			PoiUtils.fixPoi(point, Constants.MAP_BAIDU);
			fence.setPoint(point);
			fence.setRadius(500d);
			fence.setId(UUID.randomUUID().toString());
			
			fenceInfoService.create(fence);*/
			
			longitude = 113.960557;
			latitude = 22.526613;
			
			fence = new CircleFence();
			point = new Point();
			point.setLatitude(latitude);
			point.setLongitude(longitude);
			PoiUtils.fixPoi(point, Constants.MAP_BAIDU);
			fence.setPoint(point);
			fence.setRadius(500d);
			fence.setId(UUID.randomUUID().toString());
			
			fenceInfoService.create(fence);
			
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package com.appleframework.auto.service.location;

import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appleframework.auto.bean.fence.CircleFence;
import com.appleframework.auto.bean.fence.Point;
import com.appleframework.auto.service.fence.FenceInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/spring-*.xml" })
public class FenceInfoServiceTest {

	@Resource
	private FenceInfoService fenceInfoService;

	@Test
	public void testAddOpinion1() {
		try {
			double latitude = 113.96645;
			double longitude = 22.538192;
			
			CircleFence fence = new CircleFence();
			Point point = new Point();
			point.setLatitude(latitude);
			point.setLongitude(longitude);
			fence.setPoint(point);
			fence.setRadius(100d);
			fence.setId(UUID.randomUUID().toString());
			
			fenceInfoService.create(fence);

			latitude = 113.956497;
			longitude = 22.53866;
			
			fence = new CircleFence();
			point = new Point();
			point.setLatitude(latitude);
			point.setLongitude(longitude);
			fence.setPoint(point);
			fence.setRadius(100d);
			fence.setId(UUID.randomUUID().toString());
			
			fenceInfoService.create(fence);

			
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

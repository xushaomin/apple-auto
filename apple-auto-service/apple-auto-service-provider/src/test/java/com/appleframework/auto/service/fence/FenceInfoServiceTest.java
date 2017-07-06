package com.appleframework.auto.service.fence;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appleframework.auto.bean.fence.CircleFence;
import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.auto.bean.fence.Point;
import com.appleframework.auto.service.utils.Constants;
import com.appleframework.exception.ServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/spring-*.xml" })
public class FenceInfoServiceTest {

	@Resource
	private FenceInfoService fenceInfoService;
	
	@Test
	public void get() {
		try {
			List<Fence> list = fenceInfoService.get();
			for (Fence fence : list) {
				System.out.println(fence.toString());
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void add() {
		try {
			double longitude = 113.980153;
			double latitude = 22.546445;
			
			CircleFence fence = new CircleFence();
			Point point = new Point();
			point.setLatitude(latitude);
			point.setLongitude(longitude);
			PoiUtils.fixPoi(point, Constants.MAP_BAIDU);
			fence.setPoint(point);
			fence.setRadius(200d);
			fence.setId("010");
			
			fenceInfoService.create(fence);
			
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}

package com.appleframework.auto.service.location;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appleframework.auto.bean.location.Location;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/spring-*.xml" })
public class LocationNewestServiceTest {

	@Resource
	private LocationNewestService locationNewestService;
	
	@Test
	public void testAddOpinion1() {
		try {
			String account = "2017061401";
			for (int i = 0; i < 120000; i++) {
				Location location = locationNewestService.newest(account, 1);
				System.out.println(location);
				Thread.sleep(1000);
			}
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

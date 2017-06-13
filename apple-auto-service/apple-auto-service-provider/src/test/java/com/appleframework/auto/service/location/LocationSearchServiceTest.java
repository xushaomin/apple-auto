package com.appleframework.auto.service.location;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appleframework.bean.location.Location;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/spring-*.xml" })
public class LocationSearchServiceTest {

	@Resource
	private LocationSearchService locationSearchService;
	
	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  

	@Test
	public void testAddOpinion1() {
		try {
			String date = "2017-06-13";
			String account = "2017061301";
			long startTime = format.parse(date).getTime();
			long endTime = startTime + 86400000L;
			List<Location> list = locationSearchService.search(account, startTime, endTime, 1);
			for (Location location : list) {
				System.out.println(location);
			}
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package com.appleframework.auto.service.journey;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appleframework.auto.bean.location.Journey;
import com.appleframework.model.page.Pagination;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/spring-*.xml" })
public class JourneySearchServiceTest {

	@Resource
	private JourneySearchService journeySearchService;
		
	@SuppressWarnings("deprecation")
	@Test
	public void get() {
		try {
			Date date = new Date();
			date.setDate(date.getDate() - 1);
			Pagination page = new Pagination(1, 5);
			String account = "xusm";
			
			page = journeySearchService.search(page, account, date.getTime());
			for (Object entity : page.getList()) {
				Journey journey = (Journey) entity;
				System.out.println(journey.toString());
			}
			System.out.println("------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}

package com.appleframework.auto.storager.Journey;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appleframework.auto.bean.location.Journey;
import com.appleframework.auto.storager.journey.key.JourneyRowkey;
import com.appleframework.data.hbase.client.SimpleHbaseClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/spring-*.xml" })
public class HbaseJourneyServiceTest {

	@Resource
	private SimpleHbaseClient locationHbaseDao;
	
	@Test
	public void testAddOpinion1() {
		try {
			String account = "xusm";
			Long startTime = System.currentTimeMillis();
			JourneyRowkey startRowKey = JourneyRowkey.create(account, startTime);
			List<Journey> list = locationHbaseDao.findObjectList(startRowKey, 10000, Journey.class);
			for (Journey journey : list) {
				System.out.println(journey);
			}
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

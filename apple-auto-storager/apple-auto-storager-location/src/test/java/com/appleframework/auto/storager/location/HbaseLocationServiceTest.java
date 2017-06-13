package com.appleframework.auto.storager.location;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appleframework.auto.storager.location.key.LocationRowkey;
import com.appleframework.bean.location.Location;
import com.appleframework.data.hbase.client.SimpleHbaseClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/spring-*.xml" })
public class HbaseLocationServiceTest {

	@Resource
	private SimpleHbaseClient locationHbaseDao;
	
	@Test
	public void testAddOpinion1() {
		try {
			LocationRowkey startRowKey = LocationRowkey.create("00000020170613011497318232000");
			List<Location> list = locationHbaseDao.findObjectList(startRowKey, 10000, Location.class);
			for (Location location : list) {
				System.out.println(location);
			}
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

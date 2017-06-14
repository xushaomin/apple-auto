package com.appleframework.auto.storager.location;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.storager.location.key.LocationRowkey;
import com.appleframework.data.hbase.client.SimpleHbaseClient;
import com.appleframework.jms.core.producer.MessageProducer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/spring-*.xml" })
public class PlaybackLocationServiceTest {

	@Resource
	private SimpleHbaseClient locationHbaseDao;
	
	@Resource
	private MessageProducer messageProducer;
	
	@Test
	public void testAddOpinion1() {
		try {
			LocationRowkey startRowKey = LocationRowkey.create("00000020170614010000000000000");
			List<Location> list = locationHbaseDao.findObjectList(startRowKey, 100000, Location.class);
			for (Location location : list) {
				messageProducer.sendByte(location.getByteArray());
			}
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

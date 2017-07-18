package com.appleframework.auto.service.fence;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appleframework.auto.entity.fence.FenceEntityWithBLOBs;
import com.appleframework.exception.ServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/spring-*.xml" })
public class FenceEntityServiceTest {

	@Resource
	private FenceCacheService fenceCacheService;
	
	@Resource
	private FenceInfoService fenceInfoService;
	
	@Resource
	private FenceEntityService fenceEntityService;
	
	@Test
	public void get() {
		try {
			fenceCacheService.clear();
			List<FenceEntityWithBLOBs> list = fenceEntityService.findAll();
			for (FenceEntityWithBLOBs entity : list) {
				System.out.println(entity.toString());
				fenceInfoService.create(entity);
			}
			System.out.println("------------------------------------");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	

}

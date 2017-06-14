package com.appleframework.auto.service.fence;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.exception.ServiceException;
import com.hazelcast.core.HazelcastInstance;

@Service("fenceInfoService")
public class FenceInfoServiceImpl implements FenceInfoService {

	protected final static Logger logger = Logger.getLogger(FenceInfoServiceImpl.class);

	@Resource
	private HazelcastInstance hazelcastInstance;

	@Override
	public void create(Fence fence) throws ServiceException {
		Set<Fence> fenceSet = hazelcastInstance.getSet("FENCE_INFO");
		fenceSet.add(fence);
	}

}
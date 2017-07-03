package com.appleframework.auto.calculate.fence.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.fence.CircleFence;
import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.auto.bean.fence.RectangleFence;
import com.appleframework.auto.calculate.fence.service.FenceInfoService;
import com.appleframework.cache.core.CacheException;
import com.appleframework.cache.core.utils.SerializeUtility;
import com.appleframework.cache.jedis.factory.PoolFactory;
import com.appleframework.config.core.PropertyConfigurer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class FenceInfoServiceImpl implements FenceInfoService {

	protected final static Logger logger = Logger.getLogger(FenceInfoServiceImpl.class);

	@Resource
	private PoolFactory poolFactory;
	
	@Resource
	private FenceRectangleService fenceRectangleService;
	
	@Resource
	private FenceCircleService fenceCircleService;

	@PostConstruct
	public void init() {
		List<Fence> list = this.get();
		fenceCircleService.init(list);
		fenceRectangleService.init(list);
	}

	public void create(Fence fence) {
		if (fence instanceof CircleFence) {
			CircleFence circleFence = (CircleFence) fence;
			fenceCircleService.create(circleFence);
		} else if(fence instanceof RectangleFence) {
			RectangleFence rectangleFence = (RectangleFence) fence;
			fenceRectangleService.create(rectangleFence);
		}
	}

	public void update(Fence oldFence, Fence newFence) {
		if (oldFence instanceof CircleFence && newFence instanceof CircleFence) {
			CircleFence oldCircleFence = (CircleFence) oldFence;
			CircleFence newCircleFence = (CircleFence) newFence;
			fenceCircleService.update(oldCircleFence, newCircleFence);
		} else if (oldFence instanceof RectangleFence && newFence instanceof RectangleFence) {
			RectangleFence oldRectangleFence = (RectangleFence) oldFence;
			RectangleFence newRectangleFence = (RectangleFence) newFence;
			fenceRectangleService.update(oldRectangleFence, newRectangleFence);
		}
	}

	public void delete(Fence oldFence) {
		if (oldFence instanceof CircleFence) {
			CircleFence oldCircleFence = (CircleFence) oldFence;
			fenceCircleService.delete(oldCircleFence);
		} else if (oldFence instanceof RectangleFence) {
			RectangleFence oldRectangleFence = (RectangleFence) oldFence;
			fenceRectangleService.delete(oldRectangleFence);
		}
	}

	@SuppressWarnings("deprecation")
	public List<Fence> get() {
		List<Fence> list = new ArrayList<Fence>();
		JedisPool jedisPool = poolFactory.getReadPool();
		Jedis jedis = jedisPool.getResource();
		try {
			String keyFence = PropertyConfigurer.getString("redis.fence.map", "KEY_FENCE_MAP");
			byte[] key = keyFence.getBytes();
			Map<byte[], byte[]> map = jedis.hgetAll(key);
			if (null != map && map.size() > 0) {
				for (Map.Entry<byte[], byte[]> entry : map.entrySet()) {
					Fence boValue = (Fence) SerializeUtility.unserialize(entry.getValue());
					list.add(boValue);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CacheException(e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}
		return list;
	}

}

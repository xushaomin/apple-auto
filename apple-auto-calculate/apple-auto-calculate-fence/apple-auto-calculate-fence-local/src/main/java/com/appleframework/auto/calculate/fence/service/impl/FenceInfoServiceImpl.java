package com.appleframework.auto.calculate.fence.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.fence.Fence;
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
	private FenceCacheService fenceCacheService;
	
	@PostConstruct
	public void init() {
		List<Fence> list = this.get();
		fenceCacheService.init(list);
	}

	public void create(Fence newFence) {
		fenceCacheService.create(newFence);
	}

	public void update(Fence oldFence, Fence newFence) {
		fenceCacheService.update(oldFence, newFence);
	}

	public void delete(Fence oldFence) {
		fenceCacheService.delete(oldFence);
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
	
	@SuppressWarnings("deprecation")
	public Fence get(String fenceId) {
		JedisPool jedisPool = poolFactory.getReadPool();
		Jedis jedis = jedisPool.getResource();
		Fence fence = null;
		try {
			String keyFence = PropertyConfigurer.getString("redis.fence.map", "KEY_FENCE_MAP");
			byte[] key = keyFence.getBytes();
			byte[] value = jedis.hget(key, fenceId.getBytes());
			if (null != value) {
				fence = (Fence) SerializeUtility.unserialize(value);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CacheException(e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}
		return fence;
	}

}

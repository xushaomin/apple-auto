package com.appleframework.auto.calculate.notify.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.cache.core.utils.SerializeUtility;
import com.appleframework.cache.jedis.factory.PoolFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service("fenceCacheService")
public class FenceCacheService {

	protected final static Logger logger = Logger.getLogger(FenceCacheService.class);
	
	private final static String KEY_FENCE_MAP   = "KEY_FENCE_MAP";
		
	@Resource
	private PoolFactory poolFactory;
	
	@SuppressWarnings("deprecation")
	public List<Fence> get() {
		List<Fence> list = new ArrayList<Fence>();
		JedisPool jedisPool = poolFactory.getReadPool();
		Jedis jedis = jedisPool.getResource();
		try {
			byte[] key = KEY_FENCE_MAP.getBytes();
			Map<byte[], byte[]> map = jedis.hgetAll(key);
			if (null != map && map.size() > 0) {
				for (Map.Entry<byte[], byte[]> entry : map.entrySet()) {
					Fence boValue = (Fence) SerializeUtility.unserialize(entry.getValue());
					list.add(boValue);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}
		return list;
	}
	
	@SuppressWarnings("deprecation")
	public Fence get(String id) {
		Fence fence = null;
		JedisPool jedisPool = poolFactory.getReadPool();
		Jedis jedis = jedisPool.getResource();
		try {
			byte[] key = KEY_FENCE_MAP.getBytes();
			byte[] field = id.getBytes();
			byte[] value = jedis.hget(key, field);
			if (null != value) {
				fence = (Fence) SerializeUtility.unserialize(value);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}
		return fence;
	}

}
package com.appleframework.auto.service.fence.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.auto.bean.fence.SyncOperate;
import com.appleframework.auto.service.fence.FenceInfoService;
import com.appleframework.cache.core.CacheException;
import com.appleframework.cache.core.utils.SerializeUtility;
import com.appleframework.cache.jedis.factory.PoolFactory;
import com.appleframework.exception.ServiceException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service("fenceInfoService")
public class FenceInfoServiceImpl implements FenceInfoService {

	protected final static Logger logger = Logger.getLogger(FenceInfoServiceImpl.class);
	
	private final static String KEY_FENCE_MAP   = "KEY_FENCE_MAP";
	
	private final static String KEY_FENCE_TOPIC = "KEY_FENCE_TOPIC";
	
	@Resource
	private PoolFactory poolFactory;

	@SuppressWarnings("deprecation")
	@Override
	public void create(Fence fence) throws ServiceException {
		JedisPool jedisPool = poolFactory.getReadPool();
		Jedis jedis = jedisPool.getResource();
		try {
			byte[] key = KEY_FENCE_MAP.getBytes();
			byte[] field = fence.getId().getBytes();
			byte[] newFence = SerializeUtility.serialize(fence);
			List<byte[]> list = jedis.hmget(key, field);
			if (list.size() > 0 && null != list.get(0)) {
				byte[] value = list.get(0);
				Fence oldFence = (Fence) SerializeUtility.unserialize(value);

				Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
				map.put(field, newFence);
				jedis.hmset(key, map);

				// 修改
				SyncOperate operate = new SyncOperate();
				operate.setOperate(2);
				operate.setOldFence(oldFence);
				operate.setNewFence(fence);
				jedis.publish(KEY_FENCE_TOPIC.getBytes(), SerializeUtility.serialize(operate));
			} else {
				Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
				map.put(field, newFence);
				jedis.hmset(key, map);

				// 新增
				SyncOperate operate = new SyncOperate();
				operate.setOperate(1);
				operate.setNewFence(fence);
				jedis.publish(KEY_FENCE_TOPIC.getBytes(), SerializeUtility.serialize(operate));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CacheException(e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	@Override
	public void update(Fence fence) throws ServiceException {
		this.create(fence);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void delete(String id) throws ServiceException {
		JedisPool jedisPool = poolFactory.getReadPool();
		Jedis jedis = jedisPool.getResource();
		try {
			byte[] key = KEY_FENCE_MAP.getBytes();
			byte[] field = id.getBytes();
			List<byte[]> list = jedis.hmget(key, field);
			if (list.size() > 0) {
				byte[] value = list.get(0);
				Fence oldFence = (Fence) SerializeUtility.unserialize(value);

				jedis.hdel(key, field);
				// 删除
				SyncOperate operate = new SyncOperate();
				operate.setOperate(3);
				operate.setOldFence(oldFence);
				jedis.publish(KEY_FENCE_TOPIC.getBytes(), SerializeUtility.serialize(operate));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CacheException(e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public List<Fence> get() throws ServiceException {
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
			throw new CacheException(e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}
		return list;
	}

}
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
import com.appleframework.auto.calculate.fence.service.FenceInfoService;
import com.appleframework.cache.core.CacheException;
import com.appleframework.cache.core.utils.SerializeUtility;
import com.appleframework.cache.jedis.factory.PoolFactory;
import com.appleframework.structure.kdtree.KDTree;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class FenceInfoServiceImpl implements FenceInfoService {

	protected final static Logger logger = Logger.getLogger(FenceInfoServiceImpl.class);

	private final static String KEY_FENCE_MAP = "KEY_FENCE_MAP";

	@Resource
	private PoolFactory poolFactory;

	private KDTree<String> kdTree;

	@PostConstruct
	public void init() {
		kdTree = new KDTree<String>(4);
		List<Fence> list = this.get();
		for (Fence fence : list) {
			if (fence instanceof CircleFence) {
				CircleFence circleFence = (CircleFence) fence;
				try {
					kdTree.insert(circleFence.allToArray(), circleFence.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void create(Fence fence) {
		if (fence instanceof CircleFence) {
			CircleFence circleFence = (CircleFence) fence;
			try {
				kdTree.insert(circleFence.allToArray(), circleFence.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void update(Fence oldFence, Fence newFence) {
		if (oldFence instanceof CircleFence && newFence instanceof CircleFence) {
			CircleFence oldCircleFence = (CircleFence) oldFence;
			CircleFence newCircleFence = (CircleFence) newFence;
			try {
				kdTree.delete(oldCircleFence.allToArray());
				kdTree.insert(newCircleFence.allToArray(), newCircleFence.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void delete(Fence oldFence) {
		if (oldFence instanceof CircleFence) {
			CircleFence oldCircleFence = (CircleFence) oldFence;
			try {
				kdTree.delete(oldCircleFence.allToArray());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public KDTree<String> getKdTree() {
		return kdTree;
	}

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
			throw new CacheException(e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}
		return list;
	};

}

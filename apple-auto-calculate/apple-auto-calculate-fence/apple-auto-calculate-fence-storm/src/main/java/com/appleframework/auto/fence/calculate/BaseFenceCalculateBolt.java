package com.appleframework.auto.fence.calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.storm.topology.base.BaseRichBolt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.auto.bean.fence.CircleFence;
import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.fence.calculate.factory.RedisFactory;
import com.appleframework.cache.core.CacheException;
import com.appleframework.cache.core.utils.SerializeUtility;
import com.appleframework.cache.jedis.factory.PoolFactory;
import com.appleframework.structure.kdtree.KDTree;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public abstract class BaseFenceCalculateBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(BaseFenceCalculateBolt.class);

	protected static KDTree<String> kdTree;

	private final static String KEY_FENCE_MAP = "KEY_FENCE_MAP";

	public void init(Properties props) {
		if (null == kdTree) {
			PoolFactory poolFactory = RedisFactory.getInstance(props);
			kdTree = new KDTree<String>(4);
			List<Fence> list = this.get(poolFactory);
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
	}

	public Set<String> calculate2(Location location) {
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		// 获取围栏信息
		double[] T = { latitude, longitude, 0, 0 };
		try {
			return kdTree.nearestEuclideanReturnSet(T);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	private List<Fence> get(PoolFactory poolFactory) {
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

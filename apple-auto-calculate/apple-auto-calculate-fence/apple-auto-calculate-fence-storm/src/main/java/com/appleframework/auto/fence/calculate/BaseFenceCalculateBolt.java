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
import com.appleframework.auto.bean.fence.SyncOperate;
import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.fence.calculate.factory.RedisFactory;
import com.appleframework.cache.core.CacheException;
import com.appleframework.cache.core.utils.SerializeUtility;
import com.appleframework.cache.jedis.factory.PoolFactory;
import com.appleframework.jms.core.utils.ByteUtils;
import com.appleframework.structure.kdtree.KDTree;

import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public abstract class BaseFenceCalculateBolt extends BaseRichBolt {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseFenceCalculateBolt.class);

	private static final long serialVersionUID = 1L;

	protected static KDTree<String> kdTree;
	
	protected Properties props;
		
	public void init() {
		// 初始化kdtree
		final PoolFactory poolFactory = RedisFactory.getInstance(props);
		if (null == kdTree) {
			kdTree = new KDTree<String>(4);
			List<Fence> list = this.get(poolFactory);
			for (Fence fence : list) {
				if (fence instanceof CircleFence) {
					CircleFence circleFence = (CircleFence) fence;
					try {
						kdTree.insert(circleFence.allToArray(), circleFence.getId());
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
			}
		}
		
		//redis topic consumer
		final BinaryJedisPubSub pubSub = new BinaryJedisPubSub() {
			@Override
			public void onMessage(byte[] channel, byte[] message) {
				processMessage(ByteUtils.fromByte(message));
			}

			@Override
			public void onPMessage(byte[] pattern, byte[] channel, byte[] message) {
				processMessage(ByteUtils.fromByte(message));
			}

			@Override
			public void punsubscribe() {
				super.punsubscribe();
			}

			@Override
			public void punsubscribe(byte[]... patterns) {
				super.punsubscribe(patterns);
			}
		};
		
		// 初始化redis topic订阅
		new Thread(new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				try {
					String topic = props.getProperty("redis.fence.topic");
					String[] topics = topic.split(",");
					JedisPool jedisPool = poolFactory.getWritePool();
					Jedis jedis = jedisPool.getResource();
					try {
						for (int i = 0; i < topics.length; i++) {
							final String topicc = topics[i];
							jedis.psubscribe(pubSub, topicc.getBytes());
						}
					} catch (Exception e) {
						logger.error(e.getMessage());
					} finally {
						jedisPool.returnResource(jedis);
					}
				} catch (Exception e) {
					logger.error("Subscribing failed.", e);
				}
			}
		}).start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				pubSub.unsubscribe();
			}
		}));
	}

	public Set<String> calculate2(Location location) {
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		// 获取围栏信息
		double[] T = { latitude, longitude, 0, 0 };
		try {
			return kdTree.nearestEuclideanReturnSet(T);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	private List<Fence> get(PoolFactory poolFactory) {
		List<Fence> list = new ArrayList<Fence>();
		JedisPool jedisPool = poolFactory.getReadPool();
		Jedis jedis = jedisPool.getResource();
		try {
			String mapKey = props.getProperty("redis.fence.map");
			byte[] key = mapKey.getBytes();
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
	
	public void create(Fence fence) {
		if (fence instanceof CircleFence) {
			CircleFence circleFence = (CircleFence) fence;
			try {
				kdTree.insert(circleFence.allToArray(), circleFence.getId());
			} catch (Exception e) {
				logger.error(e.getMessage());
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
				logger.error(e.getMessage());
			}
		}
	}

	public void delete(Fence oldFence) {
		if (oldFence instanceof CircleFence) {
			CircleFence oldCircleFence = (CircleFence) oldFence;
			try {
				kdTree.delete(oldCircleFence.allToArray());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	public void processMessage(Object message) {
		if (message instanceof SyncOperate) {
			SyncOperate operate = (SyncOperate) message;
			if(logger.isInfoEnabled()) {
				logger.info("SyncOperate===========" + operate.toString());
			}
			if (operate.getOperate() == SyncOperate.CREATE) {
				this.create(operate.getNewFence());
			} else if (operate.getOperate() == SyncOperate.UPDATE) {
				this.update(operate.getOldFence(), operate.getNewFence());
			} else {
				this.delete(operate.getOldFence());
			}
		}
	}
}

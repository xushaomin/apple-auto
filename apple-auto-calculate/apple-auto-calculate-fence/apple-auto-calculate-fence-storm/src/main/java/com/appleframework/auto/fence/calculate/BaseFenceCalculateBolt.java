package com.appleframework.auto.fence.calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.storm.topology.base.BaseRichBolt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.cache.core.CacheException;
import com.appleframework.cache.core.utils.SerializeUtility;
import com.appleframework.cache.jedis.factory.PoolFactory;
import com.appleframework.jms.core.utils.ByteUtils;

import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public abstract class BaseFenceCalculateBolt extends BaseRichBolt {

	private static final Logger logger = LoggerFactory.getLogger(BaseFenceCalculateBolt.class);

	private static final long serialVersionUID = 1L;

	protected Properties props;

	public void init(final PoolFactory poolFactory) {
		// redis topic consumer
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

	@SuppressWarnings("deprecation")
	public List<Fence> get(PoolFactory poolFactory) {
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

	public abstract void processMessage(Object message);
}

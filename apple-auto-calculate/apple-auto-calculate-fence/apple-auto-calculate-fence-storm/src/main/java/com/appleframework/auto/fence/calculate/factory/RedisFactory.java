package com.appleframework.auto.fence.calculate.factory;

import com.appleframework.auto.fence.calculate.config.StormConfigurer;
import com.appleframework.cache.jedis.config.MasterSlaveServersConfig;
import com.appleframework.cache.jedis.factory.MasterSlavePoolFactory;
import com.appleframework.cache.jedis.factory.PoolFactory;

import redis.clients.jedis.JedisPoolConfig;

public class RedisFactory {

	private static MasterSlavePoolFactory poolFactory;

	private static void init() {

		MasterSlaveServersConfig serverConfig = new MasterSlaveServersConfig();
		serverConfig.setMasterAddressUri(StormConfigurer.getString("redis.masterAddressUri"));
		serverConfig.setSlaveAddressUris(StormConfigurer.getString("redis.slaveAddressUris"));
		serverConfig.setDatabase(StormConfigurer.getInteger("redis.database", 0));

		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(24);
		poolConfig.setMaxIdle(24);
		poolConfig.setMinIdle(0);

		poolFactory = new MasterSlavePoolFactory();
		poolFactory.setMasterPoolConfig(poolConfig);
		poolFactory.setSlavePoolConfig(poolConfig);
		poolFactory.setServerConfig(serverConfig);

		poolFactory.init();
	}

	public static synchronized PoolFactory getInstance() {
		if (poolFactory == null) {
			init();
		}
		return poolFactory;
	}

}

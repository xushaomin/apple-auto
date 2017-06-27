package com.appleframework.auto.fence.calculate.factory;

import java.util.Properties;

import com.appleframework.cache.jedis.config.MasterSlaveServersConfig;
import com.appleframework.cache.jedis.factory.MasterSlavePoolFactory;
import com.appleframework.cache.jedis.factory.PoolFactory;

import redis.clients.jedis.JedisPoolConfig;

public class RedisFactory {

	private static MasterSlavePoolFactory poolFactory;

	private static void init(Properties props) {

		MasterSlaveServersConfig serverConfig = new MasterSlaveServersConfig();
		serverConfig.setMasterAddressUri(props.getProperty("redis.masterAddressUri"));
		serverConfig.setSlaveAddressUris(props.getProperty("redis.slaveAddressUris"));
		serverConfig.setDatabase(Integer.parseInt(props.getProperty("redis.database")));

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

	public static PoolFactory getInstance(Properties props) {
		if(null == poolFactory)
			init(props);
		return poolFactory;
	}

}

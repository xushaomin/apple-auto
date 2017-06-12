package com.appleframework.auto.acquisition.socket.session;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastInstanceFactoryBean implements FactoryBean<HazelcastInstance> {

	private List<String> memberList = new ArrayList<>();

	public void setMembers(String members) {
		String[] memberArray = members.split(",");
		for (String member : memberArray) {
			memberList.add(member);
		}
	}

	@Override
	public HazelcastInstance getObject() throws Exception {
		Config config = new Config();
		config.getNetworkConfig().setPortAutoIncrement(true);
		NetworkConfig network = config.getNetworkConfig();
		JoinConfig join = network.getJoin();
		join.getMulticastConfig().setEnabled(false);
		join.getTcpIpConfig().setMembers(memberList).setEnabled(true);
		HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
		return instance;
	}

	@Override
	public Class<HazelcastInstance> getObjectType() {
		return HazelcastInstance.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}

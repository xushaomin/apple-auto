package com.appleframework.auto.fence.calculate;

import java.util.Properties;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import com.appleframework.boot.AbstractMainContainer;
import com.appleframework.config.core.PropertyConfigurer;

/**
 * @Date Jun 10, 2015
 *
 * @Author dengjie
 *
 * @Note KafkaTopology Task
 */
public class KafkaTopology extends AbstractMainContainer {

	public void doStart() {
		Properties props = PropertyConfigurer.getProps();
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("fence-calculate-spout", new KafkaSpout(props));
		builder.setBolt("fence-calculate-blots", new FenceCalculateBolt(props)).fieldsGrouping("fence-calculate-spout", new Fields("account"));
		builder.setBolt("fence-inout-blots", new FenceInoutBolt(props)).fieldsGrouping("fence-calculate-blots", new Fields("account"));
		builder.setBolt("fence-notify-blots", new FenceNotifyBolt(props)).fieldsGrouping("fence-inout-blots", new Fields("account"));
		
		Config config = new Config();
		config.setDebug(false);

		String args = System.getProperty("application.name");
		//String args = null;
		if (args != null && args.length() > 0) {
			config.setNumWorkers(2);
			try {
				StormSubmitter.submitTopology(args, config, builder.createTopology());
			} catch (AlreadyAliveException e) {
				e.printStackTrace();
			} catch (InvalidTopologyException e) {
				e.printStackTrace();
			} catch (AuthorizationException e) {
				e.printStackTrace();
			}
		} else {
			LocalCluster local = new LocalCluster();
			local.submitTopology("fence-calculate", config, builder.createTopology());
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			local.shutdown();
		}
	}
}

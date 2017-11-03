package com.appleframework.auto.acquisition.vvsmart.consumer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import com.appleframework.auto.acquisition.vvsmart.model.JsonResult;
import com.appleframework.auto.acquisition.vvsmart.model.JsonTrack;
import com.appleframework.auto.acquisition.vvsmart.utils.LocationConversion;
import com.appleframework.auto.bean.location.Location;
import com.appleframework.config.core.PropertyConfigurer;
import com.appleframework.jms.core.producer.MessageProducer3;
import com.google.gson.Gson;

@SuppressWarnings("deprecation")
public class LocationMessageConsumer {

	private static final Log logger = LogFactory.getLog(LocationMessageConsumer.class);

	private MessageProducer3 messageProducer3;

	private String webSocketUrl;

	private WebSocketClient wc;

	private Gson gson = new Gson();

	public void init() throws URISyntaxException {

		wc = new WebSocketClient(new URI(webSocketUrl), new Draft_17()) {

			@Override
			public void onOpen(ServerHandshake handshakedata) {
				logger.info("onOpen---" + handshakedata.getHttpStatusMessage());
			}

			@Override
			public void onMessage(String message) {
				try {
					JsonResult result = gson.fromJson(message, JsonResult.class);
					List<JsonTrack> list = result.getList();
					logger.info("result.time --- " + result.getTime() +"---size---" + list.size());
					for (JsonTrack jsonTrack : list) {
						Location location = LocationConversion.conversion(jsonTrack);
						if(null != location) {
							String topic = PropertyConfigurer.getString("producer.topic.location", "location");
							messageProducer3.sendObject(topic, location.getAccount(), location);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onError(Exception ex) {
			}

			@Override
			public void onClose(int code, String reason, boolean remote) {

			}
		};

		wc.connect();

		while (true) {
			try {
				if (wc.isClosed()) {
					wc.connect();
				}
				wc.send("762");
			} catch (Exception e) {
				logger.error(e);
			}
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				logger.error(e);
			}
		}
	}

	public void setWebSocketUrl(String webSocketUrl) {
		this.webSocketUrl = webSocketUrl;
	}

	public void setMessageProducer3(MessageProducer3 messageProducer3) {
		this.messageProducer3 = messageProducer3;
	}

	public void destory() {
		if (!wc.isClosed()) {
			wc.close();
		}
	}
}

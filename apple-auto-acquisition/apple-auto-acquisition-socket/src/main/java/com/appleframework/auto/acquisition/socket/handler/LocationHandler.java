/**
* 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ***************************************************************************************
 *                                                                                     *
 *                        Website : http://www.appleframework.com                           *
 *                                                                                     *
 ***************************************************************************************
 */
package com.appleframework.auto.acquisition.socket.handler;

import org.apache.log4j.Logger;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.cim.sdk.server.constant.CIMConstant;
import com.appleframework.cim.sdk.server.handler.CIMRequestHandler;
import com.appleframework.cim.sdk.server.model.ReplyBody;
import com.appleframework.cim.sdk.server.model.SentBody;
import com.appleframework.cim.sdk.server.session.AbstractCIMSession;
import com.appleframework.cim.util.ContextHolder;
import com.appleframework.config.core.PropertyConfigurer;
import com.appleframework.jms.core.producer.MessageProducer3;

/**
 * 账号绑定实现
 * 
 */
public class LocationHandler implements CIMRequestHandler {

	protected final Logger logger = Logger.getLogger(LocationHandler.class);
	
	private static String topic = PropertyConfigurer.getString("producer.topic", "location");

	public ReplyBody process(AbstractCIMSession newSession, SentBody message) {

		MessageProducer3 messageProducer3 = ContextHolder.getBean(MessageProducer3.class);

		ReplyBody reply = new ReplyBody();
		reply.setCode(CIMConstant.ReturnCode.CODE_200);
		try {

			String account = message.get("account");
			String latitude = message.get("latitude");
			String longitude = message.get("longitude");
			String altitude = message.get("altitude");

			String speed = message.get("speed");
			String direction = message.get("direction");
			String time = message.get("time");

			Location location = new Location();
			location.setAccount(account);
			location.setDirection(Double.parseDouble(direction));
			location.setLatitude(Double.parseDouble(latitude));
			location.setLongitude(Double.parseDouble(longitude));
			location.setAltitude(Double.parseDouble(altitude));
			location.setSpeed(Double.parseDouble(speed));
			location.setTime(Long.parseLong(time));

			if (logger.isInfoEnabled())
				logger.info(location.toString());

			messageProducer3.sendObject(topic, account, location);
		} catch (Exception e) {
			reply.setCode(CIMConstant.ReturnCode.CODE_500);
		}
		return reply;
	}
}

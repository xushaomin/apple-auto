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
package com.appleframework.auto.bean.location;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 位置对象
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Location implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户
	 */
	private String account;

	/**
	 * 经度
	 */
	private Double latitude;

	/**
	 * 维度
	 */
	private Double longitude;

	/**
	 * 高度
	 */
	private Double altitude;

	/**
	 * 速度
	 */
	private Double speed;

	/**
	 * 方向
	 */
	private Double direction;

	/**
	 * 时间
	 */
	private Long time;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getAltitude() {
		return altitude;
	}

	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Double getDirection() {
		return direction;
	}

	public void setDirection(Double direction) {
		this.direction = direction;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("#Message#").append("\n");
		buffer.append("account:").append(account).append("\n");
		buffer.append("latitude:").append(latitude).append("\n");
		buffer.append("longitude:").append(longitude).append("\n");
		buffer.append("altitude:").append(altitude).append("\n");
		buffer.append("speed:").append(speed).append("\n");
		buffer.append("direction:").append(direction).append("\n");
		buffer.append("time:").append(time).append("\n");
		return buffer.toString();
	}

	public boolean isNotEmpty(String txt) {
		return txt != null && txt.trim().length() != 0;
	}

}
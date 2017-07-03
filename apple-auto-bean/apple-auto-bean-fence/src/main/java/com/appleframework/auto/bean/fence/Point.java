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
package com.appleframework.auto.bean.fence;

import java.io.Serializable;

/**
 * 位置对象
 */
public class Point implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 经度
	 */
	private Double latitude;

	/**
	 * 维度
	 */
	private Double longitude;

	public Point() {
	}

	public Point(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Point(String parameter) {
		String[] parameters = parameter.split(",");
		this.latitude = Double.parseDouble(parameters[0]);
		this.longitude = Double.parseDouble(parameters[1]);;
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
	
	public double[] toArray() {
		double[] point = { latitude, longitude};
		return point;
	}

	@Override
	public String toString() {
		return "Point [latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}
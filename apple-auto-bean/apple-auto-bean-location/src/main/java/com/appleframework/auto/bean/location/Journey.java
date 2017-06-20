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
public class Journey implements Serializable {

	private static final long serialVersionUID = 1L;

	private String account; // 用户
	private Long startTime; // 开始时间
	private Long endTime; // 结束时间
	private Integer totalTime; // 行驶时长 (s)
	private Integer mileage; // 行驶里程 (m)
	private Double oilWear; // 油耗 （ml）
	private Integer idleTime; // 怠速时长 (s)
	private Integer rushUpNO; // 急加速次数
	private Integer rushSlowNO; // 急减速次数
	private Integer rushTurnNO; // 急转弯次数
	private Integer overSpeedNO; // 超速次数

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Integer getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}

	public Integer getMileage() {
		return mileage;
	}

	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}

	public Double getOilWear() {
		return oilWear;
	}

	public void setOilWear(Double oilWear) {
		this.oilWear = oilWear;
	}

	public Integer getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(Integer idleTime) {
		this.idleTime = idleTime;
	}

	public Integer getRushUpNO() {
		return rushUpNO;
	}

	public void setRushUpNO(Integer rushUpNO) {
		this.rushUpNO = rushUpNO;
	}

	public Integer getRushSlowNO() {
		return rushSlowNO;
	}

	public void setRushSlowNO(Integer rushSlowNO) {
		this.rushSlowNO = rushSlowNO;
	}

	public Integer getRushTurnNO() {
		return rushTurnNO;
	}

	public void setRushTurnNO(Integer rushTurnNO) {
		this.rushTurnNO = rushTurnNO;
	}

	public Integer getOverSpeedNO() {
		return overSpeedNO;
	}

	public void setOverSpeedNO(Integer overSpeedNO) {
		this.overSpeedNO = overSpeedNO;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Journey [account=");
		builder.append(account);
		builder.append(", startTime=");
		builder.append(startTime);
		builder.append(", endTime=");
		builder.append(endTime);
		builder.append(", totalTime=");
		builder.append(totalTime);
		builder.append(", mileage=");
		builder.append(mileage);
		builder.append(", oilWear=");
		builder.append(oilWear);
		builder.append(", idleTime=");
		builder.append(idleTime);
		builder.append(", rushUpNO=");
		builder.append(rushUpNO);
		builder.append(", rushSlowNO=");
		builder.append(rushSlowNO);
		builder.append(", rushTurnNO=");
		builder.append(rushTurnNO);
		builder.append(", overSpeedNO=");
		builder.append(overSpeedNO);
		builder.append("]");
		return builder.toString();
	}

}
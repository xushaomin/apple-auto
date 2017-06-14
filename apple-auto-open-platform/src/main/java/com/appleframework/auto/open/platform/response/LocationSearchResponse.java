package com.appleframework.auto.open.platform.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.appleframework.auto.bean.location.Location;

/**
 * <pre>
 * 功能说明：
 * </pre>
 * 
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "locationSearchResponse")
public class LocationSearchResponse {

	private List<Location> list;

	public List<Location> getList() {
		return list;
	}

	public void setList(List<Location> list) {
		this.list = list;
	}

}

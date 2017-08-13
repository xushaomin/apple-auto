package com.appleframework.auto.acquisition.vvsmart.model;

import java.io.Serializable;
import java.util.List;

public class JsonResult implements Serializable {

	private static final long serialVersionUID = 1411801420539585196L;

	private Integer Result;
	private String Time;

	private List<JsonTrack> List;

	public Integer getResult() {
		return Result;
	}

	public void setResult(Integer result) {
		Result = result;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	public List<JsonTrack> getList() {
		return List;
	}

	public void setList(List<JsonTrack> list) {
		List = list;
	}

}

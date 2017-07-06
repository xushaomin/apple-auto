package com.appleframework.auto.bean.fence;

import java.io.Serializable;
import java.util.List;

public class PolygonFence extends BaseFence implements Fence, Serializable {

	private static final long serialVersionUID = 1L;

	private List<Double> polygonXA;
	private List<Double> polygonYA;

	@Override
	public Integer getType() {
		return 3;
	}

	public List<Double> getPolygonXA() {
		return polygonXA;
	}

	public void setPolygonXA(List<Double> polygonXA) {
		this.polygonXA = polygonXA;
	}

	public List<Double> getPolygonYA() {
		return polygonYA;
	}

	public void setPolygonYA(List<Double> polygonYA) {
		this.polygonYA = polygonYA;
	}

}

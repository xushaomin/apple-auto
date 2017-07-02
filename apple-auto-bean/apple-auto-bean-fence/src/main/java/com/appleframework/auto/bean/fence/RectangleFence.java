package com.appleframework.auto.bean.fence;

import java.io.Serializable;

/**
 * 矩形围栏
 */
public class RectangleFence extends BaseFence implements Fence, Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 矩形对角A点
	 */
	private Point pointA;
	
	/**
	 * 矩形对角B点
	 */
	private Point pointB;

	public Point getPointA() {
		return pointA;
	}

	public void setPointA(Point pointA) {
		this.pointA = pointA;
	}

	public Point getPointB() {
		return pointB;
	}

	public void setPointB(Point pointB) {
		this.pointB = pointB;
	}

	public double[] aToArray() {
		double[] point = { pointA.getLatitude(), pointA.getLongitude()};
		return point;
	}
	
	public double[] bToArray() {
		double[] point = { pointB.getLatitude(), pointB.getLongitude()};
		return point;
	}
	
	public double[] allToArray() {
		double[] point = { pointA.getLatitude(), pointA.getLongitude(), pointB.getLatitude(), pointB.getLongitude() };
		return point;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"pointA\":\"");
		builder.append(pointA);
		builder.append("\",\"pointB\":\"");
		builder.append(pointB);
		builder.append("\"}  ");
		return builder.toString();
	}
	
	

}

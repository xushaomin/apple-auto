package com.appleframework.auto.fence.calculate.model;

import java.io.Serializable;

import com.appleframework.auto.bean.location.Location;

public class FenceLocation implements Serializable {

	private static final long serialVersionUID = 1542422512605760941L;

	private Location in;
	private int inCnt = 0;
	private int outCnt = 0;

	public Location getIn() {
		return in;
	}

	public void setIn(Location in) {
		this.in = in;
	}

	public int getInCnt() {
		return inCnt;
	}

	public void setInCnt(int inCnt) {
		this.inCnt = inCnt;
	}

	public int getOutCnt() {
		return outCnt;
	}

	public void setOutCnt(int outCnt) {
		this.outCnt = outCnt;
	}

	public int addInCount() {
		this.inCnt = this.inCnt + 1;
		return this.inCnt;
	}

	public int addOutCount() {
		this.outCnt = this.outCnt + 1;
		return this.outCnt;
	}

	public FenceLocation(Location in) {
		this.in = in;
		this.inCnt = 1;
	}

	public FenceLocation(Location in, int inCnt) {
		this.in = in;
		this.inCnt = inCnt;
	}

	public static FenceLocation create(Location in) {
		return new FenceLocation(in);
	}

	public static FenceLocation create(Location in, int count) {
		return new FenceLocation(in, count);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"in\":\"");
		builder.append(in);
		builder.append("\",\"inCnt\":\"");
		builder.append(inCnt);
		builder.append("\",\"outCnt\":\"");
		builder.append(outCnt);
		builder.append("\"}  ");
		return builder.toString();
	}

}

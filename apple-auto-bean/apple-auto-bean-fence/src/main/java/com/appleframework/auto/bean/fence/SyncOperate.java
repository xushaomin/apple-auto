package com.appleframework.auto.bean.fence;

import java.io.Serializable;

public class SyncOperate implements Serializable {

	private static final long serialVersionUID = -331105663663246055L;

	public static int CREATE = 1;
	public static int UPDATE = 2;
	public static int DELETE = 3;

	private Integer operateType;
	private Integer fenceType;
	private Fence oldFence;
	private Fence newFence;

	public Fence getOldFence() {
		return oldFence;
	}

	public void setOldFence(Fence oldFence) {
		this.oldFence = oldFence;
	}

	public Fence getNewFence() {
		return newFence;
	}

	public void setNewFence(Fence newFence) {
		this.newFence = newFence;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public Integer getFenceType() {
		return fenceType;
	}

	public void setFenceType(Integer fenceType) {
		this.fenceType = fenceType;
	}

	@Override
	public String toString() {
		return "SyncOperate [operateType=" + operateType + ", fenceType=" + fenceType + ", oldFence=" + oldFence
				+ ", newFence=" + newFence + "]";
	}

}

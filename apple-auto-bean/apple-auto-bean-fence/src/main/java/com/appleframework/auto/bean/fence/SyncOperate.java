package com.appleframework.auto.bean.fence;

import java.io.Serializable;

public class SyncOperate implements Serializable {

	private static final long serialVersionUID = -331105663663246055L;
	
	private Integer operate;
	private Fence oldFence;
	private Fence newFence;

	public Integer getOperate() {
		return operate;
	}

	public void setOperate(Integer operate) {
		this.operate = operate;
	}

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

}

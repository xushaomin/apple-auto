package com.appleframework.auto.fence.calculate;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.auto.bean.fence.CircleFence;
import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.auto.bean.fence.SyncOperate;
import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.fence.calculate.factory.RedisFactory;
import com.appleframework.cache.jedis.factory.PoolFactory;
import com.appleframework.structure.kdtree.KDTree;

public abstract class BaseFenceCalculateCircleBolt extends BaseFenceCalculateBolt {

	private static final Logger logger = LoggerFactory.getLogger(BaseFenceCalculateCircleBolt.class);

	private static final long serialVersionUID = 1L;

	protected static KDTree<String> kdTree;

	protected Properties props;

	public void init() {
		// 初始化kdtree
		PoolFactory poolFactory = RedisFactory.getInstance(props);
		if (null == kdTree) {
			kdTree = new KDTree<String>(4);
			List<Fence> list = this.get(poolFactory);
			for (Fence fence : list) {
				if (fence instanceof CircleFence) {
					CircleFence circleFence = (CircleFence) fence;
					try {
						kdTree.insert(circleFence.allToArray(), circleFence.getId());
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
			}
		}
		super.init(poolFactory);
	}

	public Set<String> calculate2(Location location) {
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		// 获取围栏信息
		double[] T = { latitude, longitude, 0, 0 };
		try {
			return kdTree.nearestEuclideanReturnSet(T);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public void create(CircleFence newFence) {
		try {
			kdTree.insert(newFence.allToArray(), newFence.getId());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void update(CircleFence oldFence, CircleFence newFence) {
		try {
			kdTree.delete(oldFence.allToArray());
			kdTree.insert(newFence.allToArray(), newFence.getId());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void delete(CircleFence oldFence) {
		try {
			kdTree.delete(oldFence.allToArray());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void processMessage(Object message) {
		if (message instanceof SyncOperate) {
			SyncOperate operate = (SyncOperate) message;
			if (logger.isInfoEnabled()) {
				logger.info("SyncOperate===========" + operate.toString());
			}
			if (operate.getFenceType() == 1) {
				if (operate.getOperateType() == SyncOperate.CREATE) {
					this.create((CircleFence) operate.getNewFence());
				} else if (operate.getOperateType() == SyncOperate.UPDATE) {
					this.update((CircleFence) operate.getOldFence(), (CircleFence) operate.getNewFence());
				} else {
					this.delete((CircleFence) operate.getOldFence());
				}
			}
		}
	}
}

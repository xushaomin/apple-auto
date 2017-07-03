package com.appleframework.auto.fence.calculate;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.auto.bean.fence.RectangleFence;
import com.appleframework.auto.bean.fence.SyncOperate;
import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.fence.calculate.factory.RedisFactory;
import com.appleframework.cache.jedis.factory.PoolFactory;
import com.appleframework.structure.rtree.Point;
import com.appleframework.structure.rtree.Rectangle;
import com.appleframework.structure.rtree.SpatialIndex;
import com.appleframework.structure.rtree.rtree.RTree;

import gnu.trove.procedure.TIntProcedure;

public abstract class BaseFenceCalculateRectangleBolt extends BaseFenceCalculateBolt {

	private static final Logger logger = LoggerFactory.getLogger(BaseFenceCalculateRectangleBolt.class);

	private static final long serialVersionUID = 1L;

	protected static SpatialIndex rTree = new RTree();

	protected Properties props;

	public void init() {
		rTree.init(null);
		PoolFactory poolFactory = RedisFactory.getInstance(props);
		List<Fence> list = this.get(poolFactory);
		for (Fence fence : list) {
			if (fence instanceof RectangleFence) {
				RectangleFence rectangleFence = (RectangleFence) fence;
				Rectangle rectangle = new Rectangle(rectangleFence.allToArray());
				try {
					rTree.add(rectangle, Integer.parseInt(rectangleFence.getId()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		super.init(poolFactory);
	}

	public Set<String> calculate2(Location location) {
		Point point = new Point(location.getLatitude(), location.getLongitude());
		final Set<String> fenceIdSet = new HashSet<>();
		TIntProcedure v = new TIntProcedure() {
			public boolean execute(int i) {
				fenceIdSet.add(String.valueOf(i));
				return true;
			}
		};
		rTree.nearest(point, v, 0);
		return fenceIdSet;
	}

	public void create(RectangleFence newFence) {
		try {
			rTree.add(new Rectangle(newFence.allToArray()), Integer.parseInt(newFence.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update(RectangleFence oldFence, RectangleFence newFence) {
		try {
			rTree.delete(new Rectangle(oldFence.allToArray()), Integer.parseInt(oldFence.getId()));
			rTree.add(new Rectangle(newFence.allToArray()), Integer.parseInt(newFence.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void delete(RectangleFence oldFence) {
		try {
			rTree.delete(new Rectangle(oldFence.allToArray()), Integer.parseInt(oldFence.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void processMessage(Object message) {
		if (message instanceof SyncOperate) {
			SyncOperate operate = (SyncOperate) message;
			if (logger.isInfoEnabled()) {
				logger.info("SyncOperate===========" + operate.toString());
			}
			if (operate.getFenceType() == 2) {
				if (operate.getOperateType() == SyncOperate.CREATE) {
					this.create((RectangleFence) operate.getNewFence());
				} else if (operate.getOperateType() == SyncOperate.UPDATE) {
					this.update((RectangleFence) operate.getOldFence(), (RectangleFence) operate.getNewFence());
				} else {
					this.delete((RectangleFence) operate.getOldFence());
				}
			}
		}
	}
}

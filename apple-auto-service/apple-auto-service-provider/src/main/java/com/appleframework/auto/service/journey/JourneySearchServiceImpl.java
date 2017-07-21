package com.appleframework.auto.service.journey;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.location.Journey;
import com.appleframework.auto.service.journey.key.JourneyRowkey;
import com.appleframework.data.hbase.client.SimpleHbaseClient;
import com.appleframework.model.page.Pagination;

@Service("journeySearchService")
public class JourneySearchServiceImpl implements JourneySearchService {

	protected final static Logger logger = Logger.getLogger(JourneySearchServiceImpl.class);

	@Resource
	private SimpleHbaseClient journeyHbaseDao;

	@Override
	public Pagination search(Pagination page, String account, long startTime) {
		JourneyRowkey startRowKey = JourneyRowkey.create(account, startTime);
		JourneyRowkey endRowKey = JourneyRowkey.create(account, startTime + 315532800000L);
		com.appleframework.data.core.page.Pagination<Journey> pagin = journeyHbaseDao.findPageList(startRowKey,
				endRowKey, Journey.class, page.getPageNo(), page.getPageSize());
		page.setList(pagin.getList());
		page.setTotalCount(pagin.getTotalCount());
		page.adjustPageNo();
		return page;
	}

}
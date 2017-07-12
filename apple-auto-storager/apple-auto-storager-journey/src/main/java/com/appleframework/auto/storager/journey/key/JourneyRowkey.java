package com.appleframework.auto.storager.journey.key;

import org.apache.hadoop.hbase.util.Bytes;

import com.appleframework.auto.bean.location.Journey;
import com.appleframework.auto.storager.journey.utils.StringUtils;
import com.appleframework.data.hbase.client.RowKey;

public class JourneyRowkey implements RowKey {
	
	public static long ID_MAX = 999999999999999999L;
	
	private String row;

	public JourneyRowkey(Journey journey) {
		String account = journey.getAccount();
		String accountS = StringUtils.zeroBeforeFill(account, 16);
		this.row = accountS + (ID_MAX - journey.getStartTime());
	}

	public JourneyRowkey(String row) {
		this.row = row;
	}
		

	public static JourneyRowkey create(Journey journey) {
		return new JourneyRowkey(journey);
	}

	public static JourneyRowkey create(String row) {
		return new JourneyRowkey(row);
	}

	@Override
	public byte[] toBytes() {
		return Bytes.toBytes(row);
	}

	public String getRow() {
		return row;
	}

	@Override
	public String toString() {
		return row;
	}
	
}
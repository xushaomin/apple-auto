package com.appleframework.auto.service.location.key;

import org.apache.hadoop.hbase.util.Bytes;

import com.appleframework.auto.service.utils.StringUtils;
import com.appleframework.bean.location.Location;
import com.appleframework.data.hbase.client.RowKey;

public class LocationRowkey implements RowKey {
	
	private String row;

	public LocationRowkey(Location bo) {
		String account = bo.getAccount();
		String accountS = StringUtils.zeroBeforeFill(account, 16);
		this.row = accountS + bo.getTime();
	}

	public LocationRowkey(String row) {
		this.row = row;
	}
	
	public LocationRowkey(String account, long time) {
		String accountS = StringUtils.zeroBeforeFill(account, 16);
		this.row = accountS + time;
	}
		

	public static LocationRowkey create(Location bo) {
		return new LocationRowkey(bo);
	}

	public static LocationRowkey create(String row) {
		return new LocationRowkey(row);
	}
	
	public static LocationRowkey create(String account, long time) {
		return new LocationRowkey(account, time);
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
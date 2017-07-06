package com.appleframework.auto.service.fence;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

	public static List<Double> string2DoubleList(String args) {
		String[] arrays = args.split(",");
		List<Double> list = new ArrayList<>(arrays.length);
		for (int i = 0; i < arrays.length; i++) {
			String string = arrays[i];
			list.add(Double.parseDouble(string));
		}
		return list;
	}
    
}

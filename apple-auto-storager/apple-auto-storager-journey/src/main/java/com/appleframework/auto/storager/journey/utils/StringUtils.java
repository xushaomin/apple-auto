package com.appleframework.auto.storager.journey.utils;

public class StringUtils {

	public static String zeroAfterFill(String message, int size) {
		int length = message.length();
		if (length >= size) {
			return message;
		} else {
			String zeroStr = "";
			int differLength = size - length;
			for (int i = 1; i <= differLength; i++) {
				zeroStr += "0";
			}
			return message + zeroStr;
		}
	}

	public static String zeroBeforeFill(String message, int size) {
		int length = message.length();
		if (length >= size) {
			return message;
		} else {
			String zeroStr = "";
			int differLength = size - length;
			for (int i = 1; i <= differLength; i++) {
				zeroStr += "0";
			}
			return zeroStr + message;
		}
	}

	public static void main(String[] args) {
		System.out.println(StringUtils.zeroBeforeFill("234", 10));
	}
}

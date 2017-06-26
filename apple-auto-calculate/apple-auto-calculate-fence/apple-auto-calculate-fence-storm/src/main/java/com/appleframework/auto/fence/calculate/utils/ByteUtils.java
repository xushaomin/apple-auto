package com.appleframework.auto.fence.calculate.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ByteUtils {

	public static Object fromByte(byte[] obj) {
		Object object = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(obj);
		try {
			ObjectInputStream ois = new ObjectInputStream(bis);
			object = ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	public static byte[] toBytes(Object object) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			byte[] objMessage = bos.toByteArray();
			return objMessage;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
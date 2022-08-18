package com.sample.app.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializationUtil {

	public static byte[] serialize(Serializable serializable) {

		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
			objectOutputStream.writeObject(serializable);
			objectOutputStream.flush();
			return byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static <T> T deserialize(byte[] byteArray, Class<T> clazz) {
		try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
				ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
			return (T) objectInputStream.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}

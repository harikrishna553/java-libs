package com.sample.app.tools;

import java.lang.reflect.Method;
import java.util.Optional;

public class ReflectionUtil {
	/**
	 * Returns a Method by name and parameter types from a given class.
	 */
	public static Optional<Method> getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
		try {
			return Optional.of(clazz.getDeclaredMethod(methodName, parameterTypes));
		} catch (NoSuchMethodException e) {
			return Optional.empty();
		}
	}

	/**
	 * Returns all declared methods from the given class.
	 */
	public static Method[] getAllMethods(Class<?> clazz) {
		return clazz.getDeclaredMethods();
	}
}

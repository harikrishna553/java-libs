package com.sample.app.introspector;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.sample.app.model.Employee;

public class PopulateJavaObjectFromAMap {

	private static boolean matchesPrimitive(final Class<?> targetType, final Class<?> valueType) {
		if (!targetType.isPrimitive()) {
			return false;
		}

		try {
			// see if there is a "TYPE" field. This is present for primitive wrappers.
			final Field typeField = valueType.getField("TYPE");
			final Object primitiveValueType = typeField.get(valueType);

			if (targetType == primitiveValueType) {
				return true;
			}
		} catch (final NoSuchFieldException | IllegalAccessException ignored) {
			// If the TYPE field is inaccessible, it suggests that we're not dealing with a
			// primitive wrapper.
			// There's no action required in this case, as we can't check for compatibility.
		}
		return false;
	}

	private static boolean isCompatibleType(final Object value, final Class<?> type) {
		return value == null || type.isInstance(value) || matchesPrimitive(type, value.getClass());
	}

	public static <T> T fromMap(Map<String, Object> map, Class<T> clazz) throws Exception {
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

		T object = clazz.getDeclaredConstructor().newInstance();

		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

			try {
				String name = propertyDescriptor.getName();
				Object value = map.get(name);

				Method setterMethod = propertyDescriptor.getWriteMethod();
				if (setterMethod == null) {
					continue;
				}
				final Class<?> firstParameterType = setterMethod.getParameterTypes()[0];

				if (!isCompatibleType(value, firstParameterType)) {
					throw new Exception("Can't set the value " + value + " to the " + name);
				}
				setterMethod.invoke(object, value);

			} catch (Exception e) {
				throw e;
			}

		}

		return object;

	}

	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new HashMap<>();

		map.put("id", 123);
		map.put("name", "Krishna Gurram");
		map.put("age", 34);

		Employee emp = fromMap(map, Employee.class);
		System.out.println(emp);

	}

}

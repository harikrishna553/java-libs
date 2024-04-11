package com.sample.app.introspector;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import com.sample.app.model.Employee;

public class GetPropertiesOfClass {

	private static PropertyDescriptor[] propertyDescriptors(final Class<?> c) throws IntrospectionException {
		BeanInfo beanInfo = Introspector.getBeanInfo(c);
		return beanInfo.getPropertyDescriptors();
	}

	public static void main(String[] args) throws Exception {
		PropertyDescriptor[] propertyDescriptors = propertyDescriptors(Employee.class);

		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String name = propertyDescriptor.getName();
			Class<?> type = propertyDescriptor.getPropertyType();
			Method methodToReadThisProperty = propertyDescriptor.getReadMethod();
			Method methodToUpdateThisProperty = propertyDescriptor.getWriteMethod();

			System.out.println("name : " + name);
			System.out.println("type : " + type);
			if (methodToReadThisProperty != null) {
				System.out.println("methodToReadThisProperty : " + methodToReadThisProperty.getName());
			}
			if (methodToUpdateThisProperty != null) {
				System.out.println("methodToUpdateThisProperty : " + methodToUpdateThisProperty.getName());
			}
			System.out.println();

		}

	}

}

package com.sample.app.introspector;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.lang.reflect.Method;

import com.sample.app.model.Employee;

public class GetMethodsOfABean {

	private static MethodDescriptor[] methodDescriptors(final Class<?> clazz) throws IntrospectionException {
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		return beanInfo.getMethodDescriptors();
	}

	public static void main(String[] args) throws IntrospectionException {
		MethodDescriptor[] methodDescriptors = methodDescriptors(Employee.class);

		for (MethodDescriptor methodDescriptor : methodDescriptors) {

			Method method = methodDescriptor.getMethod();
			// Check if the method belongs to the specified class (Employee)
			if (!method.getDeclaringClass().equals(Employee.class)) {
				continue;
			}

			String name = method.getName();
			System.out.println("Method name : " + name);

			Class<?>[] parameterTypes = method.getParameterTypes();

			if (parameterTypes.length == 0) {
				continue;
			}

			System.out.println("  Paramters : ");
			for (Class<?> parameterType : parameterTypes) {
				System.out.println("    " + parameterType);
			}

		}

	}

}

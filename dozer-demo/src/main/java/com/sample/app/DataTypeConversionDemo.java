package com.sample.app;

import org.dozer.DozerBeanMapper;

import com.sample.app.model.Type2;
import com.sample.app.model.Type3;

public class DataTypeConversionDemo {

	public static void main(String[] args) {

		DozerBeanMapper mapper = new DozerBeanMapper();

		Type3 type3 = new Type3("10", 20.987);
		Type2 type2 = mapper.map(type3, Type2.class);

		System.out.println(type3);
		System.out.println(type2);
	}

}

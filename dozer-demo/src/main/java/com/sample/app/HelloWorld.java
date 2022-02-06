package com.sample.app;

import org.dozer.DozerBeanMapper;

import com.sample.app.model.Type1;
import com.sample.app.model.Type2;

public class HelloWorld {

	public static void main(String[] args) {
		DozerBeanMapper mapper = new DozerBeanMapper();

		Type1 type1 = new Type1(10, 20);
		Type2 type2 = mapper.map(type1, Type2.class);
		
		System.out.println(type1);
		System.out.println(type2);
	}

}

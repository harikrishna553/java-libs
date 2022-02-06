package com.sample.app;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.dozer.DozerBeanMapper;

import com.sample.app.model.Product1;
import com.sample.app.model.Product2;

public class DateToString {

	public static void main(String[] args) throws IOException {

		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.setMappingFiles(Arrays.asList("date_to_string.xml"));

		Product1 product1 = new Product1();
		product1.setManufacturingDate(new Date());

		Product2 product2 = mapper.map(product1, Product2.class);
		Product1 product1ConvertedBack = mapper.map(product2, Product1.class);

		System.out.println(product1);
		System.out.println(product2);
		System.out.println(product1ConvertedBack);

	}

}

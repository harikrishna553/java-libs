package com.sample.app;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

import org.dozer.DozerBeanMapper;

import com.sample.app.model.Product1;
import com.sample.app.model.Product2;
import com.sample.app.model.Product3;
import com.sample.app.model.Product4;

public class DateToStringTopLevelMapping {
	public static void main(String[] args) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.setMappingFiles(Arrays.asList("date_formatting_at_top_level.xml"));

		Product1 product1 = new Product1();
		product1.setManufacturingDate(new Date());

		Product2 product2 = mapper.map(product1, Product2.class);
		Product1 product1ConvertedBack = mapper.map(product2, Product1.class);

		System.out.println("product1 : " + product1);
		System.out.println("product2 : " + product2);
		System.out.println("product1ConvertedBack : " + product1ConvertedBack);

		Date manufacturingDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date expiryDate = Date.from(LocalDate.now().plusDays(100).atStartOfDay(ZoneId.systemDefault()).toInstant());

		Product3 product3 = new Product3(manufacturingDate, expiryDate);
		Product4 product4 = mapper.map(product3, Product4.class);
		Product3 product3ConvertedBack = mapper.map(product4, Product3.class);

		System.out.println("\nproduct3 : " + product3);
		System.out.println("product4 : " + product4);
		System.out.println("product3ConvertedBack : " + product3ConvertedBack);
	}
}

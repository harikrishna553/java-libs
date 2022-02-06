package com.sample.app;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

import org.dozer.DozerBeanMapper;

import com.sample.app.model.Product3;
import com.sample.app.model.Product4;

public class DateToString1 {

	public static void main(String[] args) throws IOException {

		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.setMappingFiles(Arrays.asList("date_to_string_at_class_level.xml"));

		Date manufacturingDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date expiryDate = Date.from(LocalDate.now().plusDays(100).atStartOfDay(ZoneId.systemDefault()).toInstant());

		Product3 product3 = new Product3(manufacturingDate, expiryDate);
		Product4 product4 = mapper.map(product3, Product4.class);
		Product3 product3ConvertedBack = mapper.map(product4, Product3.class);

		System.out.println(product3);
		System.out.println(product4);
		System.out.println(product3ConvertedBack);

	}

}

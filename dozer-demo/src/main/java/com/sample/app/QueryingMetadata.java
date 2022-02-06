package com.sample.app;

import java.io.IOException;

import org.dozer.DozerBeanMapper;
import org.dozer.metadata.ClassMappingMetadata;
import org.dozer.metadata.FieldMappingMetadata;
import org.dozer.metadata.MappingMetadata;

import com.sample.app.model.Student;
import com.sample.app.model.User;

public class QueryingMetadata {

	public static void main(String[] args) throws IOException {

		DozerBeanMapper beanMapper = new DozerBeanMapper();
		beanMapper.map(new User(1,  "Arjun", "Gurram"), Student.class);

		MappingMetadata mapMetadata = beanMapper.getMappingMetadata();

		ClassMappingMetadata classMappingMetadata = mapMetadata.getClassMapping(User.class, Student.class);

		FieldMappingMetadata fieldMetadata = classMappingMetadata.getFieldMappingBySource("fName");

		System.out.println("fName mapped to : " + fieldMetadata.getDestinationName());

	}

}

package com.sample.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import com.sample.app.model.Type1;
import com.sample.app.model.Type2;

public class MapCollectionsDemo {

	public static <T, U> List<U> map(final Mapper mapper, final List<T> source, final Class<U> destType) {

		final List<U> dest = new ArrayList<>();

		for (T element : source) {
			dest.add(mapper.map(element, destType));
		}

		return dest;
	}

	public static void main(String args[]) {
		Type1 type1 = new Type1(10, 50);
		Type1 type2 = new Type1(20, 60);
		Type1 type3 = new Type1(30, 70);
		Type1 type4 = new Type1(40, 80);

		List<Type1> type1Objects = Arrays.asList(type1, type2, type3, type4);

		DozerBeanMapper mapper = new DozerBeanMapper();

		List<Type2> type2Objects = map(mapper, type1Objects, Type2.class);

		System.out.println("Printing Type1 objects");
		type1Objects.stream().forEach(System.out::println);

		System.out.println("\n\nPrinting Type2 objects");
		type2Objects.stream().forEach(System.out::println);

	}

}



package com.sample.app.reflections;

import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.RecordComponent;

public class RecordComponentsDemo {
	
	public static void main(String[] args) {
		RecordComponent[] recordComponents = Point.class.getRecordComponents();
		
		for(RecordComponent recordComponent: recordComponents) {
			System.out.println(recordComponent);
		}
		
		
	}

}

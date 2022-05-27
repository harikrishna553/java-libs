package com.sample.app.types;

import org.apache.flink.types.StringValue;

public class StringValueDemo1 {
	public static void main(String[] args) {
		StringValue stringValue1 = new StringValue();
		StringValue stringValue2 = new StringValue("Hello World");
		StringValue stringValue3 = new StringValue(stringValue2);
		StringValue stringValue4 = new StringValue(stringValue2, 5, 6);

		System.out.println("stringValue1: " + stringValue1);
		System.out.println("stringValue2: " + stringValue2);
		System.out.println("stringValue3: " + stringValue3);
		System.out.println("stringValue4: " + stringValue4);

		// Get Char array
		char[] chars = stringValue2.getCharArray();
		for (char ch : chars) {
			System.out.print(ch + ",");
		}
		
		// Get String value
		System.out.println(stringValue2.getValue());
		
		// Get sub string
		System.out.println(stringValue2.substring(6));
		System.out.println(stringValue2.substring(6, 11));
		
		System.out.println("index of 'o' is " + stringValue2.find("o"));
		System.out.println("index of 'o' from index 6 is " + stringValue2.find("o", 6));
		
		System.out.println(stringValue2.startsWith("He"));
		System.out.println(stringValue2.startsWith("Wo", 6));
		
		StringValue stringValue = new StringValue("Hi");
		System.out.println(stringValue.append('!'));	// Hi!
		System.out.println(stringValue.append("!!"));	// Hi!!!
		System.out.println(stringValue.append(new StringValue("!!!"))); // Hi!!!!!!

	}

}

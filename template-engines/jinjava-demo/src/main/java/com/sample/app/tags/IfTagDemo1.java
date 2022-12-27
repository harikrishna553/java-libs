package com.sample.app.tags;

import java.util.Collections;

import com.hubspot.jinjava.Jinjava;

public class IfTagDemo1 {
	
	public static void main(String[] args) {
		final String template1 = "{% if true %} if is evaluated to true {% endif %}";
		final String template2 = "{% if 4 > 3 %} 4 > 3 is evaluated to true {% endif %}";
		final String template3 = "{% if 4 > 5 %} 4> 5 is evaluated to true {% endif %}";
		
		final Jinjava jinjava = new Jinjava();
		final String finalDocument1 = jinjava.render(template1, Collections.EMPTY_MAP);
		final String finalDocument2 = jinjava.render(template2, Collections.EMPTY_MAP);
		final String finalDocument3 = jinjava.render(template3, Collections.EMPTY_MAP);
		
		System.out.println("finalDocument1 : " + finalDocument1);
		System.out.println("finalDocument2 : " + finalDocument2);
		System.out.println("finalDocument3 : " + finalDocument3);
	}

}

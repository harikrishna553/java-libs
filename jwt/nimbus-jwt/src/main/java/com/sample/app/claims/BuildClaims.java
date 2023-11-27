package com.sample.app.claims;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import com.nimbusds.jwt.JWTClaimsSet;

public class BuildClaims {

	private static Date addSeconds(Date date, int seconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}

	public static void main(String[] args) {
		JWTClaimsSet.Builder claimsBuilder = new JWTClaimsSet.Builder();

		claimsBuilder.issuer("https://self-learning-java-tutorial.blogspot.com/");
		claimsBuilder.subject("1234-9876-34-asdta");
		claimsBuilder.audience(Arrays.asList("https://abc.com", "https://def.com"));

		Date now = new Date(new Date().getTime() / 1000 * 1000);
		claimsBuilder.expirationTime(addSeconds(now, 600));
		claimsBuilder.notBeforeTime(now);
		claimsBuilder.issueTime(now);

		claimsBuilder.jwtID("1234567");
		claimsBuilder.claim("x-custom_1", "abc");
		claimsBuilder.claim("x-custom_2", "def");

		System.out.println(claimsBuilder.getClaims());

	}

}

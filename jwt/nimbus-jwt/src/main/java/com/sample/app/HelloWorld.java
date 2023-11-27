package com.sample.app;

import java.text.ParseException;
import java.util.List;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;

public class HelloWorld {

	public static void main(String[] args) throws ParseException {
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.subject("ebfg3e60-fc7f-59fa-a7de-e93e388ea291")
				.issuer("https://self-learning-java-tutorial.blogspot.com/")
				.audience("https://example.com")
				.build();

		PlainJWT jwt = new PlainJWT(claimsSet);
		String jwtToken = jwt.serialize();

		System.out.println("JWT token \n" + jwtToken);
		
		JWTClaimsSet jwtClaimsSet = jwt.getJWTClaimsSet();
		String subject = jwtClaimsSet.getSubject();
		String issuer = jwtClaimsSet.getIssuer();
		List<String> audience = jwtClaimsSet.getAudience();
		
		
		System.out.println("\nsubject : " + subject);
		System.out.println("issuer : " + issuer);
		System.out.println("audience : " + audience);
		
	}

}
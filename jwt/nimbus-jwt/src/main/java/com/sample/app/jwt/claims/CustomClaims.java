package com.sample.app.jwt.claims;

import java.text.ParseException;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;

public class CustomClaims {

	public static void main(String[] args) throws ParseException {
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.subject("ebfg3e60-fc7f-59fa-a7de-e93e388ea291")
				.issuer("https://self-learning-java-tutorial.blogspot.com/")
				.audience("https://example.com")
				.claim("permissions", "reader")
				.claim("limit_docs_per_hour", "100")
				.build();

		PlainJWT jwt = new PlainJWT(claimsSet);
		String jwtToken = jwt.serialize();

		System.out.println("JWT token \n" + jwtToken);
		
		JWTClaimsSet jwtClaimsSet = jwt.getJWTClaimsSet();
		System.out.println("\nClaims : \n" + jwtClaimsSet.getClaims());
		
	}

}

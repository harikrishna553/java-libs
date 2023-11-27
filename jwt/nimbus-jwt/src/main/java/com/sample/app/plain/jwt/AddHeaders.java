package com.sample.app.plain.jwt;

import java.text.ParseException;
import java.util.List;

import com.nimbusds.jose.PlainHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;

public class AddHeaders {

	public static void main(String[] args) throws ParseException {
		long timeInMillis = System.currentTimeMillis();
		long expiryTime = timeInMillis + (10 * 60 * 1000);
		
		PlainHeader header = new PlainHeader.Builder()
				.customParam("iat", timeInMillis)
				.customParam("exp", expiryTime)
				.build();
		
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.subject("ebfg3e60-fc7f-59fa-a7de-e93e388ea291")
				.issuer("https://self-learning-java-tutorial.blogspot.com/")
				.audience("https://example.com")
				.build();

		PlainJWT jwt = new PlainJWT(header, claimsSet);
		String jwtToken = jwt.serialize();
		PlainHeader jwtHeader = jwt.getHeader();

		System.out.println("JWT token \n" + jwtToken);
		System.out.println("\nJWT payload \n" + jwt.getPayload());
		System.out.println("\nJWT header \n" + jwtHeader);
		
		JWTClaimsSet jwtClaimsSet = jwt.getJWTClaimsSet();
		String subject = jwtClaimsSet.getSubject();
		String issuer = jwtClaimsSet.getIssuer();
		List<String> audience = jwtClaimsSet.getAudience();
		
		
		System.out.println("\nsubject : " + subject);
		System.out.println("issuer : " + issuer);
		System.out.println("audience : " + audience);
		
	}

}


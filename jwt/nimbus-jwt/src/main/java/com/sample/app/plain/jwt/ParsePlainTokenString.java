package com.sample.app.plain.jwt;

import java.text.ParseException;
import java.util.List;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;

public class ParsePlainTokenString {

	public static void main(String[] args) throws ParseException {
		String jwtTokenString = "eyJleHAiOjE3MDEwNjMzNzU5MjUsImlhdCI6MTcwMTA2Mjc3NTkyNSwiYWxnIjoibm9uZSJ9.eyJpc3MiOiJodHRwczovL3NlbGYtbGVhcm5pbmctamF2YS10dXRvcmlhbC5ibG9nc3BvdC5jb20vIiwic3ViIjoiZWJmZzNlNjAtZmM3Zi01OWZhLWE3ZGUtZTkzZTM4OGVhMjkxIiwiYXVkIjoiaHR0cHM6Ly9leGFtcGxlLmNvbSJ9.\n";

		PlainJWT jwt = PlainJWT.parse(jwtTokenString);

		System.out.println("JWT payload \n" + jwt.getPayload());
		System.out.println("\nJWT header \n" + jwt.getHeader());

		JWTClaimsSet jwtClaimsSet = jwt.getJWTClaimsSet();
		String subject = jwtClaimsSet.getSubject();
		String issuer = jwtClaimsSet.getIssuer();
		List<String> audience = jwtClaimsSet.getAudience();

		System.out.println("\nsubject : " + subject);
		System.out.println("issuer : " + issuer);
		System.out.println("audience : " + audience);
	}

}

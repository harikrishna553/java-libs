package com.sample.app.plain.jwt;

import java.text.ParseException;
import java.util.List;

import com.nimbusds.jose.PlainHeader;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;

public class JWTFromBase64HeaderAndPayload {

	public static void main(String[] args) throws ParseException {
		String header = "eyJleHAiOjE3MDEwNjMzNzU5MjUsImlhdCI6MTcwMTA2Mjc3NTkyNSwiYWxnIjoibm9uZSJ9";
		String payload = "eyJpc3MiOiJodHRwczovL3NlbGYtbGVhcm5pbmctamF2YS10dXRvcmlhbC5ibG9nc3BvdC5jb20vIiwic3ViIjoiZWJmZzNlNjAtZmM3Zi01OWZhLWE3ZGUtZTkzZTM4OGVhMjkxIiwiYXVkIjoiaHR0cHM6Ly9leGFtcGxlLmNvbSJ9";

		Base64URL base64Header = new Base64URL(header);
		Base64URL base64Payload = new Base64URL(payload);

		PlainJWT jwt = new PlainJWT(base64Header, base64Payload);

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

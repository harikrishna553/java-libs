package com.sample.app.claims;

import java.text.ParseException;

import com.nimbusds.jwt.JWTClaimsSet;

public class ParseClaims {

	public static void main(String[] args) throws ParseException {
		String claims = """
					{
					"iss": "https://self-learning-java-tutorial.blogspot.com/",
					"sub" : "1234-9876-34-asdta",
					"aud" : [
							"https://abc.com",
							"https://def.com"
							],
					"exp" : 1701094750,
					"nbf" : 1701084750,
					"iat" : 1701084750,
					"jti" : "1234567",
					"x-custom_1" : "abc",
					"x-custom_2" : "def"
				}
								""";
		
		JWTClaimsSet claimsSet = JWTClaimsSet.parse(claims);
		
		System.out.println(claimsSet);
	}

}

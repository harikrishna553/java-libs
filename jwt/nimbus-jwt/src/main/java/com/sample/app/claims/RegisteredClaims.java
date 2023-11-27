package com.sample.app.claims;

import java.util.Set;

import com.nimbusds.jwt.JWTClaimsSet;

public class RegisteredClaims {
	
	public static void main(String[] args) {
		Set<String> names = JWTClaimsSet.getRegisteredNames();
		
		System.out.println(names);
		
	}

}

package com.sample.app.encrypted.jwt;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jose.JWEEncrypter;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;

public class ParseEncryptedJWT {

	public static void main(String[] args) throws JOSEException, NoSuchAlgorithmException, ParseException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		SecretKey secretKey = keyGenerator.generateKey();

		// Define claims
		JWTClaimsSet jwtClaims = new JWTClaimsSet.Builder().subject("ebfg3e60-fc7f-59fa-a7de-e93e388ea291")
				.issuer("https://self-learning-java-tutorial.blogspot.com/").audience("https://example.com").build();

		// Define JWEHeader
		JWEHeader header = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128GCM);

		EncryptedJWT jwt = new EncryptedJWT(header, jwtClaims);

		JWEEncrypter encrypter = new DirectEncrypter(secretKey);

		// Do the actual encryption
		jwt.encrypt(encrypter);

		// Serialise to JWT compact form
		String encryptedJwtString = jwt.serialize();
		System.out.println("jwtString \n" + encryptedJwtString);

		// Parse back
		EncryptedJWT parsedJWT = EncryptedJWT.parse(encryptedJwtString);
		JWEDecrypter decrypter = new DirectDecrypter(secretKey);
		parsedJWT.decrypt(decrypter);

		System.out.println("\nClaims\n");
		System.out.println(parsedJWT.getJWTClaimsSet().getClaims());

	}

}

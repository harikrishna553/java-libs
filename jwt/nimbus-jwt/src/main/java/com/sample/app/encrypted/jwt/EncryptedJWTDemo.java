package com.sample.app.encrypted.jwt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;

public class EncryptedJWTDemo {

	public static void main(String[] args) throws NoSuchAlgorithmException, JOSEException, ParseException {
		// Generate a keypair
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048);
		KeyPair kp = kpg.genKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();

		// Define claims
		JWTClaimsSet jwtClaims = new JWTClaimsSet.Builder()
				.subject("ebfg3e60-fc7f-59fa-a7de-e93e388ea291")
				.issuer("https://self-learning-java-tutorial.blogspot.com/")
				.audience("https://example.com")
				.build();

		// Define JWEHeader
		JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM);

		// Create the encrypted JWT object
		EncryptedJWT jwt = new EncryptedJWT(header, jwtClaims);

		// Create an encrypter with the specified public RSA key
		RSAEncrypter encrypter = new RSAEncrypter(publicKey);

		// Do the actual encryption
		jwt.encrypt(encrypter);

		// Serialise to JWT compact form
		String encryptedJwtString = jwt.serialize();
		System.out.println("jwtString \n" + encryptedJwtString);

		// Parse back
		jwt = EncryptedJWT.parse(encryptedJwtString);

		// Create an decrypter with the specified private RSA key
		RSADecrypter decrypter = new RSADecrypter(privateKey);

		// Decrypt
		jwt.decrypt(decrypter);

		System.out.println("\nClaims\n");
		System.out.println(jwt.getJWTClaimsSet().getClaims());

	}

}

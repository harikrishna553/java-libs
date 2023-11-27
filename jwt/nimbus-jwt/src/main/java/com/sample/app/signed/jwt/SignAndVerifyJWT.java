package com.sample.app.signed.jwt;

import java.net.URISyntaxException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class SignAndVerifyJWT {

	public static void main(String[] args) throws NoSuchAlgorithmException, URISyntaxException, JOSEException {
		// Generate a keypair
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048);
		KeyPair kp = kpg.genKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();

		// Define claims
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.subject("ebfg3e60-fc7f-59fa-a7de-e93e388ea291")
				.issuer("https://self-learning-java-tutorial.blogspot.com/")
				.audience("https://example.com")
				.build();

		// Define JWSHeader
		JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
				.keyID("1234")
				.build();

		SignedJWT signedJWT = new SignedJWT(header, claimsSet);
		
		JWSSigner signer = new RSASSASigner(privateKey);
		signedJWT.sign(signer);

		String serializedJWT = signedJWT.serialize();
		System.out.println(serializedJWT);

		JWSVerifier verifier = new RSASSAVerifier(publicKey);
		boolean isSignatureValid = signedJWT.verify(verifier);

		System.out.println("isSignatureValid : " + isSignatureValid);
	}

}
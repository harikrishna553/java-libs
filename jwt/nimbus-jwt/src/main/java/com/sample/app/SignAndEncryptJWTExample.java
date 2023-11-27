package com.sample.app;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEEncrypter;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class SignAndEncryptJWTExample {

	public static void main(String[] args) throws JOSEException, ParseException, NoSuchAlgorithmException {
		// Create JWT claims
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.subject("ebfg3e60-fc7f-59fa-a7de-e93e388ea291")
				.issuer("https://self-learning-java-tutorial.blogspot.com/")
				.audience("https://example.com")
				.build();
		
		// Define JWSHeader
		JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).keyID("1234").build();
		SignedJWT signedJWT = new SignedJWT(header, claimsSet);

		// Generate a keypair
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048);
		KeyPair kp = kpg.genKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();


		// Sign the JWT
		JWSSigner signer = new RSASSASigner(privateKey);
		signedJWT.sign(signer);

		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

		SecretKey secretKey = keyGenerator.generateKey();

		// Encrypt the JWT
		JWEHeader jweHeader = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128GCM);
		JWEEncrypter encrypter = new DirectEncrypter(secretKey); // Replace with your actual encryption
																	// key
		JWEObject jweObject = new JWEObject(jweHeader, new Payload(signedJWT));
		jweObject.encrypt(encrypter);

		// Serialize the final JWT
		String signedAndEncryptedJWT = jweObject.serialize();
		System.out.println("Signed and Encrypted JWT: " + signedAndEncryptedJWT);

		// Decrypt and verify the JWT
		JWEObject decryptedJWEObject = JWEObject.parse(signedAndEncryptedJWT);
		decryptedJWEObject.decrypt(new DirectDecrypter(secretKey));

		SignedJWT extractedSignedToken = decryptedJWEObject.getPayload().toSignedJWT();
		if (extractedSignedToken.verify(new RSASSAVerifier(publicKey))) {
			System.out.println("JWT Decrypted and Verified successfully");
			System.out.println("Subject: " + extractedSignedToken.getJWTClaimsSet().getSubject());
			System.out.println("Issuer: " + extractedSignedToken.getJWTClaimsSet().getIssuer());
		} else {
			System.out.println("JWT verification failed");
		}
	}
}



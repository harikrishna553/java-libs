package com.sample.app.demo3;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SsrfDemoApp {

	public static void main(String[] args) {

		// Test URLs
		String[] testUrls = { "https://example.com", // ✅ Safe
				"http://localhost:8080/admin", // ❌ Localhost
				"http://192.168.1.1", // ❌ Private IP
				"http://169.254.169.254/latest/meta-data/", // ❌ Cloud metadata
				"http://google.com" // ✅ Safe
		};

		System.out.println("=== SSRF Protection Demo ===\n");

		for (String url : testUrls) {
			testUrl(url, false);
		}

		System.out.println("\n=== Dev Mode (allowPrivate=true) ===\n");

		for (String url : testUrls) {
			testUrl(url, true);
		}
	}

	private static void testUrl(String url, boolean allowPrivate) {
	    System.out.println("Testing: " + url);

	    try {
	        String safeUrl = SsrfProtection.validateSafeUrl(
	            url,
	            allowPrivate,
	            true
	        );

	        System.out.println("✔ SAFE: " + safeUrl);

	        try {
	            String response = fetch(safeUrl);
	            System.out.println("Response length: " + response.length());
	        } catch (Exception e) {
	            System.out.println("⚠ FETCH FAILED: " + 
	                (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()));
	        }

	    } catch (Exception e) {
	        System.out.println("✘ BLOCKED: " + 
	            (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()));
	    }

	    System.out.println("----------------------------------------");
	}

	/**
	 * Simple HTTP fetch method (simulates SSRF vulnerable behavior if not
	 * protected)
	 */
	public static String fetch(String url) throws Exception {
		return HttpClient.newHttpClient()
				.send(HttpRequest.newBuilder(URI.create(url)).GET().build(), HttpResponse.BodyHandlers.ofString())
				.body();
	}
}
package com.sample.app.demo2;

import java.net.InetAddress;
import java.net.URI;

public class StringMatch {

	public static void main(String[] args) {

		String[] testUrls = { "http://localhost:9090", "http://localhost.attacker.com", "http://127.0.0.1",
				"http://2130706433", // decimal form of 127.0.0.1
				"http://[::1]", // IPv6 loopback
				"http://[::ffff:127.0.0.1]", // IPv6-mapped IPv4
				"http://example.com" };

		for (String url : testUrls) {
			testUrl(url);
			System.out.println("--------------------------------------------------");
		}
	}

	private static void testUrl(String url) {
		System.out.println("Testing URL: " + url);

		// ❌ Naive check
		if (!url.contains("localhost")) {
			System.out.println("Naive Check: ✅ ALLOWED");
		} else {
			System.out.println("Naive Check: ❌ BLOCKED");
		}

		try {
			URI uri = URI.create(url);
			String host = uri.getHost();

			System.out.println("Extracted Host: " + host);

			InetAddress[] addresses = InetAddress.getAllByName(host);

			for (InetAddress addr : addresses) {
				System.out.println("Resolved IP: " + addr.getHostAddress());

				if (addr.isLoopbackAddress()) {
					System.out.println("⚠️  This is LOOPBACK (localhost equivalent)");
				}
			}

		} catch (Exception e) {
			System.out.println("Error resolving host: " + e.getMessage());
		}
	}
}
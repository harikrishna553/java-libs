package com.sample.app.demo2;

import java.net.InetAddress;
import java.net.URI;

public class IpNormalizationDemo {

	public static void main(String[] args) {

		String[] testUrls = { "http://127.0.0.1", "http://[::1]", "http://[::ffff:127.0.0.1]", "http://2130706433", // decimal
				"http://0x7f000001", // hex (may fail in Java DNS)
				"http://127.1", // short form
				"http://example.com" };

		for (String url : testUrls) {
			System.out.println("==================================================");
			testUrl(url);
		}
	}

	private static void testUrl(String url) {
		System.out.println("Testing URL: " + url);

		// ❌ Naive string-based check
		if (url.contains("127.0.0.1") || url.contains("localhost")) {
			System.out.println("Naive Check: ❌ BLOCKED");
		} else {
			System.out.println("Naive Check: ✅ ALLOWED");
		}

		try {
			URI uri = URI.create(url);
			String host = uri.getHost();

			System.out.println("Extracted Host: " + host);

			// Resolve and normalize
			InetAddress[] addresses = InetAddress.getAllByName(host);

			for (InetAddress addr : addresses) {
				String normalizedIp = addr.getHostAddress();

				System.out.println("Normalized IP: " + normalizedIp);

				// ✅ Proper checks
				if (addr.isLoopbackAddress()) {
					System.out.println("❗ SECURITY RISK: Loopback (localhost)");
				} else if (addr.isSiteLocalAddress()) {
					System.out.println("❗ SECURITY RISK: Private network");
				} else if (addr.isAnyLocalAddress()) {
					System.out.println("❗ SECURITY RISK: Any local address");
				} else {
					System.out.println("✅ Public IP (appears safe)");
				}
				System.out.println();
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
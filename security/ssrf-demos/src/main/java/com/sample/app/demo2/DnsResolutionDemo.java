package com.sample.app.demo2;

import java.net.InetAddress;

public class DnsResolutionDemo {

	public static void main(String[] args) {
		String host = "google.com";

		try {
			System.out.println("Resolving host: " + host);
			System.out.println("--------------------------------------------------");

			InetAddress[] addresses = InetAddress.getAllByName(host);

			for (InetAddress addr : addresses) {
				String ip = addr.getHostAddress();

				System.out.println("Resolved IP: " + ip);

				// Classification (important for SSRF understanding)
				if (addr.isLoopbackAddress()) {
					System.out.println("❗ Loopback (localhost)");
				} else if (addr.isSiteLocalAddress()) {
					System.out.println("❗ Private IP");
				} else if (addr.isAnyLocalAddress()) {
					System.out.println("❗ Any local address");
				} else if (addr.isLinkLocalAddress()) {
					System.out.println("❗ Link-local address");
				} else {
					System.out.println("✅ Public IP");
				}

				System.out.println();
			}

		} catch (Exception e) {
			System.out.println("Error resolving host: " + e.getMessage());
		}
	}
}
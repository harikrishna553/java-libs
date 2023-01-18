package com.sample.app.miscellaneous;

import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.Cookie;

public class ReadCookies {

	private static String cookieAsStr(Cookie cookie) {

		return "Cookie [name=" + cookie.name + ", value=" + cookie.value + ", url=" + cookie.url + ", domain="
				+ cookie.domain + ", path=" + cookie.path + ", expires=" + cookie.expires + ", httpOnly="
				+ cookie.httpOnly + ", secure=" + cookie.secure + ", sameSite=" + cookie.sameSite + "]";

	}

	public static void main(String[] args) {
		try (Playwright playwright = Playwright.create();
				Browser browser = playwright.chromium()
						.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
				Page page = browser.newPage();) {

			page.navigate("https://self-learning-java-tutorial.blogspot.com/2014/03/features-of-java.html");

			System.out.println("Print all the cookies");
			List<Cookie> cookies = page.context().cookies();
			for (Cookie cookie : cookies) {
				System.out.println(cookieAsStr(cookie));
			}

		}
	}

}

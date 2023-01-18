package com.sample.app.miscellaneous;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class CustomizeTimeoutDemo {

	public static void main(String[] args) {
		try (Playwright playwright = Playwright.create();
				Browser browser = playwright.chromium().launch();
				BrowserContext browserContext = browser.newContext();) {

			browserContext.setDefaultTimeout(5000);
			browserContext.setDefaultNavigationTimeout(5000);

			Page page = browser.newPage();
			page.navigate("https://self-learning-java-tutorial.blogspot.com/");
			System.out.println(page.title());
		}
	}

}

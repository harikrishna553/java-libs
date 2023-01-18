package com.sample.app.miscellaneous;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PrintPageSource {

	public static void main(String[] args) {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			Page page = browser.newPage();
			page.navigate("https://self-learning-java-tutorial.blogspot.com/2014/03/features-of-java.html");
			System.out.println(page.content());
		}
	}

}

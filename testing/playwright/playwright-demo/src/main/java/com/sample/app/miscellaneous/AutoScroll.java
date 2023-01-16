package com.sample.app.miscellaneous;

import java.util.concurrent.TimeUnit;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class AutoScroll {

	public static void main(String[] args) throws InterruptedException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			Page page = browser.newPage();
			page.navigate("https://self-learning-java-tutorial.blogspot.com/2014/07/basic-java.html");

			for (int i = 0; i < 5; i++) {
				page.mouse().wheel(0, 1000);
				TimeUnit.SECONDS.sleep(2);
			}
		}
	}

}

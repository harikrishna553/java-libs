package com.sample.app.miscellaneous;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Browser.NewContextOptions;

public class SetBaseURL {

	public static void main(String[] args) throws InterruptedException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));

			NewContextOptions newContextOptions = new Browser.NewContextOptions()
					.setBaseURL("https://self-learning-java-tutorial.blogspot.com");
			BrowserContext browserContext = browser.newContext(newContextOptions);

			Page page = browserContext.newPage();
			page.navigate("2014/01/data-types-in-java_31.html");
			System.out.println("title : " + page.title());
			
			page.navigate("2014/01/running-simple-hello-world-applcation.html");
			System.out.println("title : " + page.title());
		}
	}
}

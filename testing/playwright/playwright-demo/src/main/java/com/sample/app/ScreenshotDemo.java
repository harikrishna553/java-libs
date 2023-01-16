package com.sample.app;

import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class ScreenshotDemo {

	public static void main(String[] args) {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			Page page = browser.newPage();
			page.navigate("https://self-learning-java-tutorial.blogspot.com/");
			System.out.println(page.title());
			String filePath = "/Users/Shared/playwright/demo.png";
			page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(filePath)));
		}
	}

}

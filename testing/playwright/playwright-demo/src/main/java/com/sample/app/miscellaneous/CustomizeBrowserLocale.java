package com.sample.app.miscellaneous;

import java.io.IOException;
import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Browser.NewContextOptions;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class CustomizeBrowserLocale {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));

			NewContextOptions newContextOptions = new Browser.NewContextOptions().setLocale("de-DE");
			BrowserContext browserContext = browser.newContext(newContextOptions);
			Page page = browserContext.newPage();

			page.navigate("https://google.com");

			String filePath = "/Users/Shared/playwright/demo.png";
			page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(filePath)));

		}
	}
}

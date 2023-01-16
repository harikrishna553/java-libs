package com.sample.app.miscellaneous;

import java.nio.file.Paths;
import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class GoogleSearchResult {

	public static void main(String[] args) {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));

			Page page = browser.newPage();
			page.navigate("https://google.com");

			page.fill("input[name=q]", "self learning java blogspot");
			page.click("input[value=\"Google Search\"]");

			page.waitForNavigation(() -> {
				System.out.println("Waiting for search result\n");
			});

			List<String> allTitles = page.locator("h3[class=\"LC20lb MBeuO DKV0Md\"]").allTextContents();
			for (String title : allTitles) {
				System.out.println(title);
			}

			String filePath = "/Users/Shared/playwright/search.png";
			page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(filePath)));

		}
	}

}

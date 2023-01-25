package com.sample.app.locators;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class HighlightMatchingElements {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {

			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));

			final String content = FileUtil.resourceAsString("locators" + File.separator + "highlightDemo.html");

			Page page = browser.newPage();
			page.setContent(content);

			page.locator(".impt").highlight();
			String filePath = "/Users/Shared/playwright/demo.png";
			page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(filePath)));
		}
	}
}

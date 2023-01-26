package com.sample.app.locators;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class SelectTextDemo {
	public static void main(String[] args) throws IOException, InterruptedException {
		try (Playwright playwright = Playwright.create()) {

			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));

			final String content = FileUtil.resourceAsString("locators" + File.separator + "selectText.html");

			Page page = browser.newPage();
			page.setContent(content);

			page.locator("#h1").selectText();
			String filePath = "/Users/Shared/playwright/screenShot1.png";
			page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(filePath)));

			page.locator("#features").selectText();
			filePath = "/Users/Shared/playwright/screenShot2.png";
			page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(filePath)));
		}
	}
}

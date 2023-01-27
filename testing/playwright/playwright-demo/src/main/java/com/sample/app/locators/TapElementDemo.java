package com.sample.app.locators;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class TapElementDemo {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {

			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			
			BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions().setHasTouch(true));

			final String content = FileUtil.resourceAsString("locators" + File.separator + "tapElement.html");

			Page page = browserContext.newPage();
			page.setContent(content);

			page.locator("#favoriteColors").locator("option[value=\"red\"]").tap();
			page.locator("input[type=\"submit\"]").tap();

			String textContent = page.locator("id=result").textContent();
			System.out.println(textContent);
		}
	}
}

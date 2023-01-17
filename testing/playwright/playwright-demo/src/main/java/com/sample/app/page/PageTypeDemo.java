package com.sample.app.page;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class PageTypeDemo {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("locators" + File.separator + "loginPage.html");

			Page page = browser.newPage();
			page.setContent(content);

			page.type("#email", "demo@demo.com", new Page.TypeOptions().setDelay(100));
			page.type("#password", "demo123");
			page.type("#confirm-password", "demo123");

			page.click("input[type=\"submit\"]");

			String result = page.locator("#result").textContent();
			System.out.println(result);

		}
	}
}

package com.sample.app.locators;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class ElementEnableCheck {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {

			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));

			final String content = FileUtil.resourceAsString("locators" + File.separator + "readOnlyCheck.html");

			Page page = browser.newPage();
			page.setContent(content);

			boolean isNameEnabled = page.locator("#name").isEnabled();
			boolean isAgeEnabled = page.locator("#age").isEnabled();
			boolean isCountryEnabled = page.locator("#country").isEnabled();

			System.out.println("isNameEnabled : " + isNameEnabled);
			System.out.println("isAgeEnabled : " + isAgeEnabled);
			System.out.println("isCountryEnabled : " + isCountryEnabled);

		}
	}
}

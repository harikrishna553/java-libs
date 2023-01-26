package com.sample.app.locators;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class ElementHiddenCheck {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {

			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));

			final String content = FileUtil.resourceAsString("locators" + File.separator + "elementHiddenCheck.html");

			Page page = browser.newPage();
			page.setContent(content);

			boolean isNameHidden = page.locator("#name").isHidden();
			boolean isAgeHidden = page.locator("#age").isHidden();
			boolean isCountryHidden = page.locator("#country").isHidden();
			boolean isGenderHidden = page.locator("#gender").isHidden();

			System.out.println("isNameHidden : " + isNameHidden);
			System.out.println("isAgeHidden : " + isAgeHidden);
			System.out.println("isCountryHidden : " + isCountryHidden);
			System.out.println("isGenderHidden : " + isGenderHidden);

		}
	}
}

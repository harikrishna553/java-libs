package com.sample.app.locators;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class EditableCheckDemo {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {

			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));

			final String content = FileUtil.resourceAsString("locators" + File.separator + "readOnlyCheck.html");

			Page page = browser.newPage();
			page.setContent(content);

			boolean isNameEditable = page.locator("#name").isEditable();
			boolean isAgeEditable = page.locator("#age").isEditable();
			boolean isCountryEditable = page.locator("#country").isEditable();

			System.out.println("isNameEditable : " + isNameEditable);
			System.out.println("isAgeEditable : " + isAgeEditable);
			System.out.println("isCountryEditable : " + isCountryEditable);

		}
	}
}

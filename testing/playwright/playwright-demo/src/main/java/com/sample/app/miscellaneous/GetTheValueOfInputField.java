package com.sample.app.miscellaneous;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class GetTheValueOfInputField {

	private static void printValueBySelector(Page page, String selector, String label) {
		String value1 = page.inputValue(selector);
		String value2 = page.locator(selector).inputValue();

		System.out.println(label + " : " + value1);
		System.out.println(label + ": " + value2);
	}

	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("locators" + File.separator + "loginPage.html");

			Page page = browser.newPage();
			page.setContent(content);

			page.locator("input#email").fill("demo@abc.com");
			page.locator("input#password").fill("test1234");
			page.locator("input#confirm-password").fill("test1234");

			printValueBySelector(page, "input#email", "email");
			printValueBySelector(page, "input#password", "password");
			printValueBySelector(page, "input#confirm-password", "email");

		}
	}

}

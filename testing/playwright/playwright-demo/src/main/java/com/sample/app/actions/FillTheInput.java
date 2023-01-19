package com.sample.app.actions;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class FillTheInput {
	private static void printValueBySelector(Page page, String selector, String label) {
		String value1 = page.inputValue(selector);

		System.out.println(label + " : " + value1);
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("actions" + File.separator + "registerMe.html");

			Page page = browser.newPage();
			page.setContent(content);

			page.getByLabel("email").fill("demo@abc.com");
			page.locator("input#password").fill("test1234");
			page.locator("input#confirm-password").fill("test1234");

			printValueBySelector(page, "input#email", "email");
			printValueBySelector(page, "input#password", "password");
			printValueBySelector(page, "input#confirm-password", "confirm password");

			TimeUnit.SECONDS.sleep(3);

		}
	}
}

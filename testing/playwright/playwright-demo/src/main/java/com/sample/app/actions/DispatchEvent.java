package com.sample.app.actions;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class DispatchEvent {
	private static void printValueBySelector(Page page, String selector, String label) {
		String value1 = page.inputValue(selector);

		System.out.println(label + " : " + value1);
	}

	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("actions" + File.separator + "registerMe.html");

			Page page = browser.newPage();
			page.setContent(content);

			page.getByLabel("email").type("demo@demo.com");
			page.locator("input#password").type("test1234");
			page.locator("input#confirm-password").type("test1234");

			printValueBySelector(page, "input#email", "email");
			printValueBySelector(page, "input#password", "password");
			printValueBySelector(page, "input#confirm-password", "confirm password");

			System.out.println("Dispatching click event");
			page.locator("input[type=\"submit\"]").dispatchEvent("click");
		
			String result = page.locator("#result").textContent();
			System.out.println(result);

		}
	}
}

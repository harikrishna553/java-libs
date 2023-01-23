package com.sample.app.locators;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class ClearTheData {

	public static void main(String[] args) throws IOException, InterruptedException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("actions" + File.separator + "clearInput.html");

			Page page = browser.newPage();
			page.setContent(content);

			System.out.println("Typing the text 'Hello world!!!!'");
			page.locator("#myData").type("Hello world!!!!");
			String textContent = page.locator("#myData").inputValue();
			System.out.println("textContent : '" + textContent + "'");

			System.out.println("\nClearing the text");
			page.locator("#myData").clear();
			textContent = page.locator("#myData").inputValue();
			System.out.println("textContent : '" + textContent + "'");
		}
	}

}

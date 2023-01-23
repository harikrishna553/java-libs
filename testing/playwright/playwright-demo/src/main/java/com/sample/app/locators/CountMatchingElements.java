package com.sample.app.locators;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class CountMatchingElements {

	public static void main(String[] args) throws IOException, InterruptedException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil
					.resourceAsString("locators" + File.separator + "countMatchingElements.html");

			Page page = browser.newPage();
			page.setContent(content);

			int count = page.locator(".para").count();
			System.out.println("Total matching elements with the class 'para' are : " + count);

			count = page.locator("p").count();
			System.out.println("Total paragraph elements are : " + count);
		}
	}

}

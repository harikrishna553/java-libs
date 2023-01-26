package com.sample.app.locators;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class GetPageReferenceByLocator {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {

			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));

			final String content = FileUtil.resourceAsString("locators" + File.separator + "pageReference.html");

			Page page = browser.newPage();
			page.setContent(content);

			Locator firstMatchingElement = page.locator(".para").first();
			System.out.println("firstMatchingElement : " + firstMatchingElement.textContent());

			String pageTitle = firstMatchingElement.page().title();
			System.out.println("pageTitle : " + pageTitle);

		}
	}
}

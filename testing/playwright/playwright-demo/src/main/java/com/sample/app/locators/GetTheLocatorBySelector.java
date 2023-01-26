package com.sample.app.locators;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class GetTheLocatorBySelector {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {

			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));

			final String content = FileUtil.resourceAsString("locators" + File.separator + "locatorBySelector.html");

			Page page = browser.newPage();
			page.setContent(content);

			Locator locatorByClass = page.locator(".para");
			Locator locatorById = page.locator("#para1");
			Locator locatorByAttribute = page.locator("p[type=\"text\"]");
			Locator locatorByXPath = page.locator("//title");

			System.out.println("Content matched to locatorByClass : ");
			List<String> allTextContent = locatorByClass.allTextContents();
			for (String str : allTextContent) {
				System.out.println(str);
			}

			System.out.println("\nlocatorById : " + locatorById.textContent());
			System.out.println("\nlocatorByAttribute : " + locatorByAttribute.textContent());
			System.out.println("\nlocatorByXPath : " + locatorByXPath.textContent());

		}
	}
}

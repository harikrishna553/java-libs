package com.sample.app.locators;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class GetAllTheMatchedElements {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			
			final String content = FileUtil
					.resourceAsString("miscellaneous" + File.separator + "collectionOfElements.html");

			Page page = browser.newPage();
			page.setContent(content);

			String classSelector = "p[class=\"mypara\"]";
			List<String> allParagraphs = page.locator(classSelector).allTextContents();

			System.out.println("allParagraphs : " + allParagraphs);
		}
	}
}

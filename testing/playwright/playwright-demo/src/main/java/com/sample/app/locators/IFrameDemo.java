package com.sample.app.locators;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class IFrameDemo {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {

			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));

			final String content = FileUtil.resourceAsString("locators" + File.separator + "iframe.html");

			Page page = browser.newPage();
			page.setContent(content);

			FrameLocator frameLocator = page.frameLocator("#javaTutorial");
			System.out.println("title : " + frameLocator.locator("title").textContent());

			frameLocator = page.frameLocator("#springFrameworkTutorial");
			System.out.println("title : " + frameLocator.locator("title").textContent());

		}
	}
}

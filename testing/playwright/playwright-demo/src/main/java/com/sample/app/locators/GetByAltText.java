package com.sample.app.locators;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class GetByAltText {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {

			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));

			final String content = FileUtil.resourceAsString("locators" + File.separator + "altDemo.html");

			Page page = browser.newPage();
			page.setContent(content);

			ElementHandle elementHandle = page.getByAltText("plantuml").elementHandle();
			String src = elementHandle.getAttribute("src");
			System.out.println("This element points to the src : " + src);
		}
	}
}

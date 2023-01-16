package com.sample.app.miscellaneous;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class ElementAttributesDemo {

	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil
					.resourceAsString("miscellaneous" + File.separator + "elementAttributes.html");

			Page page = browser.newPage();
			page.setContent(content);

			Locator loctor = page.locator("p#para1");
			String clazz = loctor.getAttribute("class");
			String id = loctor.getAttribute("id");

			System.out.println("class : " + clazz);
			System.out.println("id : " + id);

		}
	}

}

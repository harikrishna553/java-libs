package com.sample.app.locators;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class LocatorByTitle {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			
			final String content = FileUtil
					.resourceAsString("locators" + File.separator + "titleDemo.html");

			Page page = browser.newPage();
			page.setContent(content);

			String text = page.getByTitle("para1").textContent();
			System.out.println("Cotent associated with title 'para1' is : " + text);
			
			text = page.getByTitle("para2").textContent();
			System.out.println("Cotent associated with title 'para2' is : " + text);
		}
	}
}

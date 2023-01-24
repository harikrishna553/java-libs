package com.sample.app.locators;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class GetLocatorByPlaceHolder {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {

			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));

			final String content = FileUtil.resourceAsString("locators" + File.separator + "placeHolderDemo.html");

			Page page = browser.newPage();
			page.setContent(content);

			ElementHandle elementHandle = page.getByPlaceholder("Enter your email id").elementHandle();
			String name = elementHandle.getAttribute("name");
			String id = elementHandle.getAttribute("id");
			String placeholder = elementHandle.getAttribute("placeholder");

			System.out.println("name : " + name);
			System.out.println("id : " + id);
			System.out.println("placeholder : " + placeholder);
		}
	}
}

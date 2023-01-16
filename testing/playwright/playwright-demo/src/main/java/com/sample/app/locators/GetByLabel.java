package com.sample.app.locators;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class GetByLabel {

	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("locators" + File.separator + "labelDemo.html");

			Page page = browser.newPage();
			page.setContent(content);

			page.getByLabel("Java").click();
			page.locator("input[type=\"submit\"]").click();

			String textContent = page.locator("id=result").textContent();
			System.out.println(textContent);
		}
	}

}

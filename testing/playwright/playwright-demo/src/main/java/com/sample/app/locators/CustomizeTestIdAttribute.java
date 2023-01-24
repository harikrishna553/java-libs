package com.sample.app.locators;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class CustomizeTestIdAttribute {

	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			
			playwright.selectors().setTestIdAttribute("my-data-testid");
			
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("locators" + File.separator + "customizeTestId.html");

			Page page = browser.newPage();
			page.setContent(content);

			String textContent = page.getByTestId("banner").textContent();
			System.out.println("Banner : " + textContent);
		}
	}

}

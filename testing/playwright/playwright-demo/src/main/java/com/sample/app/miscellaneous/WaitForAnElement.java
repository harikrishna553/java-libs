package com.sample.app.miscellaneous;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.WaitForSelectorOptions;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class WaitForAnElement {

	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("miscellaneous" + File.separator + "waitForElement.html");

			Page page = browser.newPage();
			page.setContent(content);

			WaitForSelectorOptions waitForSelectorOptions = new WaitForSelectorOptions();
			waitForSelectorOptions.setTimeout(5000);

			String resultData = page.waitForSelector("#result", waitForSelectorOptions).textContent();
			System.out.println(resultData);

		}
	}

}

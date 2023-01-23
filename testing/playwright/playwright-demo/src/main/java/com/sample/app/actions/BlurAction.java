package com.sample.app.actions;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class BlurAction {

	public static void main(String[] args) throws IOException, InterruptedException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("actions" + File.separator + "blurAction.html");

			Page page = browser.newPage();
			page.setContent(content);

			page.locator("#myData").type("Hello world!!!!");
			page.locator("#myData").blur();
			String textContent = page.locator("#myData").inputValue();

			System.out.println(textContent);

		}
	}

}

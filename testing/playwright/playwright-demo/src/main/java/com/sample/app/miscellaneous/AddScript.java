package com.sample.app.miscellaneous;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class AddScript {

	public static void main(String[] args) throws IOException, InterruptedException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil
					.resourceAsString("scripts" + File.separator + "hello.html");

			Page page = browser.newPage();
			page.setContent(content);

			page.addInitScript("let name = 'Krishna';\n"
					+ "let age = 34;");

			TimeUnit.SECONDS.sleep(10);

		}
	}

}

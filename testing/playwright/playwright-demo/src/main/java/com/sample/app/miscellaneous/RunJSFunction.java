package com.sample.app.miscellaneous;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class RunJSFunction {

	public static void main(String[] args) throws IOException, InterruptedException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("miscellaneous" + File.separator + "runJs.html");

			Page page = browser.newPage();
			page.setContent(content);

			String title = page.locator("#defaultTitle").textContent();
			System.out.println("title : " + title);

			TimeUnit.SECONDS.sleep(2);

			page.evaluate("document.getElementById('defaultTitle').innerHTML = 'Changed the title';");

			title = page.locator("#defaultTitle").textContent();
			System.out.println("title : " + title);

			TimeUnit.SECONDS.sleep(2);

		}
	}

}

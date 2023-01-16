package com.sample.app.miscellaneous;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class ReadTextboxValue {

	public static void main(String[] args) throws IOException {

		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("miscellaneous" + File.separator + "readTextBox.html");

			Page page = browser.newPage();
			page.setContent(content);

			page.locator("input#name").fill("Krishna");
			page.locator("input#age").fill("34");

			String name = page.inputValue("#name");
			String age = page.locator("input#age").inputValue();

			System.out.println("name : " + name);
			System.out.println("age : " + age);

		}

	}

}

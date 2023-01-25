package com.sample.app.locators;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class GetLocatorByTextContent {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {

			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));

			final String content = FileUtil.resourceAsString("locators" + File.separator + "locatorByTextContent.html");

			Page page = browser.newPage();
			page.setContent(content);

			System.out.println("All the locators match to the text 'Hello there!!!'");
			List<String> textContent = page.getByText("Hello there!!!").allTextContents();
			for (String str : textContent) {
				System.out.println(str);
			}

			System.out.println("\nAll the locators contains the text 'hello' and case insensitive");
			Pattern helloPattern = Pattern.compile("hello", Pattern.CASE_INSENSITIVE);
			textContent = page.getByText(helloPattern).allTextContents();
			for (String str : textContent) {
				System.out.println(str);
			}

			System.out.println("\nAll the locators contains the text 'hello' only");
			textContent = page.getByText("hello", new Page.GetByTextOptions().setExact(true)).allTextContents();
			for (String str : textContent) {
				System.out.println(str);
			}

		}
	}
}

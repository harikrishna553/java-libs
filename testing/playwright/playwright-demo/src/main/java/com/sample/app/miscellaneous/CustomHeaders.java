package com.sample.app.miscellaneous;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class CustomHeaders {

	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			Page page = browser.newPage();

			Map<String, String> headers = new HashMap<>();
			headers.put("header1", "value1");
			headers.put("header2", "value2");
			headers.put("header3", "value3");

			page.setExtraHTTPHeaders(headers);
			page.navigate("https://self-learning-java-tutorial.blogspot.com/");

		}
	}

}
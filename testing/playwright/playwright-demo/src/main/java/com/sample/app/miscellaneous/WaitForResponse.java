package com.sample.app.miscellaneous;

import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class WaitForResponse {

	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));

			Page page = browser.newPage();

			page.waitForResponse(response -> {
				String url = response.url();
				System.out.println("processed url : " + url);

				boolean isMatched = url.contains("google.com");

				if (isMatched) {
					System.out.println("Received matching response");
				}
				return isMatched;
			}, () -> {
				page.navigate("https://self-learning-java-tutorial.blogspot.com/");
			});

			System.out.println("title : " + page.title());

		}
	}

}

package com.sample.app;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class HelloWorld {

	public static void main(String[] args) {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium().launch();
			Page page = browser.newPage();
			page.navigate("https://self-learning-java-tutorial.blogspot.com/");
			System.out.println(page.title());
		}
	}

}

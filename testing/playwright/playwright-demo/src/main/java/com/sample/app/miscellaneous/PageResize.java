package com.sample.app.miscellaneous;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.concurrent.TimeUnit;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PageResize {

	public static void main(String[] args) throws InterruptedException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			Page page = browser.newPage();
			page.navigate("https://self-learning-java-tutorial.blogspot.com/");
			TimeUnit.SECONDS.sleep(3);

			System.out.println("Resizing the page");
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int screenWidth = dimension.width;
			int screenHeight = dimension.height;
			page.setViewportSize((screenWidth / 2), (screenHeight / 2));

			TimeUnit.SECONDS.sleep(3);
		}
	}

}

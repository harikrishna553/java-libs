package com.sample.app.locators;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.BoundingBox;
import com.sample.app.util.FileUtil;

public class BoundingBoxDemo {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {

			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));

			final String content = FileUtil.resourceAsString("locators" + File.separator + "boundingBox.html");

			Page page = browser.newPage();
			page.setContent(content);

			BoundingBox boundingBox = page.locator(".div1").boundingBox();

			double height = boundingBox.height;
			double width = boundingBox.width;
			double x = boundingBox.x;
			double y = boundingBox.y;

			System.out.println("height : " + height);
			System.out.println("width : " + width);
			System.out.println("x : " + x);
			System.out.println("y : " + y);

		}
	}
}

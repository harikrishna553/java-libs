package com.sample.app.miscellaneous;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class GetAllStylesOfElement {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("miscellaneous" + File.separator + "stylesOfElement.html");

			Page page = browser.newPage();
			page.setContent(content);

			Locator locator1 = page.locator("#para1");
			Map<String, Object> styles1 = (Map<String, Object>) locator1
					.evaluate("locator => getComputedStyle(locator)");

			Locator locator2 = page.locator("#para2");
			Map<String, Object> styles2 = (Map<String, Object>) locator2
					.evaluate("locator => getComputedStyle(locator)");

			System.out.println("Styles associated with the id para1 are : ");
			System.out.println("width : " + styles1.get("width"));
			System.out.println("margin : " + styles1.get("margin"));
			System.out.println("padding : " + styles1.get("padding"));
			System.out.println("font : " + styles1.get("font"));
			System.out.println("text-align : " + styles1.get("textAlign"));
			System.out.println("background : " + styles1.get("background"));
			System.out.println("color : " + styles1.get("color"));

			System.out.println("\n\nStyles associated with the id para2 are : ");
			System.out.println("width : " + styles2.get("width"));
			System.out.println("margin : " + styles2.get("margin"));
			System.out.println("padding : " + styles2.get("padding"));
			System.out.println("font : " + styles2.get("font"));
			System.out.println("text-align : " + styles2.get("textAlign"));
			System.out.println("background : " + styles2.get("background"));
			System.out.println("color : " + styles2.get("color"));
		}
	}
}

package com.sample.app.miscellaneous;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class ElementVisibleCheck {

	public static void elementVisibleCheck(final Page page, final String selector) {
		boolean isVisible1 = page.locator(selector).isVisible();
		boolean isVisible2 = page.isVisible(selector);
		boolean isVisible3 = (page.locator(selector).count() > 0 ? true : false);

		System.out.println("\nis the element with selector " + selector + " visible : " + isVisible1);
		System.out.println("is the element with selector " + selector + " visible : " + isVisible2);
		System.out.println("is the element with selector " + selector + " visible : " + isVisible3);
	}

	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("locators" + File.separator + "elementVisibleCheck.html");

			Page page = browser.newPage();
			page.setContent(content);

			String idSelector = "p[id=\"myPara1\"]";
			elementVisibleCheck(page, idSelector);

			idSelector = "p[id=\"myPara2\"]";
			elementVisibleCheck(page, idSelector);

		}
	}

}

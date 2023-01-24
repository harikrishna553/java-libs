package com.sample.app.locators;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import com.sample.app.util.FileUtil;

public class GetLocatorByAriaRoles {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {

			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));

			final String content = FileUtil.resourceAsString("locators" + File.separator + "ariaRoles.html");

			Page page = browser.newPage();
			page.setContent(content);

			ElementHandle elementHandle = page.getByRole(AriaRole.BANNER).elementHandle();

			String role = elementHandle.getAttribute("role");
			String id = elementHandle.getAttribute("id");
			String clazz = elementHandle.getAttribute("class");

			System.out.println("role : " + role);
			System.out.println("id : " + id);
			System.out.println("class : " + clazz);
		}
	}
}

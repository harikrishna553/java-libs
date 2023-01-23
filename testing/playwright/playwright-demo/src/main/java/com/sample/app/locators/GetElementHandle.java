package com.sample.app.locators;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class GetElementHandle {

	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("locators" + File.separator + "elementHandle.html");

			Page page = browser.newPage();
			page.setContent(content);

			System.out.println("Print one element handle");
			ElementHandle elementHandle = page.locator("#para3").elementHandle();
			System.out.println(elementHandle.textContent());

			System.out.println("\nPrint all the matching element handles");
			List<ElementHandle> elementHandles = page.locator(".para").elementHandles();
			for (ElementHandle elementHandleTemp : elementHandles) {
				System.out.println(elementHandleTemp.textContent());
			}

		}
	}

}
package com.sample.app.miscellaneous;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class ElementsRelativeToThisElement {

	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil
					.resourceAsString("miscellaneous" + File.separator + "relativeElements.html");

			Page page = browser.newPage();
			page.setContent(content);

			ElementHandle elementHandle = page.querySelector("#para1");
			ElementHandle grandParent = elementHandle.querySelector("xpath=../..");
			List<ElementHandle> siblings = elementHandle.querySelectorAll("xpath=following-sibling::*");

			System.out.println("elementHandle : " + elementHandle.getAttribute("id"));
			System.out.println("grandParent : " + grandParent.getAttribute("id"));

			System.out.println("Siblings");
			for (ElementHandle sibling : siblings) {
				System.out.println("\t" + sibling.getAttribute("id"));
			}

		}
	}

}

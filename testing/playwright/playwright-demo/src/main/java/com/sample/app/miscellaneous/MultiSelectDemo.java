package com.sample.app.miscellaneous;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class MultiSelectDemo {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {

			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));

			final String content = FileUtil.resourceAsString("miscellaneous" + File.separator + "multiSelect.html");

			Page page = browser.newPage();
			page.setContent(content);

			String valuesToSelect[] = { "red", "green", "yellow" };
			page.selectOption("select#favoriteColors", valuesToSelect);
			
			page.locator("input[type=\"submit\"]").click();
			
			
			String textContent = page.locator("id=result").textContent();
			System.out.println(textContent);
		}
	}
}

package com.sample.app.miscellaneous;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator.ClickOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.KeyboardModifier;
import com.sample.app.util.FileUtil;

public class SwitchBetweenTabs {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("miscellaneous" + File.separator + "openInNewTab.html");

			Page page = browser.newPage();
			page.setContent(content);

			ClickOptions clickOptions = new ClickOptions();

			List<KeyboardModifier> modifiers = Arrays.asList(KeyboardModifier.META);
			clickOptions.setModifiers(modifiers);
			page.locator("a#java").click(clickOptions);
			page.locator("a#playwright").click(clickOptions);

			/* wait for sometime, so all the pages will be loaded */
			page.waitForTimeout(8000);

			// Get all the opened pages in the context
			List<Page> pages = page.context().pages();
			for (Page tempPage : pages) {
				System.out.println("Bringing the page '" + tempPage.title() + "' to the front");
				tempPage.bringToFront();
				tempPage.waitForTimeout(2000);
			}

		}
	}
}

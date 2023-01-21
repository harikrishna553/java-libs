package com.sample.app.actions;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator.ClickOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.MouseButton;
import com.sample.app.util.FileUtil;

public class MouseActions {

	public static void main(String[] args) throws IOException, InterruptedException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("actions" + File.separator + "mouseActions.html");

			Page page = browser.newPage();
			page.setContent(content);

			page.locator("#dbClick").dblclick();
			String result = page.locator("#result").textContent();
			System.out.println(result);

			page.locator("#click").click();
			result = page.locator("#result").textContent();
			System.out.println(result);

			page.locator("#hover").hover();
			result = page.locator("#result").textContent();
			System.out.println(result);

			ClickOptions clickOptions = new ClickOptions();
			clickOptions.setButton(MouseButton.LEFT);
			page.locator("#mouseButtons").click(clickOptions);
			result = page.locator("#result").textContent();
			System.out.println(result);

			clickOptions.setButton(MouseButton.RIGHT);
			page.locator("#mouseButtons").click(clickOptions);
			result = page.locator("#result").textContent();
			System.out.println(result);

			clickOptions.setButton(MouseButton.MIDDLE);
			page.locator("#mouseButtons").click(clickOptions);
			result = page.locator("#result").textContent();
			System.out.println(result);

		}
	}

}

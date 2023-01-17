package com.sample.app.miscellaneous;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class DragAndDrop {

	public static void main(String[] args) throws IOException {

		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("miscellaneous" + File.separator + "dragAndDrop.html");

			Page page = browser.newPage();
			page.setContent(content);

			String filePath = "/Users/Shared/playwright/before.png";
			page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(filePath)));

			page.dragAndDrop("#circle", "#square");

			filePath = "/Users/Shared/playwright/after.png";
			page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(filePath)));
		}

	}

}

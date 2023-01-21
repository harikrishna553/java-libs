package com.sample.app.actions;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class KeyboardEvents {
	public static void main(String[] args) throws IOException, InterruptedException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("actions" + File.separator + "keyEvents.html");

			Page page = browser.newPage();
			page.setContent(content);

			page.locator("#demoInputField").press("A");
			page.locator("#demoInputField").press("a");
			page.locator("#demoInputField").press("$");

			// Following modification shortcuts are also supported: Shift, Control, Alt,
			// Meta
			page.locator("#demoInputField").press("Shift+A");
			page.locator("#demoInputField").press("Meta");
			page.locator("#demoInputField").press("Control");
			page.locator("#demoInputField").press("Alt");
			page.locator("#demoInputField").press("CapsLock");
			page.locator("#demoInputField").press("Escape");

			page.locator("#demoInputField").press("Shift+ArrowLeft");
			page.locator("#demoInputField").press("Shift+ArrowRight");
			page.locator("#demoInputField").press("Shift+ArrowUp");
			page.locator("#demoInputField").press("Shift+ArrowDown");

			String result = page.locator("#result").textContent();
			System.out.println(result);

		}
	}

}
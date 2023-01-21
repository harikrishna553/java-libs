package com.sample.app.actions;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class FocusEvent {
	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("actions" + File.separator + "focusAction.html");

			Page page = browser.newPage();
			page.setContent(content);

			page.locator("#demo").focus();
			String result1, result2, result3;
			result1 = page.locator("#result1").innerText();
			result2 = page.locator("#result2").innerText();
			result3 = page.locator("#result3").innerText();
			System.out.println("result1 : " + result1);
			System.out.println("result2 : " + result2);
			System.out.println("result3 : " + result3);

			
			System.out.println("\nType hello to input box");
			page.locator("#demo").type("Hello");
			result1 = page.locator("#result1").textContent();
			result2 = page.locator("#result2").textContent();
			result3 = page.locator("#result3").textContent();
			System.out.println("result1 : " + result1);
			System.out.println("result2 : " + result2);
			System.out.println("result3 : " + result3);
			
			
			// To generate focus out event
			System.out.println("\nMove the cursor out of textbox");
			page.locator("#myButton").click();
			
			result1 = page.locator("#result1").textContent();
			result2 = page.locator("#result2").textContent();
			result3 = page.locator("#result3").textContent();
			System.out.println("result1 : " + result1);
			System.out.println("result2 : " + result2);
			System.out.println("result3 : " + result3);
			
			
		}
	}

}
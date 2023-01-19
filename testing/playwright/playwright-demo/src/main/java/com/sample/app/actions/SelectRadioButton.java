package com.sample.app.actions;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class SelectRadioButton {

	public static void main(String[] args) throws IOException, InterruptedException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("actions" + File.separator + "selectRadioButton.html");

			Page page = browser.newPage();
			page.setContent(content);

			System.out.println("Selecting Java langauge");
			page.getByLabel("java").click();
			System.out.println("Is Java langauge checked : " + page.getByLabel("java").isChecked());
			System.out.println("Is Go langauge checked : " + page.getByLabel("go").isChecked());
			System.out.println("Is Python language checked : " + page.getByLabel("python").isChecked());
			TimeUnit.SECONDS.sleep(2);

			System.out.println("\nSelecting Go langauge");
			page.getByLabel("go").check();
			System.out.println("Is Java langauge checked : " + page.getByLabel("java").isChecked());
			System.out.println("Is Go langauge checked : " + page.getByLabel("go").isChecked());
			System.out.println("Is Python language checked : " + page.getByLabel("python").isChecked());
			TimeUnit.SECONDS.sleep(2);

		}
	}

}

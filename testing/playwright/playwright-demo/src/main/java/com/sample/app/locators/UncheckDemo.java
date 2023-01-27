package com.sample.app.locators;

import java.io.File;
import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class UncheckDemo {

	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("actions" + File.separator + "checkbox.html");

			Page page = browser.newPage();
			page.setContent(content);

			System.out.println("Selecting Java, go and python langauges");
			page.getByLabel("java").setChecked(true);
			page.getByLabel("go").setChecked(true);
			page.getByLabel("python").setChecked(true);

			System.out.println("Is Java language checked : " + page.getByLabel("java").isChecked());
			System.out.println("Is Go language checked : " + page.getByLabel("go").isChecked());
			System.out.println("Is Python language checked : " + page.getByLabel("python").isChecked());

			System.out.println("\nDe-selecting Java, go and python langauges");
			page.getByLabel("java").uncheck();
			page.getByLabel("go").uncheck();
			page.getByLabel("python").uncheck();

			System.out.println("\nIs Java language checked : " + page.getByLabel("java").isChecked());
			System.out.println("Is Go language checked : " + page.getByLabel("go").isChecked());
			System.out.println("Is Python language checked : " + page.getByLabel("python").isChecked());

		}
	}

}

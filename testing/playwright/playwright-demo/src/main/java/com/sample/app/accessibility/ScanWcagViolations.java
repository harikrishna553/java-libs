package com.sample.app.accessibility;

import java.util.Arrays;
import java.util.List;

import com.deque.html.axecore.playwright.AxeBuilder;
import com.deque.html.axecore.results.AxeResults;
import com.deque.html.axecore.results.Rule;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class ScanWcagViolations {

	public static void main(String[] args) {

		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			BrowserContext context = browser.newContext();
			Page page = context.newPage();

			page.navigate("https://self-learning-java-tutorial.blogspot.com/2014/11/pretty-print-json-format.html");
			AxeResults accessibilityScanResults = new AxeBuilder(page)
					.withTags(Arrays.asList("wcag2a", "wcag2aa", "wcag21a", "wcag21aa")).analyze();

			List<Rule> violations = accessibilityScanResults.getViolations();
			for (Rule violation : violations) {
				System.out.println("impact : " + violation.getImpact());
				System.out.println("description : " + violation.getDescription());
				System.out.println("description : " + violation.getTags() + "\n");
			}

		}
	}
}
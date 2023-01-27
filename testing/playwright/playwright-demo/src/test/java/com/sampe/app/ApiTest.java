package com.sampe.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class ApiTest {

	private static Playwright playwright;
	private static Browser browser;

	// Get new instances for each call
	private BrowserContext context;
	private Page page;

	@BeforeAll
	static void launchBrowser() {
		playwright = Playwright.create();
		browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
	}

	@AfterAll
	static void closeBrowser() {
		playwright.close();
	}

	@BeforeEach
	void createContextAndPage() {
		context = browser.newContext();
		page = context.newPage();
	}

	@Test
	public void testAPI() {

		APIRequestContext request = playwright.request().newContext(
				new APIRequest.NewContextOptions().setBaseURL("https://self-learning-java-tutorial.blogspot.com"));

		APIResponse apiResponse = request.get("/2015/10/gson-convert-json-to-hashmap.html");

		int status = apiResponse.status();
		assertEquals(200, status);

		byte[] body = apiResponse.body();
		String str = new String(body);

		page.setContent(str);

		String title = page.locator("title").textContent();

		assertEquals("Programming for beginners: Gson: Convert Json to a HashMap", title.trim());

	}

}

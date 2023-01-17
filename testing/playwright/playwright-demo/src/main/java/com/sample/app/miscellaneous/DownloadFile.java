package com.sample.app.miscellaneous;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Response;

public class DownloadFile {

	public static boolean writeContent(byte[] content, String filePath) {
		if (filePath == null || content == null) {
			throw new IllegalArgumentException("filePath and content must not be null");
		}

		Path file = Paths.get(filePath);
		try {
			Files.write(file, content);
		} catch (IOException e) {
			return false;
		}

		return true;

	}

	public static void main(String[] args) {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			Page page = browser.newPage();
			String url = "https://github.com/harikrishna553/java-libs/raw/master/plantUMLExamples/activityDiagrams/backward.png";
			Response response = page.navigate(url);

			// response.finished();
			byte[] body = response.body();
			String filePath = "/Users/Shared/playwright/backward.png";
			writeContent(body, filePath);

		}
	}

}

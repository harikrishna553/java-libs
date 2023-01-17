package com.sample.app.miscellaneous;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class UploadMultipleFiles {

	public static String resourceFilePath(String resourceFile) {
		ClassLoader classLoader = FileUploadDemo.class.getClassLoader();
		URL url = classLoader.getResource(resourceFile);
		return url.getPath();
	}

	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("miscellaneous" + File.separator + "multiFileUpload.html");

			Page page = browser.newPage();
			page.setContent(content);

			String filePath1 = resourceFilePath("files" + File.separator + "emp.json");
			String filePath2 = resourceFilePath("files" + File.separator + "address.json");
			Path[] paths = { Paths.get(filePath1), Paths.get(filePath2) };
			page.locator("#myFile").setInputFiles(paths);

			String text = page.locator("#fileContent").textContent();
			System.out.println("files content : \n" + text);

		}
	}
}

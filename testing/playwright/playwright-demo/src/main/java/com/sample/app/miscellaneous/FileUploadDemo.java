package com.sample.app.miscellaneous;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class FileUploadDemo {

	public static String resourceFilePath(String resourceFile) {
		ClassLoader classLoader = FileUploadDemo.class.getClassLoader();
		URL url = classLoader.getResource(resourceFile);
		return url.getPath();
	}

	public static void main(String[] args) throws IOException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
			final String content = FileUtil.resourceAsString("miscellaneous" + File.separator + "fileUpload.html");

			Page page = browser.newPage();
			page.setContent(content);

			String filePath = resourceFilePath("files" + File.separator + "emp.json");
			page.locator("#myFile").setInputFiles(Paths.get(filePath));

			String text = page.locator("#fileContent").textContent();
			System.out.println("file content : \n" + text);

		}
	}
}

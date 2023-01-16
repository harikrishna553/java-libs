package selectOption;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.sample.app.util.FileUtil;

public class SelectOption {
	public static void main(String[] args) throws IOException, InterruptedException {
		try (Playwright playwright = Playwright.create()) {

			Browser browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));

			final String content = FileUtil.resourceAsString("miscellaneous" + File.separator + "selectOption.html");

			Page page = browser.newPage();
			page.setContent(content);

			page.selectOption("select#favoriteColor", "yellow");

			page.locator("input[type=\"submit\"]").click();

			String textContent = page.locator("id=result").textContent();
			System.out.println(textContent);

			TimeUnit.SECONDS.sleep(3);
		}
	}
}

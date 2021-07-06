package mockwebserver;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.jupiter.api.Test;

import com.sample.app.SimpleRestClient;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class HelloWorldTest {

	@Test
	public void hello() throws IOException {
		try (MockWebServer mockWebServer = new MockWebServer();) {
			String msg1 = "Good Morning";
			String msg2 = "Good Afternoon";
			String msg3 = "Good Evening";

			mockWebServer.enqueue(new MockResponse().setBody(msg1));
			mockWebServer.enqueue(new MockResponse().setBody(msg2));
			mockWebServer.enqueue(new MockResponse().setBody(msg3));

			mockWebServer.start(1234);

			HttpUrl baseUrl = mockWebServer.url("/v1/welcome");

			SimpleRestClient simpleRestClient = new SimpleRestClient(baseUrl.toString());

			String body1 = simpleRestClient.getRequest();
			assertEquals(msg1, body1);

			String body2 = simpleRestClient.getRequest();
			assertEquals(msg2, body2);

			String body3 = simpleRestClient.getRequest();
			assertEquals(msg3, body3);

			mockWebServer.shutdown();
		}

	}

	@Test
	public void urlRedirection() throws IOException {
		try (MockWebServer mockWebServer = new MockWebServer();) {
			mockWebServer.start(1234);

			mockWebServer.enqueue(new MockResponse().setResponseCode(HttpURLConnection.HTTP_MOVED_TEMP)
					.addHeader("Location: " + mockWebServer.url("/new-path")).setBody("This page has moved!"));

			mockWebServer.enqueue(new MockResponse().setBody("This is the new location!"));

			HttpUrl baseUrl = mockWebServer.url("/v1/welcome");

			SimpleRestClient simpleRestClient = new SimpleRestClient(baseUrl.toString());

			CloseableHttpResponse httpResponse = simpleRestClient.getClosebaleResponse();

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			String body = SimpleRestClient.getResponseAsString(httpResponse);

			assertEquals(200, statusCode);
			assertEquals("This is the new location!", body);

			mockWebServer.shutdown();
		}

	}

	@Test()
	public void delayResponseTime() throws IOException {
		try (MockWebServer mockWebServer = new MockWebServer();) {
			mockWebServer.start(1234);

			mockWebServer.enqueue(new MockResponse().setBody("Hello World").setBodyDelay(5, TimeUnit.SECONDS));

			HttpUrl baseUrl = mockWebServer.url("/v1/welcome");

			SimpleRestClient simpleRestClient = new SimpleRestClient(baseUrl.toString());

			Throwable exception = assertThrows(SocketTimeoutException.class, () -> {
				String response = simpleRestClient.maxConnTimeOut(2);

				assertEquals("Hello World", response);
			});

			assertEquals("Read timed out", exception.getMessage());

			mockWebServer.shutdown();
		}
	}

}

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
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

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

	@Test
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

	@Test
	public void validateRequestBody() throws IOException, InterruptedException {
		try (MockWebServer mockWebServer = new MockWebServer();) {
			mockWebServer.start(1234);

			mockWebServer.enqueue(new MockResponse().setBody("Hello World"));

			HttpUrl baseUrl = mockWebServer.url("/v1/welcome");

			SimpleRestClient simpleRestClient = new SimpleRestClient(baseUrl.toString());
			simpleRestClient.postAndIgnore();

			RecordedRequest recordedRequest = mockWebServer.takeRequest();
			assertEquals("POST", recordedRequest.getMethod());
			assertEquals("name=Krishna&password=password&mailId=abcdef%40abcdef.com",
					recordedRequest.getBody().readUtf8());
			assertEquals("application/x-www-form-urlencoded; charset=UTF-8", recordedRequest.getHeader("Content-Type"));

			HttpUrl httpUrl = recordedRequest.getRequestUrl();
			assertEquals("http://localhost:1234/v1/welcome?appName=chatserver", httpUrl.toString());

			mockWebServer.shutdown();
		}
	}

	@Test
	public void customizeResponses() throws IOException, InterruptedException {
		String json = "{\"org\": \"abc_org\", \"location\": \"India\"}";

		try (MockWebServer mockWebServer = new MockWebServer();) {
			mockWebServer.start(1234);

			Dispatcher dispatcher = new Dispatcher() {

				@Override
				public MockResponse dispatch(RecordedRequest request) throws InterruptedException {

					switch (request.getPath()) {
					case "/v1/welcome1":
						return new MockResponse().setResponseCode(200).setBody("Good Morning");
					case "/v1/app-details":
						return new MockResponse().setResponseCode(200).setBody("version=9");
					case "/v1/owner":
						return new MockResponse().setResponseCode(200).setBody(json);
					}
					return new MockResponse().setResponseCode(404);
				}
			};
			mockWebServer.setDispatcher(dispatcher);

			SimpleRestClient simpleRestClient = new SimpleRestClient("http://localhost:1234/v1/app-details");
			String resp = simpleRestClient.getRequest();

			assertEquals("version=9", resp);

			mockWebServer.shutdown();
		}
	}

}
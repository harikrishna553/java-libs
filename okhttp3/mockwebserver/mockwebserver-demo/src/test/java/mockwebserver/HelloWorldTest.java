package mockwebserver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

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

}

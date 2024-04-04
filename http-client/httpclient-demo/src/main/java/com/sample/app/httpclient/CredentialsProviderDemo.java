package com.sample.app.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Collectors;

import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.Credentials;
import org.apache.hc.client5.http.auth.CredentialsProvider;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.EntityDetails;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpRequestInterceptor;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.protocol.HttpContext;

public class CredentialsProviderDemo {

	private static HttpHost targetHost = new HttpHost("http", "localhost", 8080);
	private static AuthScope authScope = new AuthScope(targetHost);
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "password123";

	public static String inputStreamToString(InputStream inputStream, Charset charSet) throws IOException {

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charSet))) {
			return reader.lines().collect(Collectors.joining("\n"));
		}

	}

	private static class CredsInterceptor implements HttpRequestInterceptor {

		@Override
		public void process(HttpRequest request, EntityDetails entity, HttpContext context)
				throws HttpException, IOException {
			CredentialsProvider provider = HttpClientContext.adapt(context).getCredentialsProvider();

			if (provider == null) {
				return;
			}

			Credentials credentials = provider.getCredentials(authScope, context);

			if (credentials instanceof UsernamePasswordCredentials) {
				UsernamePasswordCredentials creds = (UsernamePasswordCredentials) credentials;
				String encodedCredentials = Base64.getEncoder()
						.encodeToString((creds.getUserName() + ":" + new String(creds.getUserPassword()))
								.getBytes(StandardCharsets.UTF_8));
				request.addHeader("Authorization", "Basic " + encodedCredentials);
			}
		}

	}

	private static CredentialsProvider provider() {
		final BasicCredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(authScope, new UsernamePasswordCredentials(USERNAME, PASSWORD.toCharArray()));
		return provider;
	}

	public static void main(String[] args) throws Exception {

		HttpClientResponseHandler<Void> responseHandler = new HttpClientResponseHandler<Void>() {
			@Override
			public Void handleResponse(final ClassicHttpResponse response) throws IOException {

				System.out.println("Response code : " + response.getCode());

				HttpEntity httpEntity = response.getEntity();

				try (InputStream is = httpEntity.getContent()) {
					String result = inputStreamToString(is, StandardCharsets.UTF_8);
					System.out.println(result);
				}

				return null;

			}
		};

		String url = "http://localhost:8080/api/v1/basic-auth";

		try (CloseableHttpClient httpClient = HttpClients.custom().addRequestInterceptorFirst(new CredsInterceptor())
				.setDefaultCredentialsProvider(provider()).build();) {

			HttpGet httpGet = new HttpGet(url);
			httpClient.execute(httpGet, responseHandler);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

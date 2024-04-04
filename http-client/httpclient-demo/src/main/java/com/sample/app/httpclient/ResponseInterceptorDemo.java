package com.sample.app.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.EntityDetails;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpResponseInterceptor;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.protocol.HttpContext;

public class ResponseInterceptorDemo {

	private static class ResponseHeaderInterceptor implements HttpResponseInterceptor {

		@Override
		public void process(HttpResponse response, EntityDetails entity, HttpContext context)
				throws HttpException, IOException {
			System.out.println("Logging response headers by ResponseHeaderInterceptor");
			Header[] headers = response.getHeaders();

			for (Header header : headers) {
				System.out.println(header.getName() + " --> " + header.getValue());
			}
			System.out.println();

		}

	}

	private static HttpClientResponseHandler<String> responseHandler = new HttpClientResponseHandler<String>() {
		@Override
		public String handleResponse(final ClassicHttpResponse response) throws IOException, ParseException {

			HttpEntity httpEntity = response.getEntity();

			try (InputStream is = httpEntity.getContent()) {
				return inputStreamToString(is, StandardCharsets.UTF_8);
			}

		}
	};

	private static String inputStreamToString(InputStream inputStream, Charset charSet) throws IOException {

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charSet))) {
			return reader.lines().collect(Collectors.joining("\n"));
		}

	}

	public static void main(String[] args) {
		try (CloseableHttpClient httpClient = HttpClients.custom()
				.addResponseInterceptorFirst(new ResponseHeaderInterceptor()).build()) {

			ClassicHttpRequest request = new HttpGet("http://localhost:8080/api/v1/replay-request");
			String response = httpClient.execute(request, responseHandler);
			
			System.out.println("Response body :\n" + response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

package com.sample.app.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

public class HttpFluentApiDemo {

	private static String inputStreamToString(InputStream inputStream, Charset charSet) throws IOException {

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charSet))) {
			return reader.lines().collect(Collectors.joining("\n"));
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

	public static void main(String[] args) throws IOException {
		String url = "http://localhost:8080/api/v1/employees/1";

		Request request = Request.get(url);
		Response response = request.execute();
		String respMsg = response.handleResponse(responseHandler);
		System.out.println(respMsg);

		String payload = "{\n" + "  \"firstName\": \"Abhishek\",\n" + "  \"lastName\": \"Ramesh\"\n" + "}";

		request = Request.post("http://localhost:8080/api/v1/employees").addHeader("Content-Type", "application/json")
				.bodyString(payload, ContentType.APPLICATION_JSON);
		response = request.execute();
		respMsg = response.handleResponse(responseHandler);
		System.out.println(respMsg);
	}

}
package com.sample.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

public class ResponseTimeoutDemo {

	public static String inputStreamToString(InputStream inputStream, Charset charSet) throws IOException {

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charSet))) {
			return reader.lines().collect(Collectors.joining("\n"));
		}

	}

	public static void main(String[] args) {

		RequestConfig.Builder builder = RequestConfig.custom();
		builder.setResponseTimeout(5, TimeUnit.SECONDS);
		RequestConfig requestConfig = builder.build();

		HttpClientResponseHandler<String> responseHandler = new HttpClientResponseHandler<String>() {
			@Override
			public String handleResponse(final ClassicHttpResponse response) throws IOException, ParseException {

				HttpEntity httpEntity = response.getEntity();

				try (InputStream is = httpEntity.getContent()) {
					return inputStreamToString(is, StandardCharsets.UTF_8);
				}

			}
		};

		try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {

			ClassicHttpRequest request = new HttpGet("http://localhost:8080/api/v1/employees/1");

			String response = httpClient.execute(request, responseHandler);
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

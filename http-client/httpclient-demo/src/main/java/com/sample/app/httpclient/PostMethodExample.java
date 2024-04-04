package com.sample.app.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.StringEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.app.dto.Employee;

public class PostMethodExample {

	public static String inputStreamToString(InputStream inputStream, Charset charSet) throws IOException {

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charSet))) {
			return reader.lines().collect(Collectors.joining("\n"));
		}

	}

	public static void main(String[] args) throws Exception {

		HttpClientResponseHandler<String> responseHandler = new HttpClientResponseHandler<String>() {
			@Override
			public String handleResponse(final ClassicHttpResponse response) throws IOException, ParseException {

				HttpEntity httpEntity = response.getEntity();

				try (InputStream is = httpEntity.getContent()) {
					return inputStreamToString(is, StandardCharsets.UTF_8);
				}

			}
		};

		try (CloseableHttpClient httpClient = HttpClients.custom().build();) {

			HttpPost httpPost = new HttpPost("http://localhost:8080/api/v1/employees");
			Employee emp = new Employee();
			emp.setFirstName("Joel");
			emp.setLastName("Chelli");

			httpPost.setHeader("Content-Type", "application/json");

			ObjectMapper objectMapper = new ObjectMapper();

			httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(emp)));

			String response = httpClient.execute(httpPost, responseHandler);
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}



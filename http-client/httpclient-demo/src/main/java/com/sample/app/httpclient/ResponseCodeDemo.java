package com.sample.app.httpclient;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

public class ResponseCodeDemo  {

	public static void main(String[] args) {

		HttpClientResponseHandler<ClassicHttpResponse> responseHandler = new HttpClientResponseHandler<ClassicHttpResponse>() {
			@Override
			public ClassicHttpResponse handleResponse(final ClassicHttpResponse response) throws IOException, ParseException {
				return response;
				
			}
		};

		try (CloseableHttpClient httpClient = HttpClients.custom().disableAuthCaching().build();) {
			
			ClassicHttpRequest request = new HttpGet("https://self-learning-java-tutorial.blogspot.com");
			ClassicHttpResponse response = httpClient.execute(request, responseHandler);
			int responseCode = response.getCode();
			
			System.out.println("responseCode : " + responseCode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}



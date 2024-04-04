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
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpRequestInterceptor;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.protocol.HttpContext;

public class MultipleRequestInterceptorsDemo {

	private static class RequestInterceptor1 implements HttpRequestInterceptor {
		private String msg;
		private String name;

		private RequestInterceptor1(String name, String msg) {
			this.name = name;
			this.msg = msg;
		}

		@Override
		public void process(HttpRequest request, EntityDetails entity, HttpContext context)
				throws HttpException, IOException {

			System.out.println(name + " is called");
			request.setHeader("label", msg);

		}
	}

	private static class RequestInterceptor2 implements HttpRequestInterceptor {
		private String msg;
		private String name;

		private RequestInterceptor2(String name, String msg) {
			this.name = name;
			this.msg = msg;
		}

		@Override
		public void process(HttpRequest request, EntityDetails entity, HttpContext context)
				throws HttpException, IOException {

			System.out.println(name + " is called");
			request.setHeader("label", msg);

		}
	}

	private static class RequestInterceptor3 implements HttpRequestInterceptor {
		private String msg;
		private String name;

		private RequestInterceptor3(String name, String msg) {
			this.name = name;
			this.msg = msg;
		}

		@Override
		public void process(HttpRequest request, EntityDetails entity, HttpContext context)
				throws HttpException, IOException {

			System.out.println(name + " is called");
			request.setHeader("label", msg);

		}
	}

	private static class RequestInterceptor4 implements HttpRequestInterceptor {
		private String msg;
		private String name;

		private RequestInterceptor4(String name, String msg) {
			this.name = name;
			this.msg = msg;
		}

		@Override
		public void process(HttpRequest request, EntityDetails entity, HttpContext context)
				throws HttpException, IOException {

			System.out.println(name + " is called");
			request.setHeader("label", msg);

		}
	}

	private static HttpClientResponseHandler<String> responseHandler = new HttpClientResponseHandler<String>() {
		@Override
		public String handleResponse(final ClassicHttpResponse response) throws IOException,ParseException{

	HttpEntity httpEntity=response.getEntity();

	try(InputStream is=httpEntity.getContent()){return inputStreamToString(is,StandardCharsets.UTF_8);}

	}};

	private static String inputStreamToString(InputStream inputStream, Charset charSet) throws IOException {

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charSet))) {
			return reader.lines().collect(Collectors.joining("\n"));
		}

	}

	public static void main(String[] args) {
		RequestInterceptor1 interceptor1 = new RequestInterceptor1("interceptor1", "interceptor1");
		RequestInterceptor2 interceptor2 = new RequestInterceptor2("interceptor2", "interceptor2");
		RequestInterceptor3 interceptor3 = new RequestInterceptor3("interceptor3", "interceptor3");
		RequestInterceptor4 interceptor4 = new RequestInterceptor4("interceptor4", "interceptor4");

		try (CloseableHttpClient httpClient = HttpClients.custom().addRequestInterceptorFirst(interceptor2)
				.addRequestInterceptorFirst(interceptor1).addRequestInterceptorLast(interceptor3)
				.addRequestInterceptorLast(interceptor4).build()) {

			ClassicHttpRequest request = new HttpGet("http://localhost:8080/api/v1/replay-request");
			String response = httpClient.execute(request, responseHandler);
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}


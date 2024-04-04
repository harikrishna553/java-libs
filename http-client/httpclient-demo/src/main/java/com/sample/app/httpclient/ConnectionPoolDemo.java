package com.sample.app.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.ssl.SSLContexts;

public class ConnectionPoolDemo {

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

	private static HttpClientConnectionManager getConnectionManager() throws Exception {

		// setting up an SSLContext with custom trust management, where it will trust
		// any certificate presented by the server
		SSLContext sslContext = SSLContexts.custom()
				.loadTrustMaterial((X509Certificate[] chain, String authType) -> true).build();

		final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
				NoopHostnameVerifier.INSTANCE);

		ConnectionConfig connectionConfig = ConnectionConfig.custom().setConnectTimeout(10, TimeUnit.SECONDS)
				.setSocketTimeout(10, TimeUnit.SECONDS).setTimeToLive(180, TimeUnit.SECONDS)
				.setValidateAfterInactivity(30, TimeUnit.SECONDS).build();

		PoolingHttpClientConnectionManager manager = PoolingHttpClientConnectionManagerBuilder.create()
				.setPoolConcurrencyPolicy(PoolConcurrencyPolicy.LAX).setConnPoolPolicy(PoolReusePolicy.LIFO)
				.setDefaultConnectionConfig(connectionConfig).setSSLSocketFactory(sslsf).setMaxConnTotal(500)
				.setMaxConnPerRoute(30).build();

		return manager;

	}

	public static void main(String[] args) throws Exception {

		RequestConfig.Builder builder = RequestConfig.custom();
		builder.setResponseTimeout(5, TimeUnit.SECONDS);
		RequestConfig requestConfig = builder.build();

		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig)
				.setConnectionManager(getConnectionManager()).build();

		ClassicHttpRequest request = new HttpGet("http://localhost:8080/api/v1/employees/1");
		String response = httpClient.execute(request, responseHandler);
		System.out.println(response);
	}

}

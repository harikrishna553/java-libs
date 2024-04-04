package com.sample.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.ssl.SSLContexts;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class HttpClientUtil {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static class HttpClientUtilBuilder {
		private Integer connectionTimeout = 5;
		private Integer socketTimeout = 5;
		private Integer timeToLive = 120;
		private Integer validateAfterInactivity = 30;
		private PoolConcurrencyPolicy poolConcurrencyPolicy = PoolConcurrencyPolicy.LAX;
		private PoolReusePolicy poolReusePolicy = PoolReusePolicy.LIFO;
		private Integer totalConnections = 200;
		private Integer maxConnectionsPerRoute = 25;
		private Integer responseTimeout = 15;

		public HttpClientUtilBuilder connectionTimeout(Integer connectionTimeout) {
			this.connectionTimeout = connectionTimeout;
			return this;
		}

		public HttpClientUtilBuilder socketTimeout(Integer socketTimeout) {
			this.socketTimeout = socketTimeout;
			return this;
		}

		public HttpClientUtilBuilder timeToLive(Integer timeToLive) {
			this.timeToLive = timeToLive;
			return this;
		}

		public HttpClientUtilBuilder validateAfterInactivity(Integer validateAfterInactivity) {
			this.validateAfterInactivity = validateAfterInactivity;
			return this;
		}

		public HttpClientUtilBuilder poolConcurrencyPolicy(PoolConcurrencyPolicy poolConcurrencyPolicy) {
			this.poolConcurrencyPolicy = poolConcurrencyPolicy;
			return this;
		}

		public HttpClientUtilBuilder poolReusePolicy(PoolReusePolicy poolReusePolicy) {
			this.poolReusePolicy = poolReusePolicy;
			return this;
		}

		public HttpClientUtilBuilder totalConnections(Integer totalConnections) {
			this.totalConnections = totalConnections;
			return this;
		}

		public HttpClientUtilBuilder maxConnectionsPerRoute(Integer maxConnectionsPerRoute) {
			this.maxConnectionsPerRoute = maxConnectionsPerRoute;
			return this;
		}

		public HttpClientUtilBuilder responseTimeout(Integer responseTimeout) {
			this.responseTimeout = responseTimeout;
			return this;
		}

		public HttpClientUtil build() throws Exception {
			return new HttpClientUtil(this);
		}
	}

	// All timeouts are in seconds
	private Integer connectionTimeout;
	private Integer socketTimeout;
	private Integer timeToLive;
	private Integer validateAfterInactivity;
	private PoolConcurrencyPolicy poolConcurrencyPolicy;
	private PoolReusePolicy poolReusePolicy;
	private Integer totalConnections;
	private Integer maxConnectionsPerRoute;
	private HttpClientConnectionManager httpClientConnectionManager;
	private Integer responseTimeout;

	public HttpClientUtil(HttpClientUtilBuilder builder) throws Exception {
		this.connectionTimeout = builder.connectionTimeout;
		this.socketTimeout = builder.socketTimeout;
		this.timeToLive = builder.timeToLive;
		this.validateAfterInactivity = builder.validateAfterInactivity;
		this.poolConcurrencyPolicy = builder.poolConcurrencyPolicy;
		this.poolReusePolicy = builder.poolReusePolicy;
		this.totalConnections = builder.totalConnections;
		this.maxConnectionsPerRoute = builder.maxConnectionsPerRoute;
		this.responseTimeout = builder.responseTimeout;
		this.httpClientConnectionManager = getConnectionManager();

	}

	public HttpClientUtil(HttpClientConnectionManager connectionManager, Integer responseTimeout) {
		this.httpClientConnectionManager = connectionManager;
		this.responseTimeout = responseTimeout;
	}

	public HttpClientConnectionManager getConnectionManager() throws Exception {

		// setting up an SSLContext with custom trust management, where it will trust
		// any certificate presented by the server
		SSLContext sslContext = SSLContexts.custom()
				.loadTrustMaterial((X509Certificate[] chain, String authType) -> true).build();

		final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
				NoopHostnameVerifier.INSTANCE);

		ConnectionConfig connectionConfig = ConnectionConfig.custom()
				.setConnectTimeout(this.connectionTimeout, TimeUnit.SECONDS)
				.setSocketTimeout(this.socketTimeout, TimeUnit.SECONDS).setTimeToLive(this.timeToLive, TimeUnit.SECONDS)
				.setValidateAfterInactivity(this.validateAfterInactivity, TimeUnit.SECONDS).build();

		PoolingHttpClientConnectionManager manager = PoolingHttpClientConnectionManagerBuilder.create()
				.setPoolConcurrencyPolicy(this.poolConcurrencyPolicy).setConnPoolPolicy(this.poolReusePolicy)
				.setDefaultConnectionConfig(connectionConfig).setSSLSocketFactory(sslsf)
				.setMaxConnTotal(this.totalConnections).setMaxConnPerRoute(this.maxConnectionsPerRoute).build();

		return manager;

	}

	public CloseableHttpClient httpClient() {

		RequestConfig.Builder builder = RequestConfig.custom();
		builder.setResponseTimeout(responseTimeout, TimeUnit.SECONDS);
		RequestConfig requestConfig = builder.build();

		return HttpClients.custom().setConnectionManager(httpClientConnectionManager)
				.setDefaultRequestConfig(requestConfig).build();
	}

	static {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	public static String inputStreamToString(InputStream inputStream, Charset charSet) throws IOException {

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charSet))) {
			return reader.lines().collect(Collectors.joining("\n"));
		}
	}

	private static HttpClientResponseHandler<String> responseHandler = new HttpClientResponseHandler<String>() {
		@Override
		public String handleResponse(final ClassicHttpResponse response) throws IOException, ParseException {

			if (response.getCode() != HttpStatus.SC_OK && response.getCode() != HttpStatus.SC_CREATED) {
				throw new IOException("Error: HTTP request failed with status code: " + response.getCode()
						+ " message: " + response.getReasonPhrase());
			}
			HttpEntity httpEntity = response.getEntity();

			try (InputStream is = httpEntity.getContent()) {
				return inputStreamToString(is, StandardCharsets.UTF_8);
			}

		}
	};

	private static <T> T httpRequest(CloseableHttpClient httpClient, ClassicHttpRequest request, Class<T> clazz)
			throws Exception {
		try {
			String response = httpClient.execute(request, responseHandler);

			if (clazz != null) {
				return objectMapper.readValue(response, clazz);
			}
			return null;

		} catch (Exception e) {
			throw new Exception("Error: No entity content found in response");
		}
	}

	private static void setHeaders(ClassicHttpRequest request, Map<String, String> headers) {
		if (headers != null) {
			for (Map.Entry<String, String> header : headers.entrySet()) {
				request.addHeader(header.getKey(), header.getValue());
			}
		}
	}

	public static <T> T get(CloseableHttpClient httpClient, String url, Class<T> clazz, Map<String, String> headers)
			throws Exception {
		HttpGet request = new HttpGet(url);
		setHeaders(request, headers);
		return httpRequest(httpClient, request, clazz);
	}

	public static <T, D> T post(CloseableHttpClient httpClient, String url, D data, Class<T> clazz,
			Map<String, String> headers) throws Exception {
		HttpPost request = new HttpPost(url);
		setHeaders(request, headers);
		request.setEntity(new StringEntity(objectMapper.writeValueAsString(data)));
		return httpRequest(httpClient, request, clazz);
	}

	public static <T, D> T put(CloseableHttpClient httpClient, String url, D data, Class<T> clazz,
			Map<String, String> headers) throws Exception {
		HttpPut request = new HttpPut(url);
		setHeaders(request, headers);
		request.setEntity(new StringEntity(objectMapper.writeValueAsString(data)));
		return httpRequest(httpClient, request, clazz);
	}

	public static void delete(CloseableHttpClient httpClient, String url, Map<String, String> headers)
			throws Exception {
		HttpDelete request = new HttpDelete(url);
		setHeaders(request, headers);
		httpRequest(httpClient, request, null);
	}

}

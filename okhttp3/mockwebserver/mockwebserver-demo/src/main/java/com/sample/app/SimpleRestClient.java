package com.sample.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class SimpleRestClient {

	private String url;

	public SimpleRestClient(String url) {
		this.url = url;
	}

	public String getRequest() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpGet httpget = new HttpGet(url);
		return getResponseAsString(httpclient.execute(httpget));

	}

	public CloseableHttpResponse getClosebaleResponse() throws ClientProtocolException, IOException {

		HttpRequestBase httpget = new HttpGet(url);

		CloseableHttpClient httpclient = HttpClients.createDefault();
		return httpclient.execute(httpget);

	}

	public static String getResponseAsString(InputStream is) throws IOException {
		try (InputStreamReader isReader = new InputStreamReader(is)) {
			BufferedReader reader = new BufferedReader(isReader);
			StringBuffer sb = new StringBuffer();
			String str;
			while ((str = reader.readLine()) != null) {
				sb.append(str);
			}

			return sb.toString();
		}

	}

	public static String getResponseAsString(CloseableHttpResponse closeableHttpResponse)
			throws UnsupportedOperationException, IOException {

		HttpEntity httpEntity = closeableHttpResponse.getEntity();
		return getResponseAsString(httpEntity.getContent());

	}
}
package com.sample.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
		CloseableHttpResponse response = httpclient.execute(httpget);

		HttpEntity httpEntity = response.getEntity();

		InputStream is = httpEntity.getContent();
		String str = getBody(is);

		return str;

	}

	public static String getBody(InputStream is) throws IOException {
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
}

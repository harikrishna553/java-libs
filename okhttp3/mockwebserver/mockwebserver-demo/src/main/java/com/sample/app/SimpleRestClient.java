package com.sample.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

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

	public String maxConnTimeOut(int noOfSeconds) throws ClientProtocolException, IOException {
		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
		requestConfigBuilder.setConnectTimeout(noOfSeconds * 1000);
		requestConfigBuilder.setConnectionRequestTimeout(noOfSeconds * 1000);
		requestConfigBuilder.setSocketTimeout(noOfSeconds * 1000);
		RequestConfig requestConfig = requestConfigBuilder.build();

		HttpRequestBase httpget = new HttpGet(url);
		httpget.setConfig(requestConfig);

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = httpclient.execute(httpget);

		return getResponseAsString(httpResponse);

	}

	public void postAndIgnore() throws ClientProtocolException, IOException {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("name", "Krishna"));
		formparams.add(new BasicNameValuePair("password", "password"));
		formparams.add(new BasicNameValuePair("mailId", "abcdef@abcdef.com"));
		UrlEncodedFormEntity requestBody = new UrlEncodedFormEntity(formparams, Consts.UTF_8);

		HttpPost httppost = new HttpPost(url + "?appName=chatserver");
		httppost.setEntity(requestBody);

		try (CloseableHttpClient httpclient = HttpClientBuilder.create().build();) {
			httpclient.execute(httppost);
		}

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
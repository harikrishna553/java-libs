package com.sample.app;

import java.util.HashMap;
import java.util.Map;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;

import com.sample.app.dto.Employee;
import com.sample.app.util.HttpClientUtil;

public class HttpClientUtilDemo {

	public static void main(String[] args) throws Exception {
		HttpClientUtil httpClientUtil = new HttpClientUtil.HttpClientUtilBuilder().connectionTimeout(10)
				.maxConnectionsPerRoute(30).totalConnections(100).poolConcurrencyPolicy(PoolConcurrencyPolicy.LAX)
				.poolReusePolicy(PoolReusePolicy.FIFO).responseTimeout(10).socketTimeout(5).timeToLive(300)
				.validateAfterInactivity(120).build();

		try (CloseableHttpClient httpClient = httpClientUtil.httpClient();) {
			String url = "http://localhost:8080/api/v1/employees/1";

			Map<String, String> headers = new HashMap<>();
			headers.put(HttpHeaders.CONTENT_TYPE, "application/json");

			System.out.println("Employee with id 1");
			Employee emp = HttpClientUtil.get(httpClient, url, Employee.class, headers);
			System.out.println(emp);

			System.out.println("\nUpdating employee with id 1");
			Employee emp1 = new Employee();
			emp1.setFirstName("Chamu");
			emp1.setLastName("Maj");
			HttpClientUtil.put(httpClient, url, emp1, Employee.class, headers);

			emp1 = HttpClientUtil.get(httpClient, url, Employee.class, headers);
			System.out.println(emp1);

			System.out.println("\nCreate new employee");
			Employee emp2 = new Employee();
			emp2.setFirstName("Gopi");
			emp2.setLastName("Battu");
			emp2 = HttpClientUtil.post(httpClient, "http://localhost:8080/api/v1/employees", emp2, Employee.class,
					headers);
			System.out.println(emp2);

		}

	}

}

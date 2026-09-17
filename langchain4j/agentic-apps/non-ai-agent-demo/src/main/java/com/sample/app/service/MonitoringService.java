package com.sample.app.service;

import com.sample.app.model.ServiceHealth;
import com.sample.app.model.Status;

public class MonitoringService {

  public ServiceHealth getHealth(String serviceName) {

    System.out.println();
    System.out.println("[MonitoringService] Calling monitoring API for: " + serviceName);

    // Simulate:
    //
    // GET /monitoring/services/checkout-service/health

    if ("checkout-service".equalsIgnoreCase(serviceName)) {

      return new ServiceHealth(serviceName, Status.DEGRADED, 18.7, 1250);
    }

    if ("payment-service".equalsIgnoreCase(serviceName)) {

      return new ServiceHealth(serviceName, Status.DOWN, 27.5, 3500);
    }

    return new ServiceHealth(serviceName, Status.HEALTHY, 0.8, 120);
  }
}

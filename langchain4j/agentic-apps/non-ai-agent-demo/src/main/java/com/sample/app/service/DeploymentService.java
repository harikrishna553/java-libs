package com.sample.app.service;

import com.sample.app.model.DeploymentInfo;
import java.time.LocalDateTime;

public class DeploymentService {

  public DeploymentInfo getLatestDeployment(String serviceName) {

    System.out.println();
    System.out.println("[DeploymentService] Looking up latest deployment for: " + serviceName);

    if ("checkout-service".equalsIgnoreCase(serviceName)) {

      return new DeploymentInfo(
          serviceName, "4.7.2", "4.7.1", LocalDateTime.now().minusMinutes(45), "checkout-team");
    }

    if ("payment-service".equalsIgnoreCase(serviceName)) {

      return new DeploymentInfo(
          serviceName, "8.2.0", "8.1.6", LocalDateTime.now().minusHours(3), "payments-team");
    }

    return new DeploymentInfo(
        serviceName, "1.0.0", "0.9.9", LocalDateTime.now().minusDays(2), "platform-team");
  }
}

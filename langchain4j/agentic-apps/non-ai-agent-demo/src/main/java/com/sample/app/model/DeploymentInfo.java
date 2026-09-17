package com.sample.app.model;

import java.time.LocalDateTime;

public class DeploymentInfo {
  private String serviceName;
  private String version;
  private String previousVersion;
  private LocalDateTime deployedAt;
  private String deployedBy;

  public DeploymentInfo(
      String serviceName,
      String version,
      String previousVersion,
      LocalDateTime deployedAt,
      String deployedBy) {
    super();
    this.serviceName = serviceName;
    this.version = version;
    this.previousVersion = previousVersion;
    this.deployedAt = deployedAt;
    this.deployedBy = deployedBy;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getPreviousVersion() {
    return previousVersion;
  }

  public void setPreviousVersion(String previousVersion) {
    this.previousVersion = previousVersion;
  }

  public LocalDateTime getDeployedAt() {
    return deployedAt;
  }

  public void setDeployedAt(LocalDateTime deployedAt) {
    this.deployedAt = deployedAt;
  }

  public String getDeployedBy() {
    return deployedBy;
  }

  public void setDeployedBy(String deployedBy) {
    this.deployedBy = deployedBy;
  }
}

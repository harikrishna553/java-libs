package com.sample.app.model;

public class ServiceHealth {
  private String serviceName;
  private Status status;
  private double errorRate;
  private long latencyMs;

  public ServiceHealth(String serviceName, Status status, double errorRate, long latencyMs) {
    super();
    this.serviceName = serviceName;
    this.status = status;
    this.errorRate = errorRate;
    this.latencyMs = latencyMs;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public double getErrorRate() {
    return errorRate;
  }

  public void setErrorRate(double errorRate) {
    this.errorRate = errorRate;
  }

  public long getLatencyMs() {
    return latencyMs;
  }

  public void setLatencyMs(long latencyMs) {
    this.latencyMs = latencyMs;
  }
}

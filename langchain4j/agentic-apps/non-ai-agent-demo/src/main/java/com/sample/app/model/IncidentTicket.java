package com.sample.app.model;

import java.time.LocalDateTime;

public class IncidentTicket {
  private String ticketId;
  private String serviceName;
  private String priority;
  private String incident;
  private String rootCauseAnalysis;
  private ServiceHealth serviceHealth;
  private DeploymentInfo deploymentInfo;
  private LocalDateTime createdAt;

  public IncidentTicket(
      String ticketId,
      String serviceName,
      String priority,
      String incident,
      String rootCauseAnalysis,
      ServiceHealth serviceHealth,
      DeploymentInfo deploymentInfo,
      LocalDateTime createdAt) {
    this.ticketId = ticketId;
    this.serviceName = serviceName;
    this.priority = priority;
    this.incident = incident;
    this.rootCauseAnalysis = rootCauseAnalysis;
    this.serviceHealth = serviceHealth;
    this.deploymentInfo = deploymentInfo;
    this.createdAt = createdAt;
  }

  public String getTicketId() {
    return ticketId;
  }

  public void setTicketId(String ticketId) {
    this.ticketId = ticketId;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public String getIncident() {
    return incident;
  }

  public void setIncident(String incident) {
    this.incident = incident;
  }

  public String getRootCauseAnalysis() {
    return rootCauseAnalysis;
  }

  public void setRootCauseAnalysis(String rootCauseAnalysis) {
    this.rootCauseAnalysis = rootCauseAnalysis;
  }

  public ServiceHealth getServiceHealth() {
    return serviceHealth;
  }

  public void setServiceHealth(ServiceHealth serviceHealth) {
    this.serviceHealth = serviceHealth;
  }

  public DeploymentInfo getDeploymentInfo() {
    return deploymentInfo;
  }

  public void setDeploymentInfo(DeploymentInfo deploymentInfo) {
    this.deploymentInfo = deploymentInfo;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}

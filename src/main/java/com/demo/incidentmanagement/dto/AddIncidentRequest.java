package com.demo.incidentmanagement.dto;

/**
 * Record for adding incident
 * @param email User's email
 * @param incidentDetail Incident detail message
 * @param priority Incident priority
 */
public record AddIncidentRequest(String email, String incidentDetail, String priority) {
}

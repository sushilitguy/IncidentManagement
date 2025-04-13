package com.demo.incidentmanagement.dto;

/**
 * Record for adding incident
 * @param email User's email
 * @param incidentDetails Incident detail message
 * @param priority Incident priority
 */
public record AddIncidentRequest(String email, String incidentDetails, String priority) {
}

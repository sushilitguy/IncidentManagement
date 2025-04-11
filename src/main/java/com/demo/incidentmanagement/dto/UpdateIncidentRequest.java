package com.demo.incidentmanagement.dto;

/**
 * Record for updating incident
 * @param id Incident id
 * @param email User's email
 * @param incidentDetails Incident detail message
 * @param priority Incident priority
 * @param status Incident status
 */
public record UpdateIncidentRequest(String id, String email, String incidentDetails, String priority, String status) {
}

package com.demo.incidentmanagement.dto;

/**
 * Record for Update Password Request
 * @param email User's Email
 * @param password User's updated password
 */
public record UpdatePasswordRequest(String email, String password) {
}

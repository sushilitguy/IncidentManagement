package com.demo.incidentmanagement.dto;

/**
 * Record for forgot password request
 * @param email User's email
 */
public record ForgotPasswordRequest(String email) {
}

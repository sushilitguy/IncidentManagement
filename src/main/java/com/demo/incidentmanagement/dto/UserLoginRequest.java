package com.demo.incidentmanagement.dto;

/**
 * Record for User Login Request
 * @param email User's Email
 * @param password User's password
 */
public record UserLoginRequest(String email, String password) {
}

package com.demo.incidentmanagement.dto.response;

public record AuthSuccessResponse(long id,
                                  String email,
                                  boolean reset,
                                  String firstName,
                                  String lastName,
                                  String token) {
}

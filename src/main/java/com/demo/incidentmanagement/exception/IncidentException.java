package com.demo.incidentmanagement.exception;

import org.springframework.http.HttpStatus;

/**
 * Custom Exception to use in application
 */
public class IncidentException extends RuntimeException {
  private final HttpStatus httpStatus;

  /**
   * Constructor
   * @param message Exception message
   * @param httpStatus HttpStatus code to be sent in response
   */
  public IncidentException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

  /**
   * Returns Status code
   * @return HttpStatus object
   */
  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}

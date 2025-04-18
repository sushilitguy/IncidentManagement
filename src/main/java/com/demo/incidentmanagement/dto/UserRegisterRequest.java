package com.demo.incidentmanagement.dto;

/**
 * Record for User Register request
 * @param userType User Type
 * @param firstName User's First name
 * @param lastName User's Last name
 * @param email User's Email
 * @param address User's Address
 * @param pinCode User's PinCode
 * @param city User's city
 * @param state User's State
 * @param country User's Country
 * @param mobNo User's Mobile No
 * @param password User's Password
 */
public record UserRegisterRequest(String userType, String firstName, String lastName, String email,
                                  String address, int pinCode, String city, String state, String country,
                                  String mobNo, String password) {
}

package com.demo.incidentmanagement.controller;

import com.demo.incidentmanagement.dto.ForgotPasswordRequest;
import com.demo.incidentmanagement.dto.UpdatePasswordRequest;
import com.demo.incidentmanagement.dto.UserLoginRequest;
import com.demo.incidentmanagement.dto.UserRegisterRequest;
import com.demo.incidentmanagement.model.AppUsers;
import com.demo.incidentmanagement.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to handle User functionality
 */
@RestController
public class AppUserController {
    private final AppUserService service;

    /**
     * Constructor
     * @param service AppUserService object
     */
    @Autowired
    public AppUserController(AppUserService service) {
        this.service = service;
    }

    /**
     * Handle login request, it validates user credentials if authentication is successful
     * @param loginRequest Request object received from client
     * @return token if authentication is successful else message "Failure"
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest loginRequest) {
        String result = service.verify(loginRequest);
        if(!result.equals("Failure")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid UserName or Password", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Handle User registration request
     * @param registerRequest Request object received from client
     * @return Saved User object
     */
    @PostMapping("/register")
    public ResponseEntity<AppUsers> registerUser(@RequestBody UserRegisterRequest registerRequest) {
        AppUsers savedUser = service.registerUser(registerRequest);
        if(savedUser != null) {
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Handle Forgot password request
     * @param forgotPasswordRequest ForgotPasswordRequest Object
     * @return Message for password reset
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        boolean success = service.resetPassword(forgotPasswordRequest);
        if(success) {
            return new ResponseEntity<>("Temporary Password sent to your email.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Email not present in our records", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Handle Update password request
     * @param request updatePassword
     * @return Message for password update
     */
    @PatchMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequest request) {
        boolean success = service.updatePassword(request);
        if(success) {
            return new ResponseEntity<>("Password updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Email not present in our records", HttpStatus.BAD_REQUEST);
        }
    }
}

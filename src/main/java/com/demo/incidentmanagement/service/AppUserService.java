package com.demo.incidentmanagement.service;

import com.demo.incidentmanagement.dto.ForgotPasswordRequest;
import com.demo.incidentmanagement.dto.UpdatePasswordRequest;
import com.demo.incidentmanagement.dto.UserLoginRequest;
import com.demo.incidentmanagement.dto.UserRegisterRequest;
import com.demo.incidentmanagement.exception.IncidentException;
import com.demo.incidentmanagement.model.AppUsers;
import com.demo.incidentmanagement.model.AppUsersData;
import com.demo.incidentmanagement.model.UserType;
import com.demo.incidentmanagement.repo.AppUsersRepo;
import com.demo.incidentmanagement.util.PasswordUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class to handle User operations
 */
@Service
public class AppUserService {
    private final AppUsersRepo repo;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final EmailService emailService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /**
     * Constructor
     * @param repo Repository instance
     * @param authManager AuthenticationManager object
     * @param jwtService JWTService object
     * @param emailService EmailService object
     */
    @Autowired
    public AppUserService(AppUsersRepo repo, AuthenticationManager authManager, JWTService jwtService, EmailService emailService) {
        this.repo = repo;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    /**
     * Verifies user credentials and returns token in case of successful authentication
     * @param userLoginRequest Request object received from client
     * @return token if authentication is successful else message "Failure"
     */
    public String verify(UserLoginRequest userLoginRequest) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.email(),userLoginRequest.password()));

        if(auth.isAuthenticated()) {
            return jwtService.generateToken(userLoginRequest.email());
        } else {
            return "Failure";
        }
    }

    /**
     * Registers new user
     * @param registerRequest Request object received from client
     * @return saved User object
     */
    public AppUsers registerUser(UserRegisterRequest registerRequest) {
        AppUsers savedAppUser = null;
        String email = registerRequest.email();
        String password = registerRequest.password();
        UserType userType = null;
        try {
            userType = UserType.valueOf(registerRequest.userType());
        } catch (IllegalArgumentException e) {
            throw new IncidentException("Invalid value for UserType", HttpStatus.BAD_REQUEST);
        }

        if(!PasswordUtility.isValidPassword(password)) {
            throw new IncidentException("Password criteria not matched", HttpStatus.BAD_REQUEST);
        }

        if(repo.findByEmail(email).isPresent()) {
            throw new IncidentException("User Already Exists", HttpStatus.CONFLICT);
        }

        if (email != null && !email.isBlank()) {
            AppUsers appUser = new AppUsers();
            appUser.setEmail(email);
            appUser.setPassword(encoder.encode(password));

            AppUsersData userData = new AppUsersData();
            userData.setUserType(userType);
            userData.setFirstName(registerRequest.firstName());
            userData.setLastName(registerRequest.lastName());
            userData.setMobNo(registerRequest.MobNo());
            userData.setAddress(registerRequest.address());
            userData.setPinCode(registerRequest.pincode());
            userData.setCity(registerRequest.city());
            userData.setState(registerRequest.state());
            userData.setCountry(registerRequest.country());

            appUser.setUserData(userData);
            userData.setAppUser(appUser);

            savedAppUser = repo.save(appUser);
        } else {
            throw new IncidentException("Invalid User Creation Request", HttpStatus.BAD_REQUEST);
        }

        return savedAppUser;
    }

    /**
     * Resets user password with temporary password
     * @param forgotPasswordRequest ForgotPasswordRequest object
     * @return Whether reset password successful
     */
    public boolean resetPassword(ForgotPasswordRequest forgotPasswordRequest) {
        boolean isReset = false;
        Optional<AppUsers> userOptional = repo.findByEmail(forgotPasswordRequest.email());

        if(userOptional.isPresent()) {
            String tempPassword = PasswordUtility.generateTempPassword();
            AppUsers user = userOptional.get();
            user.setPassword(encoder.encode(tempPassword));
            user.setReset(true);

            try {
                boolean mailSent = emailService.sendPasswordResetMail(user.getEmail(), tempPassword, user.getUserData().getFirstName() + " " + user.getUserData().getLastName());
                if(mailSent) {
                    repo.save(user);
                    isReset = true;
                }
            } catch (RuntimeException e) {
                throw new IncidentException(e.getMessage(), HttpStatus.NOT_MODIFIED);
            }
        } else {
            throw new IncidentException("User with email : " + forgotPasswordRequest.email() + " not found", HttpStatus.BAD_REQUEST);
        }

        return isReset;
    }

    /**
     * Updated User's password
     * @param request UpdatePasswordRequest object
     * @return Whether password update successful
     */
    public boolean updatePassword(UpdatePasswordRequest request) {
        boolean isReset = false;

        if(!PasswordUtility.isValidPassword(request.password())) {
            throw new IncidentException("Password criteria not matched", HttpStatus.BAD_REQUEST);
        }

        Optional<AppUsers> userOptional = repo.findByEmail(request.email());

        if(userOptional.isPresent()) {
            AppUsers user = userOptional.get();
            user.setPassword(encoder.encode(request.password()));
            user.setReset(false);

            repo.save(user);
            isReset = true;
        } else {
            throw new IncidentException("User with email : " + request.email() + " not found", HttpStatus.BAD_REQUEST);
        }

        return isReset;
    }
}

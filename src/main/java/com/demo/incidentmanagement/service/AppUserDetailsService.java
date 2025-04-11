package com.demo.incidentmanagement.service;

import com.demo.incidentmanagement.model.AppUsers;
import com.demo.incidentmanagement.model.UserPrincipal;
import com.demo.incidentmanagement.repo.AppUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Class to represent UserDetails to be used by Security module
 */
@Service
public class AppUserDetailsService implements UserDetailsService {
    private final AppUsersRepo repo;

    /**
     * Constructor
     * @param repo AppUsersRepo object
     */
    @Autowired
    public AppUserDetailsService(AppUsersRepo repo) {
        this.repo = repo;
    }

    /**
     * Loads User by username, in our case by email
     * @param username User email
     * @return UserDetails object
     * @throws UsernameNotFoundException Exception thrown in case user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUsers> user = repo.findByEmail(username);
        if(user.isEmpty()) {
            System.out.println("User : " + username + " not found in database");
            throw new UsernameNotFoundException("User : " + username + " not found in database");
        }
        return new UserPrincipal(user.get());
    }
}

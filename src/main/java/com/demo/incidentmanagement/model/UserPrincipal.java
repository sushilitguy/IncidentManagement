package com.demo.incidentmanagement.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Class to represent UserDetails to be used by Security module
 */
public class UserPrincipal implements UserDetails {
    private final AppUsers user;

    /**
     * Constructor
     * @param user AppUsers object
     */
    public UserPrincipal(AppUsers user) {
        this.user = user;
    }

    /**
     * Returns authorities, by default returning User role
     * @return Collection of Authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("User"));
    }

    /**
     * Returns User's password
     * @return User's password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns Username, in our case it will return user email
     * @return user email
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Returns whether account not expired
     * @return default value true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Returns whether account not locked
     * @return default value true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Returns whether credentials not expired
     * @return default value true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Returns whether user is enabled
     * @return default value true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}

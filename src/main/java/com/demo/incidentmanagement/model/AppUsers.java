package com.demo.incidentmanagement.model;

import jakarta.persistence.*;

/**
 * Entity class for handling Application User
 */
@Entity
public class AppUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private boolean reset = false;
    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL, optional = false)
    @PrimaryKeyJoinColumn
    private AppUsersData userData;

    /**
     * Default Constructor
     */
    public AppUsers() {
    }

    /**
     * Parameterized Constructor
     * @param id Id for row identification
     * @param email User email
     * @param password User password
     * @param reset Reset flag, by default it's false, will set true once user requests Forgot password
     * @param userData AppUsersData object
     */
    public AppUsers(long id, String email, String password, boolean reset, AppUsersData userData) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.reset = reset;
        this.userData = userData;
    }

    /**
     * Returns id
     * @return long value of id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id
     * @param id long value of id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns user email
     * @return user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets user email
     * @param email user email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns user password (encrypted form)
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets user password
     * @param password user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns whether reset flag is enabled
     * @return password reset flag
     */
    public boolean isReset() {
        return reset;
    }

    /**
     * Sets password reset flag
     * @param reset password reset flag
     */
    public void setReset(boolean reset) {
        this.reset = reset;
    }

    /**
     * Returns user data object
     * @return AppUsersData object
     */
    public AppUsersData getUserData() {
        return userData;
    }

    /**
     * Sets user data object
     * @param userData AppUsersData object
     */
    public void setUserData(AppUsersData userData) {
        this.userData = userData;
    }

    /**
     * Returns String representation of object properties
     * @return String representation of object properties
     */
    @Override
    public String toString() {
        return "AppUsers{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", reset=" + reset +
                ", userData=" + userData.toString() +
                '}';
    }
}

package com.demo.incidentmanagement.model;

import jakarta.persistence.*;

/**
 * Entity class for handling user details data
 */
@Entity
public class AppUsersData {
    @Id
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;
    private String firstName;
    private String lastName;
    private String mobNo;
    private String address;
    private int pinCode;
    private String city;
    private String state;
    private String country;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private AppUsers appUser;

    /**
     * Default Constructor
     */
    public AppUsersData() {
    }

    /**
     * Parameterized constructor
     * @param id Id value
     * @param userType User Type
     * @param firstName User's First Name
     * @param lastName User's Last name
     * @param mobNo User's Mobile No
     * @param address User's Address
     * @param pinCode User's Pin Code
     * @param city User's City
     * @param state User's State
     * @param country User's Country
     * @param appUser AppUsers object
     */
    public AppUsersData(long id, UserType userType, String firstName, String lastName, String mobNo, String address,
                        int pinCode, String city, String state, String country, AppUsers appUser) {
        this.id = id;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobNo = mobNo;
        this.address = address;
        this.pinCode = pinCode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.appUser = appUser;
    }

    /**
     * Returns Id value
     * @return long Id value
     */
    public long getId() {
        return id;
    }

    /**
     * Sets Id value
     * @param id long Id value
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns User's Type
     * @return UserType enum value
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Sets User's Type
     * @param userType UserType enum value
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    /**
     * Returns User's First name
     * @return User's First name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets User's First name
     * @param firstName User's First name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns User's Last name
     * @return User's Last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets User's Last name
     * @param lastName User's Last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns User's Mobile no
     * @return User's Mobile no
     */
    public String getMobNo() {
        return mobNo;
    }

    /**
     * Sets User's Mobile no
     * @param mobNo User's Mobile no
     */
    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    /**
     * Returns User's Address
     * @return User's Address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets User's Address
     * @param address User's Address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns User's pin code
     * @return User's pin code
     */
    public int getPinCode() {
        return pinCode;
    }

    /**
     * Sets User's pin code
     * @param pinCode User's pin code
     */
    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    /**
     * Returns User's City
     * @return User's City
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets User's City
     * @param city User's City
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns User's state
     * @return User's state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets User's state
     * @param state User's state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Returns User's Country
     * @return User's Country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets User's Country
     * @param country User's Country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Sets User Info
     * @param appUser AppUsers object
     */
    public void setAppUser(AppUsers appUser) {
        this.appUser = appUser;
    }

    /**
     * Returns String representation of object properties
     * @return String representation of object properties
     */
    @Override
    public String toString() {
        return "AppUsersData{" +
                "id=" + id +
                ", userType=" + userType +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobNo='" + mobNo + '\'' +
                ", address='" + address + '\'' +
                ", pinCode=" + pinCode +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", appUser=" + appUser.toString() +
                '}';
    }
}

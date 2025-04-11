package com.demo.incidentmanagement.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entity class to represent Incident data
 */
@Entity
public class IncidentData {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private AppUsers userInfo;
    private String incidentDetails;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidentPriority priority;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidentStatus status = IncidentStatus.Open;
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Default Constructor
     */
    public IncidentData() {
    }

    /**
     * Parameterized constructor
     * @param id Incident Id
     * @param userInfo AppUsers object containing UserInfo
     * @param incidentDetails Incident details
     * @param priority priority value
     * @param status status value
     * @param createdAt created timestamp
     * @param updatedAt updated timestamp
     */
    public IncidentData(String id, AppUsers userInfo, String incidentDetails, IncidentPriority priority,
                        IncidentStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userInfo = userInfo;
        this.incidentDetails = incidentDetails;
        this.priority = priority;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Returns Incident Id
     * @return Incident Id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets Incident Id
     * @param id Incident Id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns UserInfo object
     * @return AppUsers object
     */
    public AppUsers getUserInfo() {
        return userInfo;
    }

    /**
     * Sets UserInfo object
     * @param userInfo AppUsers object
     */
    public void setUserInfo(AppUsers userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * Returns Incident details
     * @return Incident details
     */
    public String getIncidentDetails() {
        return incidentDetails;
    }

    /**
     * Sets Incident details
     * @param incidentDetails Incident details
     */
    public void setIncidentDetails(String incidentDetails) {
        this.incidentDetails = incidentDetails;
    }

    /**
     * Returns Incident Priority
     * @return IncidentPriority enum value
     */
    public IncidentPriority getPriority() {
        return priority;
    }

    /**
     * Sets Incident Priority
     * @param priority IncidentPriority enum value
     */
    public void setPriority(IncidentPriority priority) {
        this.priority = priority;
    }

    /**
     * Returns Incident status
     * @return IncidentStatus enum value
     */
    public IncidentStatus getStatus() {
        return status;
    }

    /**
     * Sets Incident status
     * @param status IncidentStatus enum value
     */
    public void setStatus(IncidentStatus status) {
        this.status = status;
    }

    /**
     * Returns creation timestamp
     * @return creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Returns updation timestamp
     * @return updation timestamp
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets timestamps on creation
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Sets timestamp on every update
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Returns String representation of object properties
     * @return String representation of object properties
     */
    @Override
    public String toString() {
        return "IncidentData{" +
                "id='" + id + '\'' +
                ", userInfo=" + userInfo.toString() +
                ", incidentDetails='" + incidentDetails + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                '}';
    }
}
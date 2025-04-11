package com.demo.incidentmanagement.repo;

import com.demo.incidentmanagement.model.IncidentData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for DB operations on IncidentDetails table
 */
public interface IncidentDataRepo extends JpaRepository<IncidentData, String> {
    /**
     * Returns IncidentData for User email
     * @param email User's email
     * @return List of Incident data
     */
    List<IncidentData> findByUserInfoEmail(String email);

    /**
     * Returns Incident object if incident id is created by given user
     * @param id id of incident
     * @param email email of user
     * @return Optional<IncidentData> object
     */
    Optional<IncidentData> findByIdAndUserInfoEmail(String id, String email);
}

package com.demo.incidentmanagement.service;

import com.demo.incidentmanagement.dto.AddIncidentRequest;
import com.demo.incidentmanagement.dto.UpdateIncidentRequest;
import com.demo.incidentmanagement.exception.IncidentException;
import com.demo.incidentmanagement.model.AppUsers;
import com.demo.incidentmanagement.model.IncidentData;
import com.demo.incidentmanagement.model.IncidentPriority;
import com.demo.incidentmanagement.model.IncidentStatus;
import com.demo.incidentmanagement.repo.AppUsersRepo;
import com.demo.incidentmanagement.repo.IncidentDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Service class to handle Incident data operations
 */
@Service
public class IncidentService {
    private final AppUsersRepo userRepo;
    private final IncidentDataRepo repo;
    private final Random randomGenerator;

    /**
     * Constructor
     * @param repo IncidentDataRepo object
     * @param userRepo AppUsersRepo object
     */
    @Autowired
    public IncidentService(IncidentDataRepo repo, AppUsersRepo userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        randomGenerator = new Random();
    }

    /**
     * Returns all incidents created by given user
     * @param email user email
     * @return List of incidents created by user
     */
    public List<IncidentData> getUsersAllIncidents(String email) {
        Optional<AppUsers> user = userRepo.findByEmail(email);
        if(user.isPresent()) {
            return repo.findByUserInfoEmail(email);
        } else {
            throw new IncidentException("User not available in Database", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Returns Incident object if incident id is created by given user
     * @param incidentId id of incident
     * @param email email of user
     * @return Optional<IncidentData> object
     */
    public Optional<IncidentData> getUserIncidentById(String incidentId, String email) {
        Optional<AppUsers> user = userRepo.findByEmail(email);
        if(user.isPresent()) {
            return repo.findByIdAndUserInfoEmail(incidentId, email);
        } else {
            throw new IncidentException("User not available in Database", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Adds incident in database
     * @param request AddIncidentRequest object
     * @return Added IncidentData object, null if unable to add
     */
    public IncidentData addIncident(AddIncidentRequest request) {
        IncidentData addedIncidentData = null;
        Optional<AppUsers> user = userRepo.findByEmail(request.email());
        if(user.isPresent()) {
            IncidentData incident = new IncidentData();
            incident.setId(generateIncidentId());
            incident.setUserInfo(user.get());
            incident.setIncidentDetails(request.incidentDetail());
            try {
                incident.setPriority(IncidentPriority.valueOf(request.priority()));
            } catch (IllegalArgumentException e) {
                throw new IncidentException("Invalid value for Priority", HttpStatus.BAD_REQUEST);
            }

            addedIncidentData = repo.save(incident);
        } else {
            throw new IncidentException("User not available in Database", HttpStatus.BAD_REQUEST);
        }

        return addedIncidentData;
    }

    /**
     * Auto-generate Incident ID
     * Generated ID in the following format- RMG + Random 5-digit number+ Current year (2022)
     * e.g. RMG345712022
     * @return generated Id
     */
    private String generateIncidentId() {
        String prefix = "RMG";
        int suffix = LocalDate.now().getYear();
        String id = null;

        boolean idGenerated = false;

        while(!idGenerated) {
            int randomNo = randomGenerator.nextInt(99999)+1;
            String random5Digit = String.format("%05d",randomNo);
            id = prefix + random5Digit + suffix;

            Optional<IncidentData> incidentData = repo.findById(id);

            if(incidentData.isEmpty()) {
                idGenerated = true;
            }
        }

        return id;
    }

    /**
     * Updates incident data
     * @param request UpdateIncidentRequest object
     * @return updated IncidentData object, null if unable to update
     */
    public IncidentData updateIncident(UpdateIncidentRequest request) {
        IncidentData updatedIncidentData = null;
        Optional<IncidentData> optIncidentData = repo.findById(request.id());
        if(optIncidentData.isPresent()) {
            IncidentData incidentData = optIncidentData.get();
            if(incidentData.getUserInfo().getEmail().equals(request.email())) {
                if(incidentData.getStatus().equals(IncidentStatus.Closed)) {
                    throw new IncidentException("Request status is closed, cannot be modified", HttpStatus.BAD_REQUEST);
                } else {
                    incidentData.setIncidentDetails(request.incidentDetails());
                    try {
                        incidentData.setPriority(IncidentPriority.valueOf(request.priority()));
                        incidentData.setStatus(IncidentStatus.valueOf(request.status()));
                    } catch (IllegalArgumentException ex) {
                        throw new IncidentException("Invalid values of Priority or Status", HttpStatus.BAD_REQUEST);
                    }
                    updatedIncidentData = repo.save(incidentData);
                }
            } else {
                throw new IncidentException("User not Authorised to update incident", HttpStatus.UNAUTHORIZED);
            }
        } else {
            throw new IncidentException("Incident not found in Database", HttpStatus.NOT_FOUND);
        }
        return updatedIncidentData;
    }
}

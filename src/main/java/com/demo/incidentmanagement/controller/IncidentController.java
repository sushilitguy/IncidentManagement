package com.demo.incidentmanagement.controller;

import com.demo.incidentmanagement.dto.AddIncidentRequest;
import com.demo.incidentmanagement.dto.UpdateIncidentRequest;
import com.demo.incidentmanagement.model.IncidentData;
import com.demo.incidentmanagement.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller to handle Incident management functionality
 */
@RestController
public class IncidentController {
    private final IncidentService service;

    /**
     * Constructor
     * @param service IncidentService object
     */
    @Autowired
    public IncidentController(IncidentService service) {
        this.service = service;
    }

    /**
     * Returns incidents created by user
     * @param email User email
     * @return incidents created by user
     */
    @GetMapping("/incident/user/{email}")
    public ResponseEntity<List<IncidentData>> getUsersAllIncidents(@PathVariable String email) {
        List<IncidentData> lstIncidents = service.getUsersAllIncidents(email);

        if(!lstIncidents.isEmpty()) {
            return new ResponseEntity<>(lstIncidents, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Returns Incident object if incident id is created by given user
     * @param incidentId id of incident
     * @param email user's email
     * @return IncidentData object
     */
    @GetMapping("/incident/{id}/user/{email}")
    public ResponseEntity<IncidentData> getUserIncidentById(@PathVariable("id") String incidentId, @PathVariable String email){
        Optional<IncidentData> incidentData = service.getUserIncidentById(incidentId, email);

        return incidentData.map(data -> new ResponseEntity<>(data, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Saves incident created by user
     * @param request AddIncidentRequest object
     * @return Added IncidentData object
     */
    @PostMapping("/incident")
    public ResponseEntity<IncidentData> addIncident(@RequestBody AddIncidentRequest request) {
        IncidentData incidentData = service.addIncident(request);

        if(incidentData != null) {
            return new ResponseEntity<>(incidentData, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates incident created by user only if it's created by them
     * @param request UpdateIncidentRequest object
     * @return Updated IncidentData object
     */
    @PutMapping("/incident")
    public ResponseEntity<IncidentData> updateIncident(@RequestBody UpdateIncidentRequest request) {
        IncidentData incidentData = service.updateIncident(request);

        if(incidentData != null) {
            return new ResponseEntity<>(incidentData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

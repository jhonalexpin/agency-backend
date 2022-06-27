package com.flixbus.agency.controllers;

import com.flixbus.agency.model.Agency;
import com.flixbus.agency.services.AgencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/agency")
@RequiredArgsConstructor
public class AgencyController {

    private final AgencyService agencyService;

    @PostMapping
    public ResponseEntity<?> createAgency(@RequestBody final Agency agency) {
        try {
            log.info("Started create agency process");
            final Optional<Agency> agencyCreated = agencyService.createAgency(agency);
            if (agencyCreated.isPresent()) {
                return new ResponseEntity<>(agencyCreated.get(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Error creating a new agency", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } finally {
            log.info("Finished create agency process");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAgencies() {
        try {
            log.info("Started get all agency process");
            final List<Agency> agencies = agencyService.getAllAgencies();
            return new ResponseEntity<>(agencies, HttpStatus.OK);
        } finally {
            log.info("Finished get all agency process");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAgency(@PathVariable final String id) {
        try {
            log.info("Started delete agency process");
            agencyService.deleteAgency(id);
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (Exception ex) {
            String errorMsg = String.format("Error deleting agency id=%s",id);
            log.error(errorMsg, ex);
            return new ResponseEntity<>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            log.info("Finished delete agency process");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAgency(@PathVariable final String id,
                                          @RequestBody final Agency agency) {
        try {
            log.info("Started update agency process id={}", id);
            final Agency agencyUpdated = agencyService.updateAgency(id, agency);
            return new ResponseEntity<>(agencyUpdated, HttpStatus.OK);
        } catch (Exception ex) {
            String errorMsg = String.format("Error updating agency id=%s",id);
            log.error(errorMsg, ex);
            return new ResponseEntity<>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            log.info("Finished update agency process");
        }
    }

}

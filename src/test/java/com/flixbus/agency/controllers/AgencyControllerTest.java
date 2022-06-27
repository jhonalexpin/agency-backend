package com.flixbus.agency.controllers;

import com.flixbus.agency.model.Agency;
import com.flixbus.agency.services.AgencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AgencyControllerTest {

    private static String TEST_ID = "test_id";
    private static String TEST_NAME = "test_name";
    private static String TEST_COUNTRY = "test_country";
    private static String TEST_NAME_2 = "test_name2";
    private static String TEST_COUNTRY_2 = "test_country2";
    private List<Agency> agencies;
    private Agency agency;
    private Agency agencySaved;
    private Agency agencyToBeUpdated;
    private Agency agencyUpdated;

    @Mock
    private AgencyService agencyService;

    @InjectMocks
    private AgencyController agencyController;

    @BeforeEach
    public void init() {
        agency = Agency.builder()
                .name(TEST_NAME)
                .country(TEST_COUNTRY)
                .build();
        agencySaved = Agency.builder()
                .id(TEST_ID)
                .name(TEST_NAME)
                .country(TEST_COUNTRY)
                .build();
        agencyToBeUpdated = Agency.builder()
                .name(TEST_NAME_2)
                .country(TEST_COUNTRY_2)
                .build();
        agencyUpdated = Agency.builder()
                .id(TEST_ID)
                .name(TEST_NAME_2)
                .country(TEST_COUNTRY_2)
                .build();
        agencies = new ArrayList<>();
        agencies.add(agencySaved);
    }

    @Test
    public void testCreateAgency() {
        Optional<Agency> agencyCreated = Optional.of(agencySaved);
        when(agencyService.createAgency(any(Agency.class))).thenReturn(agencyCreated);
        ResponseEntity<?> response = agencyController.createAgency(agency);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testCreateAgency_Error() {
        when(agencyService.createAgency(any(Agency.class))).thenReturn(Optional.empty());
        ResponseEntity<?> response = agencyController.createAgency(agency);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetAllAgencies() {
        when(agencyService.getAllAgencies()).thenReturn(agencies);
        ResponseEntity<?> response = agencyController.getAllAgencies();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteAgency() {
        ResponseEntity<?> response = agencyController.deleteAgency(TEST_ID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteAgency_Error() {
        doThrow(new RuntimeException()).when(agencyService).deleteAgency(anyString());
        ResponseEntity<?> response = agencyController.deleteAgency(TEST_ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateAgency() {
        when(agencyService.updateAgency(anyString(), any(Agency.class))).thenReturn(agencyUpdated);
        ResponseEntity<?> response = agencyController.updateAgency(TEST_ID, agencyToBeUpdated);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateAgency_Error() {
        when(agencyService.updateAgency(anyString(), any(Agency.class))).thenThrow(new RuntimeException());
        ResponseEntity<?> response = agencyController.updateAgency(TEST_ID, agencyToBeUpdated);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}

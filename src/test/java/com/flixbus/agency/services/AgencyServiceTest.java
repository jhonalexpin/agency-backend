package com.flixbus.agency.services;

import com.flixbus.agency.model.Agency;
import com.flixbus.agency.repositories.AgencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgencyServiceTest {

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
    private AgencyRepository agencyRepository;

    @InjectMocks
    private AgencyService agencyService;

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
    public void testGetAllAgencies() {
        when(agencyRepository.findAll()).thenReturn(agencies);
        List<Agency> agenciesRetrieved = agencyService.getAllAgencies();
        assertTrue(!agenciesRetrieved.isEmpty());
        verify(agencyRepository).findAll();
    }

    @Test
    public void testGetAllAgencies_Exception() {
        when(agencyRepository.findAll()).thenThrow(new RuntimeException());
        List<Agency> agenciesRetrieved = agencyService.getAllAgencies();
        assertTrue(agenciesRetrieved.isEmpty());
        verify(agencyRepository).findAll();
    }

    @Test
    public void testCreateAgency() {
        when(agencyRepository.save(any(Agency.class))).thenReturn(agencySaved);
        Optional<Agency> agencyCreated = agencyService.createAgency(agency);
        assertTrue(agencyCreated.isPresent());
        assertEquals(agencyCreated.get().getName(), agency.getName());
        verify(agencyRepository).save(any(Agency.class));
    }

    @Test
    public void testCreateAgency_Exception() {
        when(agencyRepository.save(any(Agency.class))).thenThrow(new RuntimeException());
        Optional<Agency> agencyCreated = agencyService.createAgency(agency);
        assertFalse(agencyCreated.isPresent());
        verify(agencyRepository).save(any(Agency.class));
    }

    @Test
    public void testDeleteAgency() {
        Optional<Agency> agencyRetrieved = Optional.of(agencySaved);
        when(agencyRepository.findById(anyString())).thenReturn(agencyRetrieved);
        agencyService.deleteAgency(TEST_ID);
        verify(agencyRepository).findById(anyString());
        verify(agencyRepository).deleteById(anyString());
    }

    @Test
    public void testDeleteAgency_AgencyNotFound() {
        when(agencyRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> agencyService.deleteAgency(TEST_ID));
        verify(agencyRepository).findById(anyString());
        verify(agencyRepository, never()).deleteById(anyString());
    }

    @Test
    public void testDeleteAgency_Exception() {
        Optional<Agency> agencyRetrieved = Optional.of(agencySaved);
        when(agencyRepository.findById(anyString())).thenReturn(agencyRetrieved);
        doThrow(new RuntimeException()).when(agencyRepository).deleteById(anyString());
        assertThrows(RuntimeException.class, () -> agencyService.deleteAgency(TEST_ID));
        verify(agencyRepository).findById(anyString());
        verify(agencyRepository).deleteById(anyString());
    }

    @Test
    public void testUpdateAgency() {
        Optional<Agency> agencyRetrieved = Optional.of(agencySaved);
        when(agencyRepository.findById(anyString())).thenReturn(agencyRetrieved);
        when(agencyRepository.save(any(Agency.class))).thenReturn(agencyUpdated);

        Agency agencyUpd = agencyService.updateAgency(TEST_ID, agencyToBeUpdated);
        assertEquals(agencyToBeUpdated.getName(), agencyUpd.getName());
        verify(agencyRepository).findById(anyString());
        verify(agencyRepository).save(any(Agency.class));
    }

    @Test
    public void testUpdateAgency_AgencyNotFound() {
        when(agencyRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> agencyService.updateAgency(TEST_ID, agencyToBeUpdated));
        verify(agencyRepository).findById(anyString());
        verify(agencyRepository, never()).save(any(Agency.class));
    }

    @Test
    public void testUpdateAgency_Exception() {
        Optional<Agency> agencyRetrieved = Optional.of(agencySaved);
        when(agencyRepository.findById(anyString())).thenReturn(agencyRetrieved);
        when(agencyRepository.save(any(Agency.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> agencyService.updateAgency(TEST_ID, agencyToBeUpdated));
        verify(agencyRepository).findById(anyString());
        verify(agencyRepository).save(any(Agency.class));
    }
}

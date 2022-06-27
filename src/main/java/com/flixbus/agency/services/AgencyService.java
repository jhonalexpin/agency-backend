package com.flixbus.agency.services;

import com.flixbus.agency.model.Agency;
import com.flixbus.agency.repositories.AgencyRepository;
import com.flixbus.agency.utils.BeanCustomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AgencyService {

    private final AgencyRepository agencyRepository;

    public List<Agency> getAllAgencies() {
        log.info("Started getting agencies process from database");
        try {
            return agencyRepository.findAll();
        } catch (Exception ex) {
            log.error("Error getting agencies from database", ex);
            return List.of();
        } finally {
            log.info("Finished getting agencies process from database");
        }
    }

    public Optional<Agency> createAgency(final Agency agency) {
        log.info("Started create agency process in database");
        try {
            final Agency agencyCreated = agencyRepository.save(agency);
            return Optional.of(agencyCreated);
        } catch (Exception ex) {
            log.error("Error creating agency in database", ex);
            return Optional.empty();
        } finally {
            log.info("Finished create agency process in database");
        }
    }

    public void deleteAgency(String id) {
        log.info("Started delete agency process in the database");
        try {
            agencyRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Agency not found"));
            agencyRepository.deleteById(id);
        } finally {
            log.info("Finished delete agency process in the database");
        }
    }

    public Agency updateAgency(final String id, final Agency agencyToBeUpdated) {
        log.info("Started delete agency process in the database");
        try {
            final Agency agency = agencyRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Agency not found"));
            final BeanCustomUtil<Agency> beanCustomUtil = new BeanCustomUtil<>();
            Agency agencyUpdated = beanCustomUtil.copyNonNullProperties(agencyToBeUpdated, agency);
            return agencyRepository.save(agencyUpdated);
        } finally {
            log.info("Finished delete agency process in the database");
        }
    }

}

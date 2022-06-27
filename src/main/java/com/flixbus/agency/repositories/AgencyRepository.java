package com.flixbus.agency.repositories;

import com.flixbus.agency.model.Agency;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyRepository extends MongoRepository<Agency, String> {
}

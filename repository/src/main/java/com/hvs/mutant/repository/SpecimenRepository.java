package com.hvs.mutant.repository;

import com.hvs.mutant.model.Specimen;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to Specimen Model extends of MongoRepository (include CRUD methods to mongo connections)
 */
@Repository
public interface SpecimenRepository extends MongoRepository<Specimen, String> {

}

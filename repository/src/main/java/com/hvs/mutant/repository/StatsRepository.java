package com.hvs.mutant.repository;


import com.hvs.mutant.model.Stats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends MongoRepository<Stats, String> {

}

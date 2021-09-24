package com.talmer.servicedesk.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.talmer.servicedesk.domain.Person;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {

	Optional<Person> findByEmail(String email);
}

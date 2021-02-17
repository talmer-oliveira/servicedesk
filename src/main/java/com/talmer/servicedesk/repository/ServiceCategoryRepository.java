package com.talmer.servicedesk.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.talmer.servicedesk.domain.ServiceCategory;

@Repository
public interface ServiceCategoryRepository extends MongoRepository<ServiceCategory, String> {

	public Optional<ServiceCategory> findByName(String name);
}

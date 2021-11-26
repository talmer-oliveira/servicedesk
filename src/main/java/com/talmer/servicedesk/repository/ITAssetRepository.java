package com.talmer.servicedesk.repository;

import java.util.Optional;

import com.talmer.servicedesk.domain.ITAsset;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITAssetRepository extends MongoRepository<ITAsset, String>{
    
    Optional<ITAsset> findByTag(String tag);
}

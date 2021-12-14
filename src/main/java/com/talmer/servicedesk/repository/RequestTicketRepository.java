package com.talmer.servicedesk.repository;

import com.talmer.servicedesk.domain.RequestTicket;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestTicketRepository extends MongoRepository<RequestTicket, String>{
    
}

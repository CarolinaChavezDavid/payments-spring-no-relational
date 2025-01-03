package com.payments.no.relational.repository;

import com.payments.no.relational.model.Card;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CardRepository extends MongoRepository<Card, String> {
}

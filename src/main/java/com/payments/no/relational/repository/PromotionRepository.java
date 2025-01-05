package com.payments.no.relational.repository;

import com.payments.no.relational.model.Promotion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PromotionRepository extends MongoRepository<Promotion, String> {
    Optional<Promotion> findByCode(String code);
}

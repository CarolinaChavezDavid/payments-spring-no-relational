package com.payments.no.relational.repository;

import com.payments.no.relational.model.Financing;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface Promotion extends MongoRepository<Promotion, String> {
    List<Promotion> findByCode(String code);

    List<Financing> findByType(String type);
}

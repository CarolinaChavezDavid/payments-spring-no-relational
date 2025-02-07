package com.payments.no.relational.repository;

import com.payments.no.relational.model.Quota;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuotaRepository extends MongoRepository<Quota, String> {
}

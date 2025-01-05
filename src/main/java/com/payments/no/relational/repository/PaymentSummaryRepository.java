package com.payments.no.relational.repository;

import com.payments.no.relational.model.PaymentSummary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentSummaryRepository extends MongoRepository<PaymentSummary, String> {
}

package com.payments.no.relational.repository;

import com.payments.no.relational.model.PurchaseMonthlyPayments;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PurchaseMonthlyPaymentRepository extends MongoRepository<PurchaseMonthlyPayments, String> {
}

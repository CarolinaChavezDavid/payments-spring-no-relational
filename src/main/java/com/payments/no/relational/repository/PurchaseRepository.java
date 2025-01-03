package com.payments.no.relational.repository;

import com.payments.no.relational.model.Purchase;
import com.payments.no.relational.model.PurchaseMonthlyPayments;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PurchaseRepository extends MongoRepository<Purchase, String> {
    List<PurchaseMonthlyPayments> findByType(String type);
}

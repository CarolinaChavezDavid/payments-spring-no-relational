package com.payments.no.relational.repository;

import com.payments.no.relational.model.Promotion;
import com.payments.no.relational.model.Purchase;
import com.payments.no.relational.model.Quota;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PurchaseRepository extends MongoRepository<Purchase, String> {
    List<Purchase> findByValidPromotion(Promotion promotion);

    @Aggregation(pipeline = {
            "{ $match: { _class: 'PurchaseMonthlyPayments', 'quotas.month': ?0, 'quotas.year': ?1 } }",
            "{ $unwind: '$quotas' }",
            "{ $match: { 'quotas.month': ?0, 'quotas.year': ?1 } }",
            "{ $project: { _id: 0, quotas: 1 } }"
    })
    List<Quota> findQuotasByMonthAndYear(String month, String year);

}

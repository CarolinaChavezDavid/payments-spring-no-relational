package com.payments.no.relational.repository;

import com.payments.no.relational.model.Card;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CardRepository extends MongoRepository<Card, String> {
    List<Card> findByExpirationDateBetween(String fromDate, String toDate);

    @Aggregation(pipeline = {
            "{ $lookup: { from: 'purchases', localField: '_id', foreignField: 'cardId', as: 'purchases' } }",
            "{ $addFields: { purchaseCount: { $size: '$purchases' } } }",
            "{ $sort: { purchaseCount: -1 } }",
            "{ $skip: ?0 }",
            "{ $limit: ?1 }"
    })
    List<Card> findTop10CardsWithMostPurchases(int skip, int limit);


}

package com.payments.no.relational.repository;

import com.payments.no.relational.model.Card;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface CardRepository extends MongoRepository<Card, String> {
    List<Card> findByExpirationDateBetween(LocalDate fromDate, LocalDate toDate);

    Card findByCardNumber(String card_number);

    @Aggregation(pipeline = {
            "{ $lookup: { from: 'purchases', localField: '_id', foreignField: 'cardId', as: 'purchases' } }",
            "{ $addFields: { purchaseCount: { $size: '$purchases' } } }",
            "{ $sort: { purchaseCount: -1 } }",
            "{ $skip: ?0 }",
            "{ $limit: ?1 }"
    })
    List<Card> findTop10CardsWithMostPurchases(int skip, int limit);


}

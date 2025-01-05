package com.payments.no.relational.repository;

import com.payments.no.relational.model.Promotion;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PromotionRepository extends MongoRepository<Promotion, String> {
    Optional<Promotion> findByCode(String code);

    @Aggregation(pipeline = {
            "{ $lookup: { from: 'purchases', localField: '_id', foreignField: 'validPromotion.$id', as: 'purchases' } }",
            "{ $project: { _id: 1, code: 1, promotionTitle: 1, nameStore: 1, purchasesCount: { $size: '$purchases' } } }",
            "{ $sort: { purchasesCount: -1 } }",
            "{ $limit: 1 }"
    })
    List<Promotion> findMostUsedPromotion();

    @Query("{ 'cuitStore': ?0, 'validityStartDate': { $gte: ?1 }, 'validityEndDate': { $lte: ?2 } }")
    List<Promotion> findValidPromotionsInRange(String cuitStore, LocalDate validityStartDate, LocalDate validityEndDate);

}

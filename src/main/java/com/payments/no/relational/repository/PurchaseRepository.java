package com.payments.no.relational.repository;

import com.payments.no.relational.model.Promotion;
import com.payments.no.relational.model.Purchase;
import com.payments.no.relational.model.PurchaseSinglePayment;
import com.payments.no.relational.model.Quota;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Map;

public interface PurchaseRepository extends MongoRepository<Purchase, String> {
    List<Purchase> findByValidPromotion(Promotion promotion);

    @Aggregation(pipeline = {
            "{ $match: { type: 'PurchaseSinglePayment', 'card.$id': ObjectId(?0) } }",
            "{ $match: { $expr: { $and: [ { $eq: [ { $month: '$purchaseDate' }, ?1 ] }, { $eq: [ { $year: '$purchaseDate' }, ?2 ] } ] } } }"
    })
    List<PurchaseSinglePayment> findSinglePaymentsByCardAndDate(String cardId, String month, String year);


    @Aggregation(pipeline = {
            "{ $match: { _class: 'PurchaseMonthlyPayments', 'quotas.month': ?0, 'quotas.year': ?1 } }",
            "{ $unwind: '$quotas' }",
            "{ $match: { 'quotas.month': ?0, 'quotas.year': ?1 } }",
            "{ $project: { _id: 0, quotas: 1 } }"
    })
    List<Quota> findQuotasByMonthAndYear(String month, String year);

    @Aggregation(pipeline = {
            "{ $match: { $expr: { $and: [ { $eq: [ { $month: '$purchaseDate' }, ?0 ] }, { $eq: [ { $year: '$purchaseDate' }, ?1 ] } ] } } }",
            "{ $group: { _id: { store: '$store', cuitStore: '$cuitStore' }, totalAmount: { $sum: '$finalAmount' } } }",
            "{ $sort: { totalAmount: -1 } }",
            "{ $limit: 1 }",
            "{ $project: { _id: 0, store: '$_id.store', cuitStore: '$_id.cuitStore', totalAmount: 1 } }"
    })
    Map<String, Object> findTopStoreByMonthAndYear(String month, String year);
}

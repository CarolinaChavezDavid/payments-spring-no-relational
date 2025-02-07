package com.payments.no.relational.repository;

import com.payments.no.relational.dto.PurchaseProjection;
import com.payments.no.relational.dto.QuotaDTO;
import com.payments.no.relational.dto.TopStoreDTO;
import com.payments.no.relational.model.*;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends MongoRepository<Purchase, String> {
    Purchase findByPaymentVoucher(String purchase_payment_voucher);

    @Query(value="{'_id':?0}", fields = "{'paymentVoucher':1,'store':1,'cuitStore':1,'amount':1,'finalAmount':1,'purchaseDate':1}")
    Optional<PurchaseProjection> findByIdWithSelectedFields(String id);

    List<Purchase> findByValidPromotion(Promotion promotion);

    @Aggregation(pipeline = {
            "{ $match: { type: 'PurchaseMonthlyPayments', 'card.$id': ObjectId(?2) } }",
            "{ $unwind: '$quotas' }",
            "{ $match: { 'quotas.monthh': ?0, 'quotas.yearr': ?1 } }",
            "{ $project: { _id: 0, 'id': '$quotas._id', 'number': '$quotas.number', 'price': '$quotas.price', 'monthh': '$quotas.monthh', 'yearr': '$quotas.yearr' } }"
    })
List<QuotaDTO> findPurchasesWithQuotasByMonthAndYear(String month, String year, String cardId);

    @Aggregation(pipeline = {
            "{ $match: { type: 'PurchaseSinglePayment', 'card.$id': ObjectId(?2) } }",
            "{ $match: { 'purchaseDate': { $gte: ?0, $lt: ?1 } } }"
    })
    List<PurchaseSinglePayment> findByPurchaseDateAndCardId(LocalDate startDate, LocalDate endDate, String cardId);

    @Aggregation(pipeline = {
            "{ $match: { $expr: { $and: [ { $eq: [ { $month: '$purchaseDate' }, ?0 ] }, { $eq: [ { $year: '$purchaseDate' }, ?1 ] } ] } } }",
            "{ $group: { _id: { store: '$store', cuitStore: '$cuitStore' }, totalAmount: { $sum: '$finalAmount' } } }",
            "{ $sort: { totalAmount: -1 } }",
            "{ $limit: 1 }",
            "{ $project: { _id: 0, store: '$_id.store', cuitStore: '$_id.cuitStore', totalAmount: 1 } }"
    })
    TopStoreDTO findTopStoreByMonthAndYear(int month, int year);
}

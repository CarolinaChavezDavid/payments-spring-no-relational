package com.payments.no.relational.service;

import com.payments.no.relational.exception.PaymentsException;
import com.payments.no.relational.model.Card;
import com.payments.no.relational.model.PaymentSummary;
import com.payments.no.relational.model.PurchaseSinglePayment;
import com.payments.no.relational.model.Quota;
import com.payments.no.relational.repository.CardRepository;
import com.payments.no.relational.repository.PaymentSummaryRepository;
import com.payments.no.relational.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentSummaryServiceImpl implements PaymentSummaryService {
    private PurchaseRepository purchaseRepository;
    private CardRepository cardRepository;
    private PaymentSummaryRepository paymentSummaryRepository;

    @Autowired
    public PaymentSummaryServiceImpl(
            PurchaseRepository purchaseRepository,
            CardRepository cardRepository,
            PaymentSummaryRepository paymentSummaryRepository
    ){
        this.purchaseRepository = purchaseRepository;
        this.cardRepository = cardRepository;
        this.paymentSummaryRepository = paymentSummaryRepository;
    }

    // 3) Generar el total de pago de un mes dado, informando las compras correspondientes
    @Override
    public PaymentSummary getPaymentSummary(String cardId, String month, String year) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new PaymentsException("Card not found for ID: " + cardId));

        List<Quota> monthlyPayments = purchaseRepository.findQuotasByMonthAndYear(month, year);

        List<PurchaseSinglePayment> singlePayments = purchaseRepository
                .findSinglePaymentsByCardAndDate(cardId, month, year);

        float totalPrice = calculateTotalPrice(monthlyPayments, singlePayments);
        LocalDate firstExpiration = LocalDate.of(Integer.parseInt(year),Integer.parseInt(month), 1).plusMonths(1);
        LocalDate secondExpiration = firstExpiration.withDayOfMonth(15);
        PaymentSummary summary = new PaymentSummary();
        summary.setCode(UUID.randomUUID().toString());
        summary.setSummaryMonth(month);
        summary.setSummaryYear(year);
        summary.setFirstExpiration(firstExpiration);
        summary.setSecondExpiration(secondExpiration);
        summary.setSurchargePercentage(5.0f);
        summary.setTotalPrice(totalPrice);
        summary.setCard(card);
        summary.setQuotasPayments(new HashSet<>(monthlyPayments));
        summary.setCashPayments(new HashSet<>(singlePayments));

        return paymentSummaryRepository.save(summary);
    }

    private float calculateTotalPrice(List<Quota> quotas,
                                      List<PurchaseSinglePayment> singlePayments) {
        float monthlyTotal = (float) quotas.stream()
                .mapToDouble(Quota::getPrice)
                .sum();

        float singleTotal = (float) singlePayments.stream()
                .mapToDouble(PurchaseSinglePayment::getFinalAmount)
                .sum();

        return (monthlyTotal + singleTotal);
    }
}

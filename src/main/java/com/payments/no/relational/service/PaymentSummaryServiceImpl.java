package com.payments.no.relational.service;

import com.payments.no.relational.dto.QuotaDTO;
import com.payments.no.relational.exception.PaymentsException;
import com.payments.no.relational.model.*;
import com.payments.no.relational.repository.CardRepository;
import com.payments.no.relational.repository.PaymentSummaryRepository;
import com.payments.no.relational.repository.PurchaseRepository;
import com.payments.no.relational.repository.QuotaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentSummaryServiceImpl implements PaymentSummaryService {
    Logger logger = LoggerFactory.getLogger(PaymentSummaryServiceImpl.class);

    private PurchaseRepository purchaseRepository;
    private CardRepository cardRepository;
    private PaymentSummaryRepository paymentSummaryRepository;
    private QuotaRepository quotaRepository;

    @Autowired
    public PaymentSummaryServiceImpl(
            PurchaseRepository purchaseRepository,
            CardRepository cardRepository,
            PaymentSummaryRepository paymentSummaryRepository,
            QuotaRepository quotaRepository
    ){
        this.purchaseRepository = purchaseRepository;
        this.cardRepository = cardRepository;
        this.paymentSummaryRepository = paymentSummaryRepository;
        this.quotaRepository = quotaRepository;
    }

    // 3) Generar el total de pago de un mes dado, informando las compras correspondientes
    @Override
    public PaymentSummary getPaymentSummary(String cardId, String month, String year) {
        LocalDate startDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), 1);
        LocalDate endDate = startDate.plusMonths(1);
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new PaymentsException("Card not found for ID: " + cardId));
        List<PurchaseSinglePayment> singlePayments =  purchaseRepository.findByPurchaseDateAndCardId(startDate, endDate, cardId);
        List<QuotaDTO> monthlyPayments = purchaseRepository.findPurchasesWithQuotasByMonthAndYear(month, year, cardId);
        List<Quota> quotaList = monthlyPayments.stream()
                .map(quota -> quotaRepository.findById(quota.getId()).get())
                .toList();
        logger.info("monthlyPayments", monthlyPayments);
        float totalPrice = 0f;
        calculateTotalPrice(monthlyPayments, singlePayments);
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
        summary.setQuotasPayments(new HashSet<>(quotaList));
        summary.setCashPayments(new HashSet<>(singlePayments));

        return paymentSummaryRepository.save(summary);
    }

    private float calculateTotalPrice(List<QuotaDTO> quotas,
                                      List<PurchaseSinglePayment> singlePayments) {
        float monthlyTotal = (float) quotas.stream()
                .mapToDouble(QuotaDTO::getPrice)
                .sum();

        float singleTotal = (float) singlePayments.stream()
                .mapToDouble(PurchaseSinglePayment::getFinalAmount)
                .sum();

        return (monthlyTotal + singleTotal);
    }
}

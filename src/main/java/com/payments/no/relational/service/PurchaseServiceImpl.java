package com.payments.no.relational.service;

import com.payments.no.relational.dto.MonthlyPaymentPurchaseDTO;
import com.payments.no.relational.dto.PurchaseProjection;
import com.payments.no.relational.dto.SinglePurchaseDTO;
import com.payments.no.relational.dto.TopStoreDTO;
import com.payments.no.relational.exception.PaymentsException;
import com.payments.no.relational.model.*;
import com.payments.no.relational.repository.CardRepository;
import com.payments.no.relational.repository.PromotionRepository;
import com.payments.no.relational.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final CardRepository cardRepository;
    private final PromotionRepository promotionRepository;

    @Autowired
    public PurchaseServiceImpl(
            PurchaseRepository purchaseRepository,
            CardRepository cardRepository,
            PromotionRepository promotionRepository
    ) {
        this.purchaseRepository = purchaseRepository;
        this.cardRepository = cardRepository;
        this.promotionRepository = promotionRepository;
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    @Override
    public Purchase getPurchaseById(String id) {
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(id);
        if(purchaseOptional.isPresent()) {
            return purchaseOptional.get();
        } else {
            throw new PaymentsException("There's no purchase related to the id");
        }
    }

    @Override
    public Purchase createPurchase(Purchase purchase) {
        Optional<Card> cardOptional = cardRepository.findById(purchase.getCard().getId());
        if (cardOptional.isPresent()) {
            return purchaseRepository.save(purchase);
        } else {
            throw new PaymentsException("There's an error with the purchase card");
        }
    }

    @Override
    public PurchaseMonthlyPayments createPurchaseMonthlyPayments(MonthlyPaymentPurchaseDTO monthlyPurchase) {
        Optional<Card> cardOptional = cardRepository.findById(monthlyPurchase.getCardId());

        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            PurchaseMonthlyPayments monthlyPayments = new PurchaseMonthlyPayments();
            monthlyPayments.setPaymentVoucher(monthlyPurchase.getPaymentVoucher());
            monthlyPayments.setStore(monthlyPurchase.getStore());
            monthlyPayments.setCuitStore(monthlyPurchase.getCuitStore());
            monthlyPayments.setAmount(monthlyPurchase.getAmount());
            monthlyPayments.setFinalAmount(monthlyPurchase.getAmount());
            monthlyPurchase.setFinalAmount(monthlyPurchase.getAmount());

            monthlyPayments.setNumberOfQuotas(monthlyPurchase.getNumberOfQuotas());
            monthlyPayments.setInterest(monthlyPurchase.getInterest());
            int numberOfQuotas = monthlyPurchase.getNumberOfQuotas();
            float quotaValue = monthlyPurchase.getAmount() + (monthlyPurchase.getAmount() * monthlyPurchase.getInterest());

            Set<Quota> quotas = new HashSet<>();
            List<String> monthsList = getNextNMonths(numberOfQuotas);

            for (int i = 0; i < monthlyPayments.getNumberOfQuotas(); i++) {
                Quota currentQuota = new Quota();
                currentQuota.setNumber(i);
                currentQuota.setPrice(quotaValue);
                currentQuota.setMonthh(monthsList.get(i).substring(0, 2));
                currentQuota.setYearr(monthsList.get(i).substring(3, 7));
                quotas.add(currentQuota);
            }

            monthlyPayments.setQuotas(quotas);

            if (monthlyPurchase.getValidPromotionCode() != null && !monthlyPurchase.getValidPromotionCode().isEmpty()) {
                Optional<Promotion> validPromo = promotionRepository.findByCode(monthlyPurchase.getValidPromotionCode());
                if (validPromo.isPresent()) {
                    Promotion promo = validPromo.get();
                    monthlyPayments.setValidPromotion(promo);
                    promo.addPurchase(monthlyPayments);
                }
            }
            monthlyPayments.setCard(card);
            card.addPurchase(monthlyPayments);

            return purchaseRepository.save(monthlyPayments);
        } else {
            throw new PaymentsException("There's an error with the purchase card");
        }
    }

    @Override
    public PurchaseSinglePayment createPurchaseSinglePayment(SinglePurchaseDTO singlePurchase) {
        Optional<Card> cardOptional = cardRepository.findById(singlePurchase.getCardId().toString());

        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            float finalAmount = singlePurchase.getAmount();
            PurchaseSinglePayment singlePayment = new PurchaseSinglePayment();

            singlePayment.setPaymentVoucher(singlePurchase.getPaymentVoucher());
            singlePayment.setStore(singlePurchase.getStore());
            singlePayment.setCuitStore(singlePurchase.getCuitStore());
            singlePayment.setAmount(singlePurchase.getAmount());

            finalAmount = finalAmount - (finalAmount * singlePurchase.getStoreDiscount());
            singlePayment.setFinalAmount(finalAmount);
            singlePurchase.setFinalAmount(finalAmount);
            singlePayment.setStoreDiscount(singlePurchase.getStoreDiscount());

            if (singlePurchase.getValidPromotionCode() != null && !singlePurchase.getValidPromotionCode().isEmpty()) {
                Optional<Promotion> validPromo = promotionRepository.findByCode(singlePurchase.getValidPromotionCode());
                if (validPromo.isPresent()) {
                    Promotion promo = validPromo.get();
                    singlePayment.setValidPromotion(promo);
                    promo.addPurchase(singlePayment);
                }
            }
            singlePayment.setCard(card);
            card.addPurchase(singlePayment);
            return purchaseRepository.save(singlePayment);
        } else {
            throw new PaymentsException("There's an error with the purchase card");
        }
    }


    public static List<String> getNextNMonths(int n) {
        List<String> months = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

        for (int i = 0; i < n; i++) {
            LocalDate monthDate = currentDate.plusMonths(i);
            months.add(monthDate.format(formatter));
        }

        return months;
    }

    @Override
    public TopStoreDTO getTopStore(int month, int year) {
        TopStoreDTO res = purchaseRepository.findTopStoreByMonthAndYear(month, year);
        System.out.println("AAAAA");
        System.out.println(res);
        return res;
    }
}

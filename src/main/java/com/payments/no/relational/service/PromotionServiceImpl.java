package com.payments.no.relational.service;

import com.payments.no.relational.dto.FinancingDTO;
import com.payments.no.relational.dto.PromotionProjection;
import com.payments.no.relational.exception.PaymentsException;
import com.payments.no.relational.model.Bank;
import com.payments.no.relational.model.Financing;
import com.payments.no.relational.model.Promotion;
import com.payments.no.relational.model.Purchase;
import com.payments.no.relational.repository.BankRepository;
import com.payments.no.relational.repository.PromotionRepository;
import com.payments.no.relational.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;
    private final BankRepository bankRepository;
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PromotionServiceImpl(
            PromotionRepository promotionRepository,
            BankRepository bankRepository,
            PurchaseRepository purchaseRepository
    ) {
        this.promotionRepository = promotionRepository;
        this.bankRepository = bankRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    // 1) Agregar una nueva promocion de tipo Financing a un banco dado
    @Transactional
    @Override
    public Promotion saveFinancing(String bankId, FinancingDTO promo) {
        Optional<Bank> bankOptional= bankRepository.findById(bankId);
         if (bankOptional.isPresent()) {
             Bank bank = bankOptional.get();
             Financing newPromo = new Financing();
             newPromo.setCode(promo.getCode());
             newPromo.setPromotionTitle(promo.getPromotionTitle());
             newPromo.setNameStore(promo.getNameStore());
             newPromo.setCuitStore(promo.getCuitStore());
             newPromo.setValidityStartDate(promo.getValidityStartDate());
             newPromo.setValidityEndDate(promo.getValidityEndDate());
             newPromo.setComments(promo.getComments());
             newPromo.setNumberOfQuotas(promo.getNumberOfQuotas());
             newPromo.setInterest(promo.getInterest());
             Financing savedPromo = promotionRepository.save(newPromo);
             bank.getPromotions().add(savedPromo);
             bankRepository.save(bank);
             return savedPromo;
        } else {
             throw new PaymentsException("There is no bank with the Id given");
         }
    }

    // 2) Extender el tiempo de validez de una promocion
    @Override
     public Promotion extendPromotion(String promoId, LocalDate newDate) {
        Optional<Promotion> optionalPromo = promotionRepository.findById(promoId);
        if (optionalPromo.isPresent()) {
            Promotion promo = optionalPromo.get();
            if(promo.getValidityStartDate().isBefore(newDate)) {
                promo.setValidityEndDate(newDate);
                return promotionRepository.save(promo);
            } else {
                throw new PaymentsException("The new date is not valid");
            }

        }else {
            throw new PaymentsException("The promotion is not found");
        }
    }

    @Transactional
    @Override
    public String removePromotionByCode(String promotionCode) {
        Optional<Promotion> promotionOptional = promotionRepository.findByCode(promotionCode);
        if (promotionOptional.isPresent()) {
            Promotion promo = promotionOptional.get();
            List<Purchase> purchases = purchaseRepository.findByValidPromotion(promo);
            for (Purchase purchase : purchases) {
                purchase.setValidPromotion(null);
                purchaseRepository.save(purchase);
            }
            promotionRepository.delete(promo);

            return "Promotion with code " + promotionCode + " removed successfully.";
        }  else {
            return "Promotion with code " + promotionCode + "does not exist.";
        }
    }

    @Override
    public Promotion getMostUsedPromotion() {
        return promotionRepository.findMostUsedPromotion().get(0);
    }

    @Override
    public List<PromotionProjection> getValidPromotionsInRange(String cuitStore, LocalDate validityStartDate, LocalDate validityEndDate) {
        return promotionRepository.findValidPromotionsInRange(cuitStore, validityStartDate, validityEndDate);
    }
}

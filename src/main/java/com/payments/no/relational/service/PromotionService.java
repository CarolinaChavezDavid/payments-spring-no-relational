package com.payments.no.relational.service;

import com.payments.no.relational.model.Promotion;

import java.time.LocalDate;
import java.util.List;

public interface PromotionService{
    List<Promotion> getAllPromotions();
    Promotion savePromotion(String bankId, Promotion promo);
    Promotion extendPromotion(String promoId, LocalDate newDate);
    String removePromotionByCode (String promotionCode);
    Promotion getMostUsedPromotion();
    List<Promotion> getValidPromotionsInRange(String cuitStore, LocalDate validityStartDate, LocalDate validityEndDate);
}

package com.payments.no.relational.service;

import com.payments.no.relational.dto.FinancingDTO;
import com.payments.no.relational.dto.PromotionProjection;
import com.payments.no.relational.model.Promotion;

import java.time.LocalDate;
import java.util.List;

public interface PromotionService{
    List<Promotion> getAllPromotions();
    Promotion saveFinancing(String bankId, FinancingDTO promo);
    Promotion extendPromotion(String promoId, LocalDate newDate);
    String removePromotionByCode (String promotionCode);
    Promotion getMostUsedPromotion();
    List<PromotionProjection> getValidPromotionsInRange(String cuitStore, LocalDate validityStartDate, LocalDate validityEndDate);
}

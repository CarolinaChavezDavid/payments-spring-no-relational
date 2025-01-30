package com.payments.no.relational.dto;

import java.time.LocalDate;

public interface PromotionProjection {
    String getId();
    String getCode();
    String getPromotionTitle();
    String getNameStore();
    String getCuitStore();
    LocalDate getValidityStartDate();
    LocalDate getValidityEndDate();
}

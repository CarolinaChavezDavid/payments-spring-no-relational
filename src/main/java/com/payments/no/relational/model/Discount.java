package com.payments.no.relational.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "promotions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount extends Promotion {

    private float discountPercentage;

    private float priceCap;

    private boolean onlyCash;

    public Discount(String code, String promotionTitle, String nameStore, String cuitStore, LocalDate validityStartDate, LocalDate validityEndDate,
                     float discountPercentage, float priceCap, boolean onlyCash){
        super(code, promotionTitle,nameStore,cuitStore,validityStartDate,validityEndDate);
        this.discountPercentage = discountPercentage;
        this.priceCap = priceCap;
        this.onlyCash = onlyCash;

    }
}


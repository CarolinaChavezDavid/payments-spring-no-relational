package com.payments.no.relational.model;

import lombok.*;
import org.springframework.cglib.core.Local;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "promotions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Financing extends Promotion {

    private int numberOfQuotas;

    private float interest;

    public Financing(String code, String promotionTitle, String nameStore, String cuitStore, LocalDate validityStartDate, LocalDate validityEndDate,
                     int numberOfQuotas, float interest){
        super(code, promotionTitle,nameStore,cuitStore,validityStartDate,validityEndDate);
        this.numberOfQuotas = numberOfQuotas;
        this.interest = interest;

    }
}
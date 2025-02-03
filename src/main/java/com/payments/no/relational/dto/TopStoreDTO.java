package com.payments.no.relational.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopStoreDTO {
    private Double totalAmount;
    private String store;
    private String cuitStore;
}

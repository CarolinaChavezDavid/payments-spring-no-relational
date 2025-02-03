package com.payments.no.relational.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PreTaskDTO {
    private String bank_id;
    private String promotion_id;
    private String card_id;
    private String promotion_to_delete_id;
    private String purchase_id;
}

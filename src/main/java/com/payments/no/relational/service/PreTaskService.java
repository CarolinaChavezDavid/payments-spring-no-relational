package com.payments.no.relational.service;

import com.payments.no.relational.dto.PreTaskDTO;

public interface PreTaskService {
    PreTaskDTO getPreTaskVariables(String bank_name,
                                   String promotion_code,
                                   String card_number,
                                   String promotion_delete_code,
                                   String purchase_payment_voucher
    );

}

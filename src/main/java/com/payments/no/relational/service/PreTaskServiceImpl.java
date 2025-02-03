package com.payments.no.relational.service;

import com.payments.no.relational.dto.PreTaskDTO;
import com.payments.no.relational.model.Bank;
import com.payments.no.relational.model.Card;
import com.payments.no.relational.model.Promotion;
import com.payments.no.relational.model.Purchase;
import com.payments.no.relational.repository.BankRepository;
import com.payments.no.relational.repository.CardRepository;
import com.payments.no.relational.repository.PromotionRepository;
import com.payments.no.relational.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreTaskServiceImpl implements PreTaskService{

    private final BankRepository bankRepository;
    private final PromotionRepository promotionRepository;
    private final CardRepository cardRepository;
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PreTaskServiceImpl(
            PromotionRepository promotionRepository,
            BankRepository bankRepository,
            CardRepository cardRepository,
            PurchaseRepository purchaseRepository
    ) {
        this.promotionRepository = promotionRepository;
        this.bankRepository = bankRepository;
        this.cardRepository = cardRepository;
        this.purchaseRepository = purchaseRepository;

    }

    @Override
    public PreTaskDTO getPreTaskVariables(String bank_name,
                                          String promotion_code,
                                          String card_number,
                                          String promotion_delete_code,
                                          String purchase_payment_voucher
    ){

        Bank bank = bankRepository.findByName(bank_name);
        Promotion promotion = promotionRepository.findByCode(promotion_code).get();
        Card card = cardRepository.findByCardNumber(card_number);
        Promotion promotion_to_delete = promotionRepository.findByCode(promotion_delete_code).get();
        Purchase purchase = purchaseRepository.findByPaymentVoucher(purchase_payment_voucher);

        PreTaskDTO pretask = new PreTaskDTO();
        pretask.setBank_id(bank.getId());
        pretask.setPromotion_id(promotion.getId());
        pretask.setCard_id(card.getId());
        pretask.setPromotion_to_delete_id(promotion_to_delete.getId());
        pretask.setPurchase_id(purchase.getId());

        return pretask;
    }

}

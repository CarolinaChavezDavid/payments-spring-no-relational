package com.payments.no.relational.controller;

import com.payments.no.relational.dto.FinancingDTO;
import com.payments.no.relational.dto.PromotionProjection;
import com.payments.no.relational.model.Promotion;
import com.payments.no.relational.service.PromotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    private final PromotionService promotionService;
    Logger logger = LoggerFactory.getLogger(PromotionController.class);


    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping
    public ResponseEntity<List<Promotion>> getAllPromotions() {
        List<Promotion> promotions = promotionService.getAllPromotions();
        return ResponseEntity.ok(promotions);
    }

//    @PostMapping("/discount")
//    public ResponseEntity<Promotion> createDiscountPromotion(
//            @RequestParam String bankId,
//            @RequestBody Discount promotion
//    ) {
//        try {
//            Promotion savedPromotion = promotionService.savePromotion(bankId, promotion);
//            return ResponseEntity.ok().body(savedPromotion);
//        } catch (Exception e) {
//            logger.error("There was a error saving the promotion information", e);
//            return ResponseEntity.badRequest().build();
//        }
//    }

    // 1) Agregar una promocion de tipo Financing a un banco dado
    @PostMapping("/financing")
    public ResponseEntity<Promotion> createFinancingPromotion(
            @RequestParam String bankId,
            @RequestBody FinancingDTO promotion
    ) {
        try {
            Promotion savedPromotion = promotionService.saveFinancing(bankId, promotion);
            return ResponseEntity.ok().body(savedPromotion);
        } catch (Exception e) {
            logger.error("There was a error saving the promotion information", e);
            return ResponseEntity.badRequest().build();
        }
    }

    // 2) Extender el tiempo de validez de una promocion
    @PutMapping("/{promotionId}/validity")
    public ResponseEntity<Promotion> extendPromotion(
            @PathVariable String promotionId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate extendDate
    ) {
        try {
            Promotion savedPromotion = promotionService.extendPromotion(promotionId, extendDate);
            return ResponseEntity.ok().body(savedPromotion);
        } catch (Exception e) {
            logger.error("There was a error saving the promotion information", e);
            return ResponseEntity.badRequest().build();
        }
    }

    // 6) Eliminar una promocion a traves de su codigo
    // tener en cuenta que esta puede haber sido aplicada a alguna compra
    @DeleteMapping("/{promotionCode}")
    public ResponseEntity<String> removePromotion(@PathVariable String promotionCode) {
        try {
            String result = promotionService.removePromotionByCode(promotionCode);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("There was a error saving the promotion information", e);
            return ResponseEntity.badRequest().build();
        }
    }

    // 7) Obtener el listado de las promociones disponibles de un local entre dos fechas
    @GetMapping("/valid")
    public List<PromotionProjection> getValidPromotions(
            @RequestParam String cuit_store,
            @RequestParam String start_date,
            @RequestParam String end_date) {
        LocalDate validityStartDate = LocalDate.parse(start_date);
        LocalDate validityEndDate = LocalDate.parse(end_date);
        return promotionService.getValidPromotionsInRange(cuit_store, validityStartDate, validityEndDate);
    }


    // 9) Obtener la promocion mas utilizada en las compras registradas
    @GetMapping("/most-used")
    public Promotion getMostUsedPromotion() {
        return promotionService.getMostUsedPromotion();
    }
}
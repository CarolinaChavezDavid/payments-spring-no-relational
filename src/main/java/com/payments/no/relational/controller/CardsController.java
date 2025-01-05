package com.payments.no.relational.controller;

import com.payments.no.relational.dto.CardDTO;
import com.payments.no.relational.model.Card;
import com.payments.no.relational.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardsController {

    Logger logger = LoggerFactory.getLogger(CardsController.class);

    private final CardService cardService;

    @Autowired
    public CardsController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable String id) {
        return cardService.getCardById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Card>> getAllCards() {
        return ResponseEntity.ok().body(cardService.getAllCards());
    }

    // 4) Obtener el listado de tarjetas que vencen en los siguientes 30 dias
    @GetMapping("/expiring")
    public ResponseEntity<List<Card>> getCloseExpiringCards() {
        try {
            List<Card> cards = cardService.getCardsCloseToExpiry();
            return ResponseEntity.ok().body(cards);
        } catch (Exception e) {
            logger.error("There was a error getting the cards", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody CardDTO cardDTO) {
        try {
            Card card = cardService.createCard(cardDTO);
            return ResponseEntity.ok().body(card);
        } catch (Exception e) {
            logger.error("There was a error saving the Bank information", e);
            return ResponseEntity.badRequest().build();
        }
    }

    // 8) Obtener la informacion de las 10 tarjetas con mas compras.
    @GetMapping("/top10")
    public ResponseEntity<List<Card>> getTop10CardsWithMostPurchases() {
        List<Card> cards = cardService.getTop10CardsWithMostPurchases();
        return ResponseEntity.ok(cards);
    }
}


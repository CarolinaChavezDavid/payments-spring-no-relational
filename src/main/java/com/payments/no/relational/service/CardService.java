package com.payments.no.relational.service;

import com.payments.no.relational.dto.CardDTO;
import com.payments.no.relational.model.Card;

import java.util.List;
import java.util.Optional;

public interface CardService {
    List<Card> getAllCards();
    Optional<Card> getCardById(String id);
    Card createCard(CardDTO cardDTO);
    List<Card> getCardsCloseToExpiry();
    List<Card> getTop10CardsWithMostPurchases();
}

package oop.g8_1.lavrenko_v_a;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Deck {
    private Card card;
    private Card trump; // козырь
    private Stack<Card> gameDeck; //стартовая колода карт;

    private static Card[] createCards(){
        int length = Suit.values().length * Rank.values().length;
        Card[] cards = new Card[length];
        int count = 0;
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                Card card = new Card(suit, rank);
                cards[count] = card;
                count++;
            }
        }
        return cards;
    }

    private static Card[] shuffleCards(Card[] cards) {
        Random random = new Random();
        int length = createCards().length;

        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < length; k++) {
                int randomIndex = random.nextInt(length);
                Card temp = cards[k];
                cards[k] = cards[randomIndex];
                cards[randomIndex] = temp;
            }
        }
        return cards;
    }

    public static Stack<Card> createStartingDeck(Card[] cards, Stack<Card> gameDeck) {
        for (Card card : cards){
            gameDeck.push(card);
        }
    return gameDeck;
    }

    public Card getCardFromDeck() {
        return gameDeck.pop();
    }

    public Card createTrump(Stack<Card> gameDeck){
        trump = getTrump();
        return trump;
    }

    public Card getTrump() {
        return gameDeck.pop();
    }
}
package oop.g8_1.lavrenko_v_a.Deck;

import java.util.*;

public class Deck {
    private Stack<Card> gameDeck;


    public Deck() {
        gameDeck = new Stack<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                gameDeck.push(new Card(suit, rank));
            }
        }
        Collections.shuffle(gameDeck);
    }

    public boolean addLastCard(Card card) {
        return gameDeck.add(card);
    }

    public Card getCardFromDeck() {
        return gameDeck.pop();
    }

    public int getDeckSize() {
        return gameDeck.size();
    }


    public Stack<Card> getGameDeck(){
        return gameDeck;
    }

    /*private static Card[] createCards(){
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
    }*/

    /*private static Card[] shuffleCards(Card[] cards) {
        Random random = new Random();
        int length = cards.length;

        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < length; k++) {
                int randomIndex = random.nextInt(length);
                Card temp = cards[k];
                cards[k] = cards[randomIndex];
                cards[randomIndex] = temp;
            }
        }
        return cards;
    }*/

    /*public static Stack<Card> startingDeck(Stack<Card> gameDeck) {

        Card[] cards = shuffleCards(createCards());
        gameDeck = new Stack<>();
        for (Card card : cards){
            gameDeck.push(card);
        }
    return gameDeck;
    }*/
}

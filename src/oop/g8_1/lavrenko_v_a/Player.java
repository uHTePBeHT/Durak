package oop.g8_1.lavrenko_v_a;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Player {
    private String playerName;
    private ArrayList<Card> hand;
    private Deck gameDeck;

    public Player(String playerName, ArrayList<Card> hand) {
        this.playerName = playerName;
        this.hand = hand;
    }



    private void takeCardFromGameDeck() {
        hand.add(gameDeck.getCardFromDeck());
    }

    private void cardsNumberInHand() {

    }


    private void sortCardsInHand() { //сортировка карт в руке игрока по возрастанию значения
        if (hand.size() > 1) {
            hand.sort(new Comparator<Card>() {
                @Override
                public int compare(Card o1, Card o2) {
                    return o1.getRank().compareTo(o2.getRank());
                }
            });
        }
    }
}

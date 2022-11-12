package oop.g8_1.lavrenko_v_a.Player;

import oop.g8_1.lavrenko_v_a.Deck.Card;
import oop.g8_1.lavrenko_v_a.Deck.Deck;


import java.util.Comparator;
import java.util.List;

public class Player {
    private String playerName;
    private List<Card> hand;
    private Deck gameDeck;

    public Player(){
    }

    public Player(String playerName, List<Card> hand) {
        this.playerName = playerName;
        this.hand = hand;
    }



    private void takeCardFromGameDeck() {
        hand.add(gameDeck.getCardFromDeck());
    }

    private int cardsNumberInHand() {
        return hand.size();
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

    public List<Card> getHand() {
       return hand;
    }

    public boolean haveTrumpSuit(){
<<<<<<< HEAD
       return hand.get()
=======
       return hand.
>>>>>>> origin/main
    }
}

package oop.g8_1.lavrenko_v_a.Player;

import oop.g8_1.lavrenko_v_a.Deck.Card;
import oop.g8_1.lavrenko_v_a.Deck.Deck;
import oop.g8_1.lavrenko_v_a.Game.Game;


import java.util.Comparator;
import java.util.List;

public class Player {
    private String playerName;
    private List<Card> hand;
    private Deck gameDeck;

    public Player() {
    }

    public Player(String playerName, List<Card> hand) {
        this.playerName = playerName;
        this.hand = hand;
    }



    private void takeCardFromGameDeck() {
        hand.add(gameDeck.getCardFromDeck());
        sortCardsInHand();
    }


    private int cardsNumberInHand() {
        return hand.size();
    }


    public void sortCardsInHand() { //сортировка карт в руке игрока по возрастанию значения
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
    public String getPlayerName() {
        return playerName;
    }

    public Card throwCardToTransfer(Card cardFromTable, Card trump) {
        int index = 0;
        while (hand.get(index).getRank().ordinal() < cardFromTable.getRank().ordinal()) { // skip карт в руке до карт нужного значения
            index++;
        }
        Card tempCard = hand.get(index); //запоминаем карту
        if (hand.get(index + 1).getRank().equals(tempCard.getRank()) && !hand.get(index + 1).getRank().equals(trump.getRank())) {
            tempCard = hand.get(index + 1); // если следующая карта такого значения и не козырная, то кидаем её.
        }
        return tempCard;
    }


    public Card throwCardToAttack(Card trump) {
        int index = searchThrowCardInHandIndex(trump);
        Card tempCard = hand.get(index);
        hand.remove(index);
        return tempCard;
    }

    private int searchThrowCardInHandIndex(Card trump){
        int index = 0;
        while (index < hand.size()) {
            if (hand.get(index).getSuit().equals(trump.getSuit())){
                index++;
            }
            break;
        }
        for (; index < hand.size(); index++) {

        }
        return index;
    }


}

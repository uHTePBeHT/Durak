package oop.g8_1.lavrenko_v_a.Player;

import oop.g8_1.lavrenko_v_a.Deck.Card;
import oop.g8_1.lavrenko_v_a.Deck.Deck;
import oop.g8_1.lavrenko_v_a.Deck.Suit;
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


    /*private void takeCardFromGameDeck() {
        hand.add(gameDeck.getCardFromDeck());
        sortCardsInHand();
    }*/


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

    public boolean hasTrump(Suit getTrump) {
        boolean temp = false;
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getSuit().equals(getTrump)) {
                temp = true;
            }
        }
        return temp;
    }

    public List<Card> getHand() {
        return hand;
    }
    public String getPlayerName() {
        return playerName;
    }

    //игрок кладёт карту, чтобы перевести
    public Card throwCardToTransfer(Card cardFromTable, Suit trumpSuit) {
        int index = 0;
        Card tempCard = null;

        while (hand.get(index).getRank().ordinal() <= cardFromTable.getRank().ordinal()) {
            if (hand.get(index).getRank().ordinal() == cardFromTable.getRank().ordinal()) {
                if (tempCard == null) {
                    tempCard = hand.get(index);
                } else {
                    if (tempCard.getSuit().equals(trumpSuit)) {
                        tempCard = hand.get(index);
                    }
                }
            }
            index++;
        }
        return tempCard;
    }

    /*public Card throwCardToTransfer(Card cardFromTable, Card trump) {
        int index = 0;
        while (hand.get(index).getRank().ordinal() < cardFromTable.getRank().ordinal()) { // skip карт в руке до карт нужного значения
            index++;
        }
        Card tempCard = hand.get(index); //запоминаем карту
        if (hand.get(index + 1).getRank().equals(tempCard.getRank()) && !hand.get(index + 1).getRank().equals(trump.getRank())) {
            tempCard = hand.get(index + 1); // если следующая карта такого значения и не козырная, то кидаем её.
        }
        return tempCard;
    }*/


    public Card throwCardToAttack(Card trump) {
        int index = searchThrowCardInHandIndex(trump.getSuit());
        Card tempCard = hand.get(index);
        hand.remove(index);
        return tempCard;
    }

    private int searchThrowCardInHandIndex(Suit trumpSuit){
        int index = 0;
        if (hand.get(index).getSuit().equals(trumpSuit)) {
            for (index = 1; index < hand.size(); index++) {
                if (!hand.get(index).getSuit().equals(trumpSuit)) {
                    return index;
                }
            }
        }
        return index;
    }

    public Card throwCardToBeat(Card tableCard, Suit getTrumpSuit) {
        int index = 0;
        Card tempCard = null;
        for (; index < hand.size(); index++) {
            if (hand.get(index).getSuit().equals(getTrumpSuit) && hand.get(index).getRank().ordinal() > tableCard.getRank().ordinal()) {
                tempCard = hand.get(index);
                break;
            }
        }
        return tempCard;
    }

    public Card trumpCardToBeat(Suit getTrumpSuit) {
        int index = 0;
        Card tempCard = null;
        for (; index < hand.size(); index++) {
            if (hand.get(index).getSuit().equals(getTrumpSuit)){
                tempCard = hand.get(index);
                break;
            }
        }
        return tempCard;
    }
}

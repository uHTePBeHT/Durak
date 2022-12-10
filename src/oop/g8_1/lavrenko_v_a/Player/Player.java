package oop.g8_1.lavrenko_v_a.Player;

import oop.g8_1.lavrenko_v_a.Deck.Card;
import oop.g8_1.lavrenko_v_a.Deck.Suit;
import java.util.List;

public class Player {
    private final String playerName;
    private List<Card> hand;



    public Player(String playerName, List<Card> hand) {
        this.playerName = playerName;
        this.hand = hand;
    }


    public boolean hasTrump(Suit getTrump, Card cardNeedToBeat) {
        boolean temp = false;
        for (Card card : hand) {
            if (card.getSuit().equals(getTrump) && card.getRank().ordinal() > cardNeedToBeat.getRank().ordinal()) {
                temp = true;
                break;
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
                        break;
                    }
                }
            }
            index++;
        }
        return tempCard;
    }



    public Card throwCardToAttack(Card trump) {
        int index = 0;
        Card tempCard = hand.get(index);

        if (hand.size() > 1) {
            if (hand.get(index).getSuit().equals(trump.getSuit())) {
                for (index = 1; index < hand.size(); index++) {
                    if (!hand.get(index).getSuit().equals(trump.getSuit())) {
                        tempCard = hand.get(index);
                        break;
                    }
                }
            }
        }
        hand.remove(tempCard);
        return tempCard;
    }


    public Card throwCardToBeat(Card tableCard) {
        int index = 0;
        Card tempCard = null;
        for (; index < hand.size(); index++) {
            if (hand.get(index).getSuit().equals(tableCard.getSuit()) && hand.get(index).getRank().ordinal() > tableCard.getRank().ordinal()) {
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

package oop.g8_1.lavrenko_v_a.Game;

import com.sun.source.tree.BreakTree;
import oop.g8_1.lavrenko_v_a.CircularLinkedList.CircularLinkedListForGame;
import oop.g8_1.lavrenko_v_a.CircularLinkedList.Node;
import oop.g8_1.lavrenko_v_a.Deck.Card;
import oop.g8_1.lavrenko_v_a.Deck.Deck;
import oop.g8_1.lavrenko_v_a.Deck.Rank;
import oop.g8_1.lavrenko_v_a.Deck.Suit;
import oop.g8_1.lavrenko_v_a.Player.Player;
import java.util.*;

public class Game {
    private Card trump; // козырь
    private Deck gameDeck; // игровая колода
    public CircularLinkedListForGame playersCircle = new CircularLinkedListForGame(null, null); // игровой круг
    private int numberOfPlayers; // количество игроков
    private List<Card> table; // игровой стол

    public void startGame() {
        createGameDeck();
        gameStarts();

        playersCircle = createQueue(createNumberOfPlayers());
        trump = createTrump();
        playersCircle.traverseListForSortCards();


    }

    /*vvvvvvvvvvvvvvv Game Preparation vvvvvvvvvvvvvv*/
    private int generateNumOfPlayers() {
        int num = (int) (2 + Math.random() * 5);
        return num;
    }

    private CircularLinkedListForGame createQueue(int numberOfPlayers) {
        for (int i = 1; i <= numberOfPlayers; i++) {
            String id = Integer.toString(i);
            String name = "Player " + id;
            Player player = new Player(name, dealCards());
            playersCircle.addNode(player);
        }
        return playersCircle;
    }

    private List<Card> dealCards() { //раздаём карты
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            cards.add(gameDeck.getCardFromDeck());
        }
        return cards;
    }

    private void createGameDeck() { //создаём игровую колоду
        gameDeck = new Deck();
    }

    private Card createTrump() {
        trump = gameDeck.getCardFromDeck();
        return trump;
    }

    public Card getTrump() {
        return trump;
    }

    private void gameStarts() {
        for (int i = 0; i < 100; i++) {
            System.out.print("-");
        }
        System.out.println();
        System.out.println("The game starts.");
    }

    private int createNumberOfPlayers() {
        numberOfPlayers = generateNumOfPlayers(); //генерируем количество игроков
        System.out.println("OK, there are " + numberOfPlayers + " players.");
        return numberOfPlayers;
    }


/*vvvvvvvvvvvvv Game methods vvvvvvvvvvvvvv*/
    private String theFirstTurn() {
        Node attacker = playersCircle.getHeadPlayer();
        Node defender = playersCircle.getNextAfterHeadPlayer();
        int cardsOnTheTable = 0;

        String loserName = turn(attacker, defender, cardsOnTheTable);

        return loserName;
    }

    private String turn(Node attacker, Node defender, int cardsOnTheTable) {
        while (numberOfPlayers > 1) {
            //attack(attacker, cardsOnTheTable);
            if (table.size() <= 6) {
                table.add(attacker.getPlayer().throwCardToAttack(trump));
            }

            for (int i = 0; i < defender.getPlayer().getHand().size(); i++) {
                if (defender.getPlayer().getHand().get(i).getRank().equals(table.get(0).getRank())) {
                   table.add(defender.getPlayer().throwCardToTransfer(table.get(0), trump));
                   attacker = defender;
                   defender = playersCircle.transitionNode(defender);
                } else {
                    for (int j = 0; j < defender.getPlayer().getHand().size(); j++) {
                        if (defender.getPlayer().getHand().get(j).getSuit().equals(table.get(0).getSuit())
                                && defender.getPlayer().getHand().get(j).getRank().ordinal() > table.get(0).getRank().ordinal()) {

                        }
                    }
                }

        }
        return;
    }

    private void attack(Node attacker, int cardsOnTable) {
        if (table.size() <= 6) {
            table.add(attacker.getPlayer().throwCardToAttack(trump));
        }
    }

    private void


}

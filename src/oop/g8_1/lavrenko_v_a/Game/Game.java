package oop.g8_1.lavrenko_v_a.Game;

import oop.g8_1.lavrenko_v_a.CircularLinkedList.CircularLinkedListForGame;
import oop.g8_1.lavrenko_v_a.Deck.Card;
import oop.g8_1.lavrenko_v_a.Deck.Deck;
import oop.g8_1.lavrenko_v_a.Player.Player;
import java.util.*;

public class Game {
    private Deck gameDeck;//игровая колода
    public CircularLinkedListForGame playersCircle = new CircularLinkedListForGame(null, null);
    private Player attacker;
    private Player defender;
    private int numberOfPlayers;

    public void startGame() {
        createGameDeck();

        for (int i = 0; i < 100; i++) {
        System.out.print("-");
        }
        System.out.println();
        System.out.println("The game starts.");

        numberOfPlayers = generateNumOfPlayers(); //генерируем количество игроков
        System.out.println("OK, there are " + numberOfPlayers + " players.");
        playersCircle = createQueue(numberOfPlayers);

        gameDeck.createTrump();
        Player player = new Player();
        createFirstPLayer();

    }

    private int generateNumOfPlayers() {
        int num = (int) (2 + Math.random() * 3);
        return num;
    }

    private CircularLinkedListForGame createQueue(int numberOfPlayers){
        for (int i = 1; i <= numberOfPlayers; i++){
            String id = Integer.toString(i);
            String name = "Player " + id;
            Player player = new Player(name, dealCards());
            playersCircle.addNode(player);

        }
        return playersCircle;
    }

    private List<Card> dealCards() { //раздаём карты
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 6; i++){
            cards.add(gameDeck.getCardFromDeck());
        }
        return cards;
    }

    private void createGameDeck(){ //создаём игровую колоду
        gameDeck = new Deck();
    }


    private void createFirstPLayer() {
<<<<<<< HEAD
        Player[] findAttackerDefender = playersCircle.traverseListFirstPlayer(gameDeck.getTrump());
        attacker = findAttackerDefender[0];
        defender = findAttackerDefender[1];
    }
=======
    }


>>>>>>> origin/main
}

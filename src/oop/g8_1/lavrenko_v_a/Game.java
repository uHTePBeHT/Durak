package oop.g8_1.lavrenko_v_a;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public class Game {
    private Deck gameDeck;//игровая колода
    private Card trump;//открывается после раздачи карт игрокам
    private ArrayList<Card> playedCards; //битые карты
    private int numberOfPlayers; //2-4
    private Queue<Player> playersQueue;
    private Scanner scanner = new Scanner(System.in);

    public void startGame() {
        createGameDeck();
        for (int i = 0; i < 100; i++) {
        System.out.print("-");
        }
        System.out.println();
        System.out.println("The game starts.");

        numberOfPlayers = enterNumOfPlayers(); //вводим количество игроков
        System.out.println("OK, there are " + numberOfPlayers + " players.");
        playersQueue = createQueue(numberOfPlayers);

    }

    private int enterNumOfPlayers() {
        System.out.print("Enter number of players (2-4): ");
        int num = scanner.nextInt();
        if (num < 2 || num > 4) {
            System.out.println("You entered a wrong number! Please enter a number from 2 to 4.");
            enterNumOfPlayers();
        }
        return num;
    }

    private Queue<Player> createQueue(int numberOfPlayers){
        for (int i = 1; i <= numberOfPlayers; i++){
            String id = Integer.toString(i);
            String name = "Player " + id;
            Player player = new Player(name, dealCards());

            playersQueue.add(player);
        }
        return playersQueue;
    }

    private ArrayList<Card> dealCards() {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < 6; i++){
            cards.add(gameDeck.getCardFromDeck());
        }
        return cards;
    }

    private void createGameDeck(){
        gameDeck = new Deck();
    }


   /* private Player startGamePLayer() {
        return
    }*/


}

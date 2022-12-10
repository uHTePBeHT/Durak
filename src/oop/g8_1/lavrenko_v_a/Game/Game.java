package oop.g8_1.lavrenko_v_a.Game;

import oop.g8_1.lavrenko_v_a.CircularLinkedList.CircularLinkedListForGame;
import oop.g8_1.lavrenko_v_a.CircularLinkedList.Node;
import oop.g8_1.lavrenko_v_a.Deck.Card;
import oop.g8_1.lavrenko_v_a.Deck.Deck;
import oop.g8_1.lavrenko_v_a.Deck.Suit;
import oop.g8_1.lavrenko_v_a.Player.Player;
import java.util.*;

public class Game {
    private Card trump; // козырь
    private Deck gameDeck; // игровая колода
    public CircularLinkedListForGame playersCircle = new CircularLinkedListForGame(null, null); // игровой круг

    public void startGame() {
        createGameDeck();
        gameStarts();

        playersCircle = createQueue(generateNumOfPlayers());
        trump = createTrump();
        printStartHands();
        allSortCards(playersCircle);

        System.out.println("\nTrump card is: " + getTrump().getRank().getValue() + getTrumpSuit().getValue());
        printStartHands();
        turn(playersCircle.getHead(), playersCircle.getNextAfterHead());
    }

    /*vvvvvvvvvvvvvvv Game Preparation vvvvvvvvvvvvvv*/
    private int generateNumOfPlayers() {
        int numberOfPlayers = (int) (2 + Math.random() * 5);
        System.out.println("OK, there are " + numberOfPlayers + " players.");
        return numberOfPlayers;
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
        if (gameDeck.getDeckSize() == 0) {
            trump = playersCircle.getTail().getPlayer().getHand().get(5);
        } else {
            trump = gameDeck.getCardFromDeck();
        }
        return trump;
    }

    private void printHand(Node curNode, List<Card> hand) {
        System.out.print(curNode.getPlayer().getPlayerName() + " [");
        for (int i = 0; i < hand.size() - 1; i++) {
            System.out.print(hand.get(i).getRank().getValue() + hand.get(i).getSuit().getValue() + " - ");
        }
        if (!hand.isEmpty()) {
            System.out.print(hand.get(hand.size() - 1).getRank().getValue() + hand.get(hand.size() - 1).getSuit().getValue() + "]");
        }
    }

    private void printStartHands() {
        Node curNode = playersCircle.getHead();
        do {
            System.out.print(curNode.getPlayer().getPlayerName() + " : ");
            for (int i = 0; i < curNode.getPlayer().getHand().size(); i++) {
                System.out.print(curNode.getPlayer().getHand().get(i).getRank().getValue() + curNode.getPlayer().getHand().get(i).getSuit().getValue() + " ");
            }
            System.out.println();
            curNode = playersCircle.getNextNode(curNode);
        } while (curNode != playersCircle.getHead());
    }

    private void printTable(List<Card> needToBeat, List<Card> beatenCards) {
        System.out.print("Table cards: ");
        for (int i = 0; i < needToBeat.size() - 1; i++) {
            System.out.print(needToBeat.get(i).getRank().getValue() + needToBeat.get(i).getSuit().getValue() + ", ");
        }
        if (needToBeat.size() > 0) {
            System.out.print(needToBeat.get(needToBeat.size() - 1).getRank().getValue() + needToBeat.get(needToBeat.size() - 1).getSuit().getValue() + ";\n");
        } else {
            System.out.println();
        }

        System.out.print("Beaten cards: ");
        for (int i = 0; i < beatenCards.size() - 1; i++) {
            System.out.print(beatenCards.get(i).getRank().getValue() + beatenCards.get(i).getSuit().getValue() + ", ");
        }
        if (!beatenCards.isEmpty()) {
            System.out.print(beatenCards.get(beatenCards.size() - 1).getRank().getValue() + beatenCards.get(beatenCards.size() - 1).getSuit().getValue() + ";\n");
        }
    }

    public Card getTrump() {
        return trump;
    }

    public Suit getTrumpSuit() {
        return trump.getSuit();
    }

    private void gameStarts() {
        for (int i = 0; i < 100; i++) {
            System.out.print("-");
        }
        System.out.println();
        System.out.println("The game starts.");
    }


    /*___________________ Game methods ___________________*/
    /*private String theFirstTurn() {
        Node attacker = playersCircle.getHead();
        Node defender = playersCircle.getNextAfterHead();


        String loserName = turn(attacker, defender);

        return loserName;
    }*/

    private void turn(Node attacker, Node defender) {
        System.out.println("\nNew turn.\n");
        //если голова списка не совпадает со следующим игроком
        if (!playersCircle.getNextNode(playersCircle.getHead()).equals(playersCircle.getHead())) {
            List<Card> needToBeat = new ArrayList<>(); // непобитые карты
            List<Card> beatenCards = new ArrayList<>(); // побитые карты
            Set<Integer> tableCardsRanks = new HashSet<>(); // значения карт на столе
            List<Card> attackerHand = attacker.getPlayer().getHand(); // карты в руке attacker
            List<Card> defenderHand = defender.getPlayer().getHand(); // карты в руке defender


            System.out.print("Attacker ");
            printHand(attacker, attackerHand);
            System.out.println();
            System.out.print("Defender ");
            printHand(defender, defenderHand);
            System.out.println("\n");

            //начало хода - атакующий кидает карту
            Card currentCard = attacker.getPlayer().throwCardToAttack(getTrump());
            needToBeat.add(currentCard);
            System.out.println(attacker.getPlayer().getPlayerName() + " plays: " + currentCard.getRank().getValue() + currentCard.getSuit().getValue() + "\n");
            printTable(needToBeat, beatenCards);
            tableCardsRanks.add(currentCard.getRank().ordinal());

            if (leftTheGame(attacker)) { //если атакующий вышел из игры
                System.out.println(attacker.getPlayer().getPlayerName() + " left the game.");
                playersCircle.deleteNode(attacker.getPlayer());
            }
            if (playersCircle.getSize() > 1) { //если есть ещё игроки
                    transfer(attacker, defender, attackerHand, defenderHand, tableCardsRanks, needToBeat, beatenCards);
            }
            printLoser();
        } else {
            printLoser();
        }
    }

    private void printLoser(){
        System.out.println(playersCircle.getHead().getPlayer().getPlayerName());
    }

    //выходит из игры: в руке не осталось карт и нет карт в колоде
    private boolean leftTheGame(Node currentNode) {
        return currentNode.getPlayer().getHand().isEmpty() && gameDeck.getDeckSize() == 0;
    }

    //есть карта, чтобы перевести
    private boolean hasCardToTransfer(List<Card> defenderHand, List<Card> needToBeat, Node defender) {
        boolean bool = false;
        for (Card card : defenderHand) {
            if (card.getRank().equals(needToBeat.get(0).getRank()) && playersCircle.getNextNode(defender).getPlayer().getHand().size() > needToBeat.size()) {
                bool = true;
                break;
            }
        }
        return bool;
    }

    //логика перевода
    private void transfer(Node attacker, Node defender, List<Card> attackerHand, List<Card> defenderHand, Set<Integer> tableCardsRanks, List<Card> needToBeat, List<Card> beatenCards) {
        if (!(hasCardToTransfer(defenderHand, needToBeat, defender) && playersCircle.getNextNode(defender).getPlayer().getHand().size() > needToBeat.size())) {
            //defender отбивается
            defend(attacker, defender, attackerHand, defenderHand, tableCardsRanks, needToBeat, beatenCards);
        }
        // Если у defender есть карта перевести (т.е. такого же значения, как и на столе)
        for (int i = 0; i < defenderHand.size(); i++) {
            //ищем карту для перевода
            if (defenderHand.get(i).getRank().equals(needToBeat.get(0).getRank())) {

                Card currentCard = defender.getPlayer().throwCardToTransfer(needToBeat.get(0), getTrumpSuit());
                needToBeat.add(currentCard); //добавляем карту на стол

                defender.getPlayer().getHand().remove(currentCard);
                defenderHand = defender.getPlayer().getHand();

                System.out.println("\n" + defender.getPlayer().getPlayerName() + " transfer: " + currentCard.getRank().getValue() + currentCard.getSuit().getValue());
                printHand(defender, defenderHand);
                tableCardsRanks.add(currentCard.getRank().ordinal()); //ранг брошенной на стол карты добавляется в сет
                System.out.println("\n");

                printTable(needToBeat, beatenCards);

                attacker = defender; //теперь защищающийся становится атакующим
                attackerHand = attacker.getPlayer().getHand();
                System.out.print("\nAttacker ");
                printHand(attacker, attackerHand);

                if (leftTheGame(defender)) {
                    playersCircle.deleteNode(defender.getPlayer());
                }

                if (playersCircle.getSize() > 1) {
                    defender = playersCircle.getNextNode(defender); //защищающимся становится следующий по кругу
                    System.out.print("\n" + "Defender ");
                    defenderHand = defender.getPlayer().getHand();
                    printHand(defender, defenderHand);

                    transfer(attacker, defender, attackerHand, defenderHand, tableCardsRanks, needToBeat, beatenCards);
                } else {
                    printLoser();
                    break;
                }
            }
        }
    }

    //ест карта, чтобы отбиться, но не козырь
    private boolean hasCardToDefendNoTrump(List<Card> defenderHand, List<Card> needToBeat) {
        boolean bool = false;
        for (Card card : defenderHand) {
            if (card.getSuit().equals(needToBeat.get(0).getSuit())
                    && card.getRank().ordinal() > needToBeat.get(0).getRank().ordinal()) {
                    bool = true;
                    break;
            }
        }
        return bool;
    }

    private void defend(Node attacker, Node defender, List<Card> attackerHand, List<Card> defenderHand, Set<Integer> tableCardsRanks, List<Card> needToBeat, List<Card> beatenCards) {
        if (!hasCardToDefendNoTrump(defenderHand, needToBeat)) {
            defByTrump(attacker, defender, attackerHand, defenderHand, tableCardsRanks, needToBeat, beatenCards);
        }
        for (int j = 0; j < defenderHand.size(); j++) {
            // если есть карта, чтобы побить (не козырь)
            if (defenderHand.get(j).getSuit().equals(needToBeat.get(0).getSuit())
                    && defenderHand.get(j).getRank().ordinal() > needToBeat.get(0).getRank().ordinal()) {
                Card currentCard = defender.getPlayer().throwCardToBeat(needToBeat.get(0));

                defender.getPlayer().getHand().remove(currentCard);
                defenderHand = defender.getPlayer().getHand();

                System.out.println("\n\n" + defender.getPlayer().getPlayerName() + " beats: " + currentCard.getRank().getValue() + currentCard.getSuit().getValue() + "\n");
                printHand(attacker, attackerHand);
                System.out.println();
                printHand(defender, defenderHand);
                System.out.println();

                if (leftTheGame(defender)) {
                    playersCircle.deleteNode(defender.getPlayer());
                }

                if (playersCircle.getSize() < 2) {
                    printLoser();
                }

                beatenCards.add(needToBeat.get(0));
                needToBeat.remove(0);
                beatenCards.add(currentCard);
                tableCardsRanks.add(currentCard.getRank().ordinal());

                printTable(needToBeat, beatenCards);

                if (!needToBeat.isEmpty()) {
                    defend(attacker, defender, attackerHand, defenderHand, tableCardsRanks, needToBeat, beatenCards);
                } else {
                    break;
                }
            }
        }


        if (leftTheGame(defender)) {
            playersCircle.deleteNode(defender.getPlayer());
        }

        if (playersCircle.getSize() < 2) {
            printLoser();
        }

        if (needToBeat.size() + beatenCards.size()/2 < 6 && !defender.getPlayer().getHand().isEmpty()) {
            if (attackerHasCardToToss(attackerHand, tableCardsRanks)) {
                attackerTossCard(attacker, defender, attackerHand, defenderHand, tableCardsRanks, needToBeat, beatenCards);
            } else {
                if (playersCircle.getSize() > 2) {
                    playersTossCards(attacker, defender, attackerHand, defenderHand, tableCardsRanks, needToBeat, beatenCards);
                }
            }
        } else {
            endOfTurn(defender);
        }
    }

    private void defByTrump(Node attacker, Node defender, List<Card> attackerHand, List<Card> defenderHand, Set<Integer> tableCardsRanks, List<Card> needToBeat, List<Card> beatenCards) {
        //если есть козырь, чтобы побить, то кидаем его
        if (defender.getPlayer().hasTrump(getTrumpSuit(), needToBeat.get(0))) {
            Card currentCard = defender.getPlayer().trumpCardToBeat(getTrumpSuit());
            System.out.println("Defender beats: " + currentCard.getRank().getValue() + currentCard.getSuit().getValue());

            defender.getPlayer().getHand().remove(currentCard);
            defenderHand = defender.getPlayer().getHand();

            beatenCards.add(needToBeat.get(0));
            needToBeat.remove(0);
            beatenCards.add(currentCard);
            printTable(needToBeat, beatenCards);
            tableCardsRanks.add(currentCard.getRank().ordinal());

            if (leftTheGame(defender)) {
                playersCircle.deleteNode(defender.getPlayer());
                endOfTurn(defender);
            }

            if (playersCircle.getSize() < 2) {
                printLoser();
            }

            if (!needToBeat.isEmpty()) {
                defend(attacker, defender, attackerHand, defenderHand, tableCardsRanks, needToBeat, beatenCards);
            }
            if (needToBeat.size() + beatenCards.size()/2 < 6 && !defenderHand.isEmpty()) {
                if (attackerHasCardToToss(attackerHand, tableCardsRanks)) {
                    attackerTossCard(attacker, defender, attackerHand, defenderHand, tableCardsRanks, needToBeat, beatenCards);
                } else {
                    if (playersCircle.getSize() > 2) {
                        playersTossCards(attacker, defender, attackerHand, defenderHand, tableCardsRanks, needToBeat, beatenCards);
                    }
                }
            } else {
                endOfTurn(defender);
            }
        } else {
            playerTakeAllCardsFromTable(attacker, defender, needToBeat, beatenCards, defenderHand);
        }
    }

    private boolean attackerHasCardToToss(List<Card> attackerHand, Set<Integer> tableCardRanks) {
        boolean bool = false;
        for (Card card : attackerHand) {
            if (tableCardRanks.contains(card.getRank().ordinal())) {
                bool = true;
                break;
            }
        }
        return bool;
    }

    //attacker подбрасывает карту после того, как defender побил карту ранее
    private void attackerTossCard(Node attacker, Node defender, List<Card> attackerHand, List<Card> defenderHand, Set<Integer> tableCardsRanks, List<Card> needToBeat, List<Card> beatenCards) {
        for (Card card : attackerHand) {
            if (tableCardsRanks.contains(card.getRank().ordinal())) {
                int tempRank = card.getRank().ordinal();

                Card tempCard = attackerThrowsUp(attackerHand, tempRank);

                if (tempCard != null) {
                    System.out.println("Attacker tosses: " + tempCard.getRank().getValue() + tempCard.getSuit().getValue());
                    needToBeat.add(tempCard);

                    attacker.getPlayer().getHand().remove(tempCard);
                    attackerHand = attacker.getPlayer().getHand();

                    tableCardsRanks.add(tempCard.getRank().ordinal());
                    break;
                }

            }
        } if (leftTheGame(attacker)) {
            playersCircle.deleteNode(attacker.getPlayer());
        }
        if (playersCircle.getSize() > 1) {
            defend(attacker, defender, attackerHand, defenderHand, tableCardsRanks, needToBeat, beatenCards);
        }
        printLoser();
    }

    //игроки подбрасывают карты
    private void playersTossCards(Node attacker, Node defender, List<Card> attackerHand, List<Card> defenderHand, Set<Integer> tableCardsRanks, List<Card> needToBeat, List<Card> beatenCards) {
        Node currentNode = playersCircle.getNextNode(defender); //игроки подбрасывают карты
        Card tempCard = null;

        while (currentNode != attacker) {
            tempCard = tossCard(currentNode, tableCardsRanks);

            if (tempCard != null) {
                System.out.println(currentNode.getPlayer().getPlayerName() + " tosses: " + tempCard.getRank().getValue() + tempCard.getSuit().getValue());
                currentNode.getPlayer().getHand().remove(tempCard);
                break;
            } else {
                currentNode = playersCircle.getNextNode(currentNode);
            }
        }
        printHand(currentNode, currentNode.getPlayer().getHand());

        needToBeat.add(tempCard);
        printTable(needToBeat, beatenCards);
        defend(attacker, defender, attackerHand, defenderHand, tableCardsRanks, needToBeat, beatenCards);
        /* подбрасывают другие игроки */
        /* если отбился, то конец хода
         * если не отбился, то набирает карты */
    }

    // игрок забирает все карты со стола после того, как не побился, и начинается новый ход
    private void playerTakeAllCardsFromTable(Node attacker, Node defender, List<Card> needToBeat, List<Card> beatenCards, List<Card> defenderHand) {
        System.out.println(defender.getPlayer().getPlayerName() + " take all cards from table.");
        defenderHand.addAll(needToBeat); //берёт все небитые карты
        defenderHand.addAll(beatenCards); //берёт все битые карты
        sortCardsInHand(defender);

        printStartHands();

        for (int k = 0; k < 2; k++) { //переопределение ролей, атакующим становится следующий после взявшего карты.
            attacker = playersCircle.getNextNode(attacker);
            defender = playersCircle.getNextNode(defender);
        }
        allPlayersTakeCards(); //все игроки набирают карты
        allSortCards(playersCircle);
        turn(attacker, defender); //новый ход
    }

    //конец хода, когда defender полностью отбился
    private void endOfTurn(Node defender) {
        if (gameDeck.getDeckSize() != 0) {
            if (!defender.getPlayer().getHand().isEmpty()) {
                Node attacker = defender;
                defender = playersCircle.getNextNode(attacker);

                allPlayersTakeCards(); //все игроки набирают карты
                turn(attacker, defender); //новый ход
            } else {
                Node attacker = playersCircle.getNextNode(defender);
                defender = playersCircle.getNextNode(attacker);

                allPlayersTakeCards(); //все игроки набирают карты
                turn(attacker, defender); //новый ход
            }
        }
    }


    //логика обхода списка игроков и добора ими нужного количества карт
    private void allPlayersTakeCards() {
        Node currentNode = playersCircle.getHead();
        if (playersCircle.getHead() != null) {
            do {
                takeCards(currentNode);
                currentNode = playersCircle.getNextNode(currentNode);
            } while (currentNode != playersCircle.getHead());
        }
        System.out.println("\n");
        printStartHands();
    }

    //логика взятия карт игроком себе в руку (берёт, если меньше 6 карт в руке и есть карты в колоде)
    private void takeCards(Node currentNode) {
        if (currentNode.getPlayer().getHand().size() < 6) {
            while (currentNode.getPlayer().getHand().size() < 6 && gameDeck.getDeckSize() > 0) {
                Card tempCard = gameDeck.getGameDeck().pop();
                currentNode.getPlayer().getHand().add(tempCard);
            }
            sortCardsInHand(currentNode);
        }
    }


    //сортировка карт в руке игрока по возрастанию Rank
    private void sortCardsInHand(Node currentNode) {
        if (currentNode.getPlayer().getHand().size() > 1) {
            currentNode.getPlayer().getHand().sort(new Comparator<>() {
                @Override
                public int compare(Card o1, Card o2) {
                    return o1.getRank().compareTo(o2.getRank());
                }
            });
        }
    }

    //все игроки сортируют карты у себя в руке, когда берут новые карты
    private void allSortCards(CircularLinkedListForGame playersCircle) {
        Node currentNode = playersCircle.getHead();

        if (playersCircle.getHead() != null) {
            do {
                sortCardsInHand(currentNode);
                currentNode = playersCircle.getNextNode(currentNode);
            } while (currentNode != playersCircle.getHead());
        }
    }

    //логика подбрасывания карты атакующим игроком
    private Card attackerThrowsUp(List<Card> attackerHand, int tempRank) {
        int index = 0;
        Card tempCard = null;

        while (attackerHand.get(index).getRank().ordinal() <= tempRank) {
            if (attackerHand.get(index).getRank().ordinal() == tempRank) {
                if (tempCard == null) {
                    tempCard = attackerHand.get(index);
                    if (index == attackerHand.size() - 1) {
                        break;
                    }
                } else {
                    if (tempCard.getSuit().equals(getTrumpSuit())) {
                        tempCard = attackerHand.get(index);
                    }
                    break;
                }
            }
            index++;
        }
        return tempCard;
    }

    private Card tossCard(Node currentNode, Set<Integer> tableCardsRanks) {
        Card currentCard = null;
        for (int i = 0; i < currentNode.getPlayer().getHand().size(); i++) {
            if (tableCardsRanks.contains(currentNode.getPlayer().getHand().get(i).getRank().ordinal())) {
                currentCard = currentNode.getPlayer().getHand().get(i);
                break;
            }
        }
        return currentCard;
    }
}



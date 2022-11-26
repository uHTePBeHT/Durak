package oop.g8_1.lavrenko_v_a.Game;

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

    public void startGame() {
        createGameDeck();
        gameStarts();

        playersCircle = createQueue(generateNumOfPlayers());
        trump = createTrump();
        allSortCards(playersCircle);


    }

    /*vvvvvvvvvvvvvvv Game Preparation vvvvvvvvvvvvvv*/
    private int generateNumOfPlayers() {
        numberOfPlayers = (int) (2 + Math.random() * 5);
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
        trump = gameDeck.getCardFromDeck();
        return trump;
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
    private String theFirstTurn() {
        Node attacker = playersCircle.getHead();
        Node defender = playersCircle.getNextAfterHead();


        String loserName = turn(attacker, defender);

        return loserName;
    }

    private String turn(Node attacker, Node defender) {
        while (numberOfPlayers > 1) {
            List<Card> needToBeat = new ArrayList<>();
            List<Card> beatenCards = new ArrayList<>();
            Set<Rank> tableCardsRanks = new HashSet<>();

            int tableSize = needToBeat.size() + beatenCards.size()/2;
            if (tableSize < 6) {
                Card currentCard = attacker.getPlayer().throwCardToAttack(getTrump());
                needToBeat.add(currentCard);
                tableCardsRanks.add(currentCard.getRank());
            }

            List<Card> attackerHand = attacker.getPlayer().getHand();
            List<Card> defenderHand = defender.getPlayer().getHand();

            for (int i = 0; i < defenderHand.size(); i++) {
                if (defenderHand.get(i).getRank().equals(needToBeat.get(0).getRank())) { //если у Защ.Игрока есть карта перевести (т.е. такого же значения, как и на столе)
                    Card currentCard = defender.getPlayer().throwCardToTransfer(needToBeat.get(0), trump);
                    needToBeat.add(currentCard); //добавляем карту на стол
                    tableCardsRanks.add(currentCard.getRank()); //ранг брошенной на стол карты добавляется в сет

                    attacker = defender; //теперь защищающийся становится атакующим
                    defender = playersCircle.transitionNode(defender); //защищающимся становится следующий по кругу
                    defenderHand = defender.getPlayer().getHand();

                } else {

                    for (int j = 0; j < defenderHand.size(); j++) {

                        if (defenderHand.get(j).getSuit().equals(needToBeat.get(0).getSuit())
                                && defenderHand.get(j).getRank().ordinal() > needToBeat.get(0).getRank().ordinal()) { // если есть карта, чтобы побить (не козырь)
                            Card currentCard = defender.getPlayer().throwCardToBeat(needToBeat.get(0), getTrumpSuit());
                            beatenCards.add(needToBeat.get(0));
                            needToBeat.remove(0);
                            beatenCards.add(currentCard);
                            tableCardsRanks.add(currentCard.getRank());

                            for (Card card : attackerHand) {                                                   //если атакующий может подкинуть карту
                                if (tableCardsRanks.contains(card.getRank())) {
                                    int tempRank = card.getRank().ordinal();
                                    Card tempCard = attackerThrowsUp(attackerHand, tempRank);
                                    needToBeat.add(tempCard);
                                    tableCardsRanks.add(tempCard.getRank());

                                    /* рекурсия defender отбивается */

                                } else {
                                    /* подбрасывают другие игроки */
                                    /* если отбился, то конец хода
                                     * если не отбился, то набирает карты */

                                }
                            }


                        } else {                                                                            //если есть козырь, чтобы побить, то кидаем его

                            if (defender.getPlayer().hasTrump(getTrumpSuit())) {
                                Card currentCard = defender.getPlayer().trumpCardToBeat(getTrumpSuit());

                                beatenCards.add(needToBeat.get(0));
                                needToBeat.remove(0);
                                beatenCards.add(currentCard);
                                tableCardsRanks.add(currentCard.getRank());
                            } else {                                                                        //если нет козыря, то набирает карты.
                                defenderHand.addAll(needToBeat); //берёт все небитые карты
                                defenderHand.addAll(beatenCards); //берёт все битые карты
                                sortCardsInHand(defender); //сортирует взятые карты в руке

                                for (int k = 0; k < 2; k++) { //переопределение ролей, атакующим становится следующий после взявшего карты.
                                    attacker = playersCircle.transitionNode(attacker);
                                    defender = playersCircle.transitionNode(defender);
                                }
                                allPlayersTakeCards(); //все игроки набирают карты
                                turn(attacker, defender); //новый ход
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private void allPlayersTakeCards() { //логика обхода списка игроков и добора ими нужного количества карт
        Node currentNode = playersCircle.getHead();
        if (playersCircle.getHead() != null) {
            do {
                takeCards(currentNode);
                currentNode = playersCircle.transitionNode(currentNode);
            } while (currentNode != playersCircle.getHead());
        }
    }

    private void takeCards(Node currentNode) { //логика взятия карт игроком себе в руку (берёт, если меньше 6 карт в руке и есть карты в колоде)
        if (currentNode.getPlayer().getHand().size() < 6) {
            while (currentNode.getPlayer().getHand().size() < 6 && gameDeck.getDeckSize() > 0) {
                Card tempCard = gameDeck.getGameDeck().pop();
                currentNode.getPlayer().getHand().add(tempCard);
            }
            sortCardsInHand(currentNode);
        }
    }


    private void sortCardsInHand(Node currentNode) { //сортировка карт в руке игрока по возрастанию Rank
        if (currentNode.getPlayer().getHand().size() > 1) {
            currentNode.getPlayer().getHand().sort(new Comparator<Card>() {
                @Override
                public int compare(Card o1, Card o2) {
                    return o1.getRank().compareTo(o2.getRank());
                }
            });
        }
    }

    private void allSortCards(CircularLinkedListForGame playersCircle) { //все игроки сортируют карты у себя в руке, когда берут новые карты
        Node currentNode = playersCircle.getHead();

        if (playersCircle.getHead() != null) {
            do {
                sortCardsInHand(currentNode);
                currentNode = playersCircle.transitionNode(currentNode);
            } while (currentNode != playersCircle.getHead());
        }
    }

    private Card attackerThrowsUp(List<Card> attackerHand, int tempRank) { //логика подбрасывания карты атакующим игроком
        int index = 0;

        while (index < tempRank) { //доходим до карты нужного значения
            index++;
        }
        Card tempCard = attackerHand.get(index);
        if (tempCard.getSuit().equals(getTrumpSuit())
                && attackerHand.get(index + 1) != null
                && attackerHand.get(index + 1).getRank().ordinal() == tempRank) {//если есть ещё карта нужного значения, то кидаем её
            index++;
            tempCard = attackerHand.get(index);
        }
        return tempCard;
    }

}



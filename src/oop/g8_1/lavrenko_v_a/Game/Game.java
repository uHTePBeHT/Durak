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

        String loserName = turn(playersCircle.getHead(), playersCircle.getNextAfterHead());
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
    /*private String theFirstTurn() {
        Node attacker = playersCircle.getHead();
        Node defender = playersCircle.getNextAfterHead();


        String loserName = turn(attacker, defender);

        return loserName;
    }*/

    private String turn(Node attacker, Node defender) {
        String loser = null;
        if (numberOfPlayers > 1) {
            List<Card> needToBeat = new ArrayList<>(); // непобитые карты
            List<Card> beatenCards = new ArrayList<>(); // побитые карты
            Set<Rank> tableCardsRanks = new HashSet<>(); // значения карт на столе
            List<Card> attackerHand = attacker.getPlayer().getHand(); // карты в руке attacker
            List<Card> defenderHand = defender.getPlayer().getHand(); // карты в руке defender
            int tableSize = 0; // количество карт на столе (<= 6)

            //начало хода - атакующий кидает карту
            Card currentCard = attacker.getPlayer().throwCardToAttack(getTrump());
            needToBeat.add(currentCard);
            tableCardsRanks.add(currentCard.getRank());

            transfer(attacker, defender, attackerHand, defenderHand, tableCardsRanks, needToBeat, beatenCards);

        } else {
            loser = playersCircle.getHead().getPlayer().getPlayerName();
        }
        return loser;
    }



    private void transfer(Node attacker, Node defender, List<Card> attackerHand, List<Card> defenderHand, Set<Rank> tableCardsRanks, List<Card> needToBeat, List<Card> beatenCards) {
        for (int i = 0; i < defenderHand.size(); i++) {
            //если у defender есть карта перевести (т.е. такого же значения, как и на столе)
            if (defenderHand.get(i).getRank().equals(needToBeat.get(0).getRank())) {
                Card currentCard = defender.getPlayer().throwCardToTransfer(needToBeat.get(0), trump);
                needToBeat.add(currentCard); //добавляем карту на стол
                tableCardsRanks.add(currentCard.getRank()); //ранг брошенной на стол карты добавляется в сет

                attacker = defender; //теперь защищающийся становится атакующим
                defender = playersCircle.transitionNode(defender); //защищающимся становится следующий по кругу
                attackerHand = attacker.getPlayer().getHand();
                defenderHand = defender.getPlayer().getHand();

                transfer(attacker, defender, attackerHand, defenderHand, tableCardsRanks, needToBeat, beatenCards);
                /*рекурсия проверки на возможность перевода*/

            } else {
                //defender отбивается
                defend(attacker, defender, attackerHand, defenderHand, tableCardsRanks, needToBeat, beatenCards);
            }
        }
    }

    private void defend(Node attacker, Node defender, List<Card> attackerHand, List<Card> defenderHand, Set<Rank> tableCardsRanks, List<Card> needToBeat, List<Card> beatenCards) {
        for (int j = 0; j < defenderHand.size(); j++) {
            // если есть карта, чтобы побить (не козырь)
            if (defenderHand.get(j).getSuit().equals(needToBeat.get(0).getSuit())
                    && defenderHand.get(j).getRank().ordinal() > needToBeat.get(0).getRank().ordinal()) {
                Card currentCard = defender.getPlayer().throwCardToBeat(needToBeat.get(0), getTrumpSuit());
                beatenCards.add(needToBeat.get(0));
                needToBeat.remove(0);
                beatenCards.add(currentCard);
                tableCardsRanks.add(currentCard.getRank());

                attackerTossCard(attacker, defender, attackerHand, defenderHand, tableCardsRanks, needToBeat, beatenCards);

            } else {
                defByTrump(attacker, defender, attackerHand, defenderHand, tableCardsRanks, needToBeat, beatenCards);
                //если есть козырь, чтобы побить, то кидаем его
            }
        }
    }

    private void defByTrump(Node attacker, Node defender, List<Card> attackerHand, List<Card> defenderHand, Set<Rank> tableCardsRanks, List<Card> needToBeat, List<Card> beatenCards) {
        //если есть козырь, чтобы побить, то кидаем его
        if (defender.getPlayer().hasTrump(getTrumpSuit())) {
            Card currentCard = defender.getPlayer().trumpCardToBeat(getTrumpSuit());

            beatenCards.add(needToBeat.get(0));
            needToBeat.remove(0);
            beatenCards.add(currentCard);
            tableCardsRanks.add(currentCard.getRank());

            attackerTossCard(attacker, defender, attackerHand, defenderHand, tableCardsRanks, needToBeat, beatenCards);
        } else {
            playerTakeAllCardsFromTable(attacker, defender, needToBeat, beatenCards, defenderHand);
                                /*//если нет козыря, то набирает карты.
                                defenderHand.addAll(needToBeat); //берёт все небитые карты
                                defenderHand.addAll(beatenCards); //берёт все битые карты
                                sortCardsInHand(defender); //сортирует взятые карты в руке

                                for (int k = 0; k < 2; k++) { //переопределение ролей, атакующим становится следующий после взявшего карты.
                                    attacker = playersCircle.transitionNode(attacker);
                                    defender = playersCircle.transitionNode(defender);
                                }
                                allPlayersTakeCards(); //все игроки набирают карты
                                turn(attacker, defender); //новый ход*/
        }

    }


    //attacker подбрасывает карту после того, как defender побил карту ранее
    private void attackerTossCard(Node attacker, Node defender, List<Card> attackerHand, List<Card> defenderHand, Set<Rank> tableCardsRanks, List<Card> needToBeat, List<Card> beatenCards) {
        for (Card card : attackerHand) {
            if (tableCardsRanks.contains(card.getRank())) {
                int tempRank = card.getRank().ordinal();
                Card tempCard = attackerThrowsUp(attackerHand, tempRank);
                needToBeat.add(tempCard);
                tableCardsRanks.add(tempCard.getRank());

                defend(attacker, defender, attackerHand, defenderHand, tableCardsRanks, needToBeat, beatenCards);

            } else {
                playersTossCards(attacker, defender, needToBeat, beatenCards, tableCardsRanks, defenderHand);
            }
        }
    }

    //игроки подбрасывают карты
    private void playersTossCards(Node attacker, Node defender, List<Card> needToBeat, List<Card> beatenCards, Set<Rank> tableCardsRanks, List<Card> defenderHand) {
        Node currentNode = playersCircle.transitionNode(defender); //игроки подбрасывают карты
        needToBeat.add(tossCard(currentNode, tableCardsRanks));
        defend();
        /* подбрасывают другие игроки */
        /* если отбился, то конец хода
         * если не отбился, то набирает карты */
    }

    // игрок набирает все карты со стола после того, как не побился, и начинается новый ход
    private void playerTakeAllCardsFromTable(Node attacker, Node defender, List<Card> needToBeat, List<Card> beatenCards, List<Card> defenderHand) {
        //если нет козыря, то набирает карты.
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


    //логика обхода списка игроков и добора ими нужного количества карт
    private void allPlayersTakeCards() {
        Node currentNode = playersCircle.getHead();
        if (playersCircle.getHead() != null) {
            do {
                takeCards(currentNode);
                currentNode = playersCircle.transitionNode(currentNode);
            } while (currentNode != playersCircle.getHead());
        }
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
            currentNode.getPlayer().getHand().sort(new Comparator<Card>() {
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
                currentNode = playersCircle.transitionNode(currentNode);
            } while (currentNode != playersCircle.getHead());
        }
    }

    //логика подбрасывания карты атакующим игроком
    private Card attackerThrowsUp(List<Card> attackerHand, int tempRank) {
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

    private Card tossCard(Node currentNode, Set<Rank> tableCardsRanks) {
        Card currentCard = null;
        for (int i = 0; i < currentNode.getPlayer().getHand().size(); i++) {
            if (tableCardsRanks.contains(currentNode.getPlayer().getHand().get(i).getRank())) {
                currentCard = currentNode.getPlayer().getHand().get(i);
                break;
            } else {
                currentNode = playersCircle.transitionNode(currentNode);
                tossCard(currentNode, tableCardsRanks);
            }
        }
        return currentCard;
    }
}



package oop.g8_1.lavrenko_v_a.CircularLinkedList;

import oop.g8_1.lavrenko_v_a.Deck.Card;
import oop.g8_1.lavrenko_v_a.Player.Player;

import java.lang.reflect.Array;

public class CircularLinkedListForGame {
    private Node head;
    private Node tail;

    public CircularLinkedListForGame(Node head, Node tail) {
        this.head = head;
        this.tail = tail;
    }

    public void addNode(Player player) {
        Node newNode = new Node(player);

        if (head == null) {
            head = newNode;
        } else {
            tail.nextNode = newNode;
        }

        tail = newNode;
        tail.nextNode = head;
    }

    public boolean containsNode(Player searchPlayer) {
        Node currentNode = head;

        if (head == null) {
            return false;
        } else {
            do {
                if (currentNode.player == searchPlayer) {
                    return true;
                }
                currentNode = currentNode.nextNode;
            } while (currentNode != head);
            return false;
        }
    }

    public void deleteNode(Player playerToDelete) {
        Node currentNode = head;
        if (head == null) { // the list is empty
            return;
        }
        do {
            Node nextNode = currentNode.nextNode;
            if (nextNode.player == playerToDelete) {
                if (tail == head) { // the list has only one single element
                    head = null;
                    tail = null;
                } else {
                    currentNode.nextNode = nextNode.nextNode;
                    if (head == nextNode) { //we're deleting the head
                        head = head.nextNode;
                    }
                    if (tail == nextNode) { //we're deleting the tail
                        tail = currentNode;
                    }
                }
                break;
            }
            currentNode = nextNode;
        } while (currentNode != head);
    }

    /*public void traverseList() {
        Node currentNode = head;

        if (head != null) {
            do {
                logger.info(currentNode.value + " ");
                currentNode = currentNode.nextNode;
            } while (currentNode != head);
        }
    }*/

    public Player[] traverseListFirstPlayer(Card trump) {
        Node currentNode = head;
        Card minTrump = null;
        Player[] players = new Player[] {head.player, head.nextNode.player};

            do {
                for (int i = 0; i < currentNode.player.getHand().size(); i++){

                    if (currentNode.player.getHand().get(i).getSuit().equals(trump.getSuit())) {

                        if (minTrump != null){

                            if (currentNode.player.getHand().get(i).getRank().ordinal() < minTrump.getRank().ordinal()) {
                                players[0] = currentNode.player;
                                players[1] = currentNode.nextNode.player;
                                minTrump = currentNode.player.getHand().get(i);
                            }
                        }
                        else minTrump = currentNode.player.getHand().get(i);
                    }
                }
                currentNode = currentNode.nextNode;
            } while (currentNode != head);
        return players;
    }
}


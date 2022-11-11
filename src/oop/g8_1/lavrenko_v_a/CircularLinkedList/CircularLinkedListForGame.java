package oop.g8_1.lavrenko_v_a.CircularLinkedList;

import oop.g8_1.lavrenko_v_a.Deck.Card;
import oop.g8_1.lavrenko_v_a.Player.Player;

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

    public Player traverseListFirstPlayer(Card trump) {
        Node currentNode = head;
        Player attacker;
        Card minTrump;

        if (head != null) {
            do {
                if (currentNode.player.getHand().)

                currentNode = currentNode.nextNode;
            } while (currentNode != head);
        }
    }
}


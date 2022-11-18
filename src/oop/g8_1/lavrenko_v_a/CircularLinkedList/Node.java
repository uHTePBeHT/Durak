package oop.g8_1.lavrenko_v_a.CircularLinkedList;

import oop.g8_1.lavrenko_v_a.Player.Player;

public class Node{
    Player player;
    Node nextNode;

    public Node(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}

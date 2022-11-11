package oop.g8_1.lavrenko_v_a.Deck;

public enum Suit {
    SPADES("\u2660"),
    HEARTS("\u2665"),
    CLUBS("\u2663"),
    DIAMONDS("\u2666");

    private final String value;

    Suit(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}

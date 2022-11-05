package oop.g8_1.lavrenko_v_a;

public enum Rank {
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("J"),
    QUEEN("Q"),
    KING("K"),
    ACE("A");

    private final String value;

    Rank(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}


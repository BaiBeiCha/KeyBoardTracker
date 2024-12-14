package org.baibei.keyboardtrackermain.pojo.keys;

public class Key {

    private final char character;
    private long amount;

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public char getCharacter() {
        return character;
    }

    public Key(char character) {
        this.character = character;
        amount = 0;
    }

    public Key add() {
        amount++;
        return this;
    }

    public Key add(long amount) {
        this.amount += amount;
        return this;
    }

    public void subtract() {
        amount--;
    }

    public void subtract(long amount) {
        this.amount -= amount;
    }

    public String toString() {
        return String.valueOf(character) + "=" + amount;
    }
}

package org.baibei.keyboardtrackerdesktop.pojo.words;

public class Word {
    private String word;
    private long amount;

    public Word(String word) {
        this.word = word.toLowerCase();
        amount = 0;
    }

    public void setWord(String word) {
        this.word = word.toLowerCase();
    }

    public boolean equals(String word) {
        return this.word.equals(word.toLowerCase());
    }

    public boolean equals(Word word) {
        return this.word.equals(word.getWord());
    }

    public String getWord() {
        return word;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Word add() {
        amount++;
        return this;
    }

    public Word add(long amount) {
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
        return word + "=" + amount;
    }
}

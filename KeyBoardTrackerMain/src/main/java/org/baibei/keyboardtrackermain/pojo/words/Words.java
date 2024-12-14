package org.baibei.keyboardtrackermain.pojo.words;

import java.util.ArrayList;

public class Words {

    private ArrayList<Word> words = new ArrayList<>();

    public Words() {}

    public Words(ArrayList<Word> words) {
        this.words = words;
    }

    public Words(Words words) {
        this.words = words.getWords();
    }

    public boolean contains(String word) {
        for (Word value : words) {
            if (value.equals(word)) {
                return true;
            }
        }

        return false;
    }

    public boolean contains(Word word) {
        for (Word value : words) {
            if (value.equals(word.getWord())) {
                return true;
            }
        }

        return false;
    }

    public void add(Word word) {
        words.add(word);
    }

    public static Words make(ArrayList<Word> words) {
        return new Words(words);
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public void setWords(ArrayList<Word> words) {
        this.words = words;
    }

    public int posOf(Word word) {
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getWord().equals(word.getWord())) {
                return i;
            }
        }
        return -1;
    }

    public void set(int pos, Word word) {
        words.set(pos, word);
    }

    public Word get(int pos) {
        return words.get(pos);
    }

    public String toString() {
        return words.toString();
    }
}

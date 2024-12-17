package org.baibei.keyboardtrackerdesktop.pojo.words;

import java.util.ArrayList;

public class Words {

    private ArrayList<Word> words = new ArrayList<>();

    public Words() {}

    public Words(ArrayList<Word> words) {
        this.words = words;
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
        sort();
    }

    public void sort() {
        for (int i = 0; i < words.size(); i++) {
            for (int j = i + 1; j < words.size(); j++) {
                if (words.get(i).getWord().compareTo(words.get(j).getWord()) > 0) {
                    Word temp = words.get(i);
                    words.set(i, words.get(j));
                    words.set(j, temp);
                }
            }
        }
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
        StringBuilder sb = new StringBuilder();
        for (Word value : words) {
            sb.append(" ");
            sb.append(value);
            if (value != words.get(words.size() - 1)) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}

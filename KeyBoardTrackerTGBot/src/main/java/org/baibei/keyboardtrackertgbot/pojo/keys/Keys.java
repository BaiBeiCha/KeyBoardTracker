package org.baibei.keyboardtrackertgbot.pojo.keys;

import org.baibei.keyboardtrackertgbot.pojo.words.Word;

import java.util.ArrayList;

public class Keys {

    private ArrayList<Key> keys = new ArrayList<>();

    public Keys() {}

    public Keys(ArrayList<Key> keys) {
        this.keys = keys;
    }

    public boolean contains(Key key) {
        if (keys.isEmpty()) {
            return false;
        }

        for (Key value : keys) {
            if (key.getCharacter() == value.getCharacter()) {
                return true;
            }
        }

        return false;
    }

    public void add(Key key) {
        keys.add(key);
    }

    public static Keys make(ArrayList<Key> keys) {
        return new Keys(keys);
    }

    public ArrayList<Key> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<Key> keys) {
        this.keys = keys;
    }

    public int posOf(Key key) {
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).getCharacter() == key.getCharacter()) {
                return i;
            }
        }
        return -1;
    }

    public void set(int pos, Key key) {
        keys.set(pos, key);
    }

    public Key get(int pos) {
        return keys.get(pos);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Keys:\n");
        for (Key value : keys) {
            sb.append(value.toString());
            if (value != keys.get(keys.size() - 1)) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}

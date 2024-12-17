package org.baibei.keyboardtrackerdesktop.pojo.keys;

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
        sort();
    }

    public void sort() {
        for (int i = 0; i < keys.size(); i++) {
            for (int j = i + 1; j < keys.size(); j++) {
                if (keys.get(i).getCharacter() > keys.get(j).getCharacter()) {
                    Key key = keys.get(i);
                    keys.set(i, keys.get(j));
                    keys.set(j, key);
                }
            }
        }
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
        for (Key value : keys) {
            sb.append(" ");
            sb.append(value);
            if (value != keys.get(keys.size() - 1)) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}

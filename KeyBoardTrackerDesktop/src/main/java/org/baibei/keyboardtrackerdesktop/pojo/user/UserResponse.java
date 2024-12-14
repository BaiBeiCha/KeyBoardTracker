package org.baibei.keyboardtrackerdesktop.pojo.user;

import org.baibei.keyboardtrackerdesktop.pojo.words.Word;
import org.baibei.keyboardtrackerdesktop.pojo.keys.Key;

import java.util.ArrayList;

public class UserResponse {

    private String username;
    private String password;
    private ArrayList<Key> keys;
    private ArrayList<Word> words;

    public UserResponse() {
    }

    public UserResponse(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.keys = (ArrayList<Key>) user.getKeys();
        this.words = (ArrayList<Word>) user.getWords();
    }

    public UserResponse(String username, String password, ArrayList<Key> keys, ArrayList<Word> words) {
        this.username = username;
        this.password = password;
        this.keys = keys;
        this.words = words;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Key> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<Key> keys) {
        this.keys = keys;
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public void setWords(ArrayList<Word> words) {
        this.words = words;
    }

    public String toString() {
        return String.format(
                "User[username=%s, keys=%s, words=%s]",
                username, keys.toString(), words.toString());
    }
}

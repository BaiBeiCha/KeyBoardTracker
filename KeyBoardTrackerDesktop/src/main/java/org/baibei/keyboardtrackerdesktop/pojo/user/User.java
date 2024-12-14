package org.baibei.keyboardtrackerdesktop.pojo.user;

import org.baibei.keyboardtrackerdesktop.passwordencoder.PasswordEncoder;
import org.baibei.keyboardtrackerdesktop.pojo.words.Word;
import org.baibei.keyboardtrackerdesktop.pojo.words.Words;
import org.baibei.keyboardtrackerdesktop.pojo.keys.Key;
import org.baibei.keyboardtrackerdesktop.pojo.keys.Keys;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String id;
    private String username;
    private String password;
    private Keys keys = new Keys();
    private Words words = new Words();

    public User() {

    }

    public User(String username, String password, ArrayList<Word> words, ArrayList<Key> keys) {
        this.username = username;
        setPassword(password);
        this.words = Words.make(words);
        this.keys = Keys.make(keys);
    }

    public User(UserResponse userResponse) {
        this.username = userResponse.getUsername();
        this.password = userResponse.getPassword();
        this.keys = new Keys(userResponse.getKeys());
        this.words = new Words(userResponse.getWords());
    }

    public void addWord(Word word) {
        if (words.contains(word)) {
            int position = words.posOf(word);
            Word gword = words.get(position);
            gword.add(word.getAmount());
            words.set(position, word);
        } else {
            words.add(word);
        }
    }

    public void addKey(Key key) {
        if (key.getCharacter() == ' ') {
            return;
        }

        if (keys.contains(key)) {
            int position = keys.posOf(key);
            Key gkey = keys.get(position);
            gkey.add(key.getAmount());
            keys.set(position, gkey);
        } else {
            keys.add(key);
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public void setPasswordS(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setWords(Words words) {
        this.words = words;
    }

    public void setWords(ArrayList<Word> words) {
        this.words = Words.make(words);
    }

    public List<Word> getWords() {
        return words.getWords();
    }

    public void setKeys(Keys keys) {
        this.keys = keys;
    }

    public void setKeys(ArrayList<Key> keys) {
        this.keys = Keys.make(keys);
    }

    public List<Key> getKeys() {
        return keys.getKeys();
    }

    public String toString() {
        return String.format(
                "User[id=%s, username=%s, keys=%s, words=%s]",
                id, username, keys.toString(), words.toString());
    }
}

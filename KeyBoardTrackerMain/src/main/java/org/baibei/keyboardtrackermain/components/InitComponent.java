package org.baibei.keyboardtrackermain.components;

import org.baibei.keyboardtrackermain.pojo.keys.Key;
import org.baibei.keyboardtrackermain.pojo.keys.Keys;
import org.baibei.keyboardtrackermain.pojo.user.User;
import org.baibei.keyboardtrackermain.pojo.words.Word;
import org.baibei.keyboardtrackermain.pojo.words.Words;
import org.baibei.keyboardtrackermain.repositories.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitComponent implements CommandLineRunner {

    private final UsersRepository repository;

    public InitComponent(UsersRepository repository) {
        this.repository = repository;
    }

    private int random() {
        return (int) (Math.random() * 100);
    }

    public User makeUser() {
        User user = new User();

        StringBuilder username = new StringBuilder();
        for (int j = 0; j < random(); j++) {
            username.append((char) (random() % 26 + 'a'));
        }
        user.setUsername(username.toString());

        Keys keys = new Keys();
        for (int i = 0; i < random(); i++) {
            keys.add(new Key((char) (random() % 26 + 'a')).add(random()));
        }
        user.setKeys(keys);

        Words words = new Words();
        for (int i = 0; i < random(); i++) {
            StringBuilder word = new StringBuilder();
            for (int j = 0; j < random(); j++) {
                word.append((char) (random() % 26 + 'a'));
            }
            Word newWord = new Word(word.toString());
            words.add(newWord.add(random()));
        }
        user.setWords(words);

        return user;
    }

    @Override
    public void run(String... args) {
    }
}

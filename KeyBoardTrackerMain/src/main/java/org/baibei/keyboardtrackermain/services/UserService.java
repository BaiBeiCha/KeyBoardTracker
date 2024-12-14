package org.baibei.keyboardtrackermain.services;

import org.baibei.keyboardtrackermain.pojo.keys.Key;
import org.baibei.keyboardtrackermain.pojo.user.User;
import org.baibei.keyboardtrackermain.pojo.words.Word;
import org.baibei.keyboardtrackermain.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public List<String> findAllUsernames() {
        List<String> usernames = new ArrayList<>();
        for (User user : usersRepository.findAll()) {
            usernames.add(user.getUsername());
        }
        return usernames;
    }

    public void update(User user) {
        User newUser = usersRepository.findByUsername(user.getUsername());
        usersRepository.delete(newUser);
        newUser.setId(user.getId());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setKeys((ArrayList<Key>) user.getKeys());
        newUser.setWords((ArrayList<Word>) user.getWords());
        usersRepository.save(newUser);
    }
}

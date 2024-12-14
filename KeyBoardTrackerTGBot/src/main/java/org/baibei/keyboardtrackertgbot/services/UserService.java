package org.baibei.keyboardtrackertgbot.services;

import org.baibei.keyboardtrackertgbot.pojo.other.RequestMethod;
import org.baibei.keyboardtrackertgbot.pojo.user.User;
import org.baibei.keyboardtrackertgbot.pojo.user.UserResponse;
import org.baibei.keyboardtrackertgbot.pojo.user.UserSync;
import org.baibei.keyboardtrackertgbot.repositories.UserSyncRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final RequestService requestService;
    private final UserSyncRepository userSyncRepository;

    public UserService(RequestService requestService,
                       UserSyncRepository userSyncRepository) {
        this.requestService = requestService;
        this.userSyncRepository = userSyncRepository;
    }

    public User getUser(String tgId) {
        UserSync userInfo = userSyncRepository.findByTgId(tgId);
        if (userInfo == null) {
            userInfo = new UserSync();
        }

        User user = new User();
        user.setUsername(userInfo.getUsername());
        user.setPassword(userInfo.getPassword());

        User newUser = requestService.send("http://localhost:8080/api/users/" + user.getUsername(),
                RequestMethod.GET, new UserResponse(user));
        if (newUser == null) {
            return new User();
        }

        if (newUser.getPassword().equals(user.getPassword())) {
            return newUser;
        } else {
            return new User();
        }
    }

    public User getUserByUsername(String username) {
        User user = requestService.send("http://localhost:8080/api/users/" + username,
                RequestMethod.GET, new UserResponse());
        if (user == null) {
            return new User();
        }

        return user;
    }

    public User getUserFromRepository(String tgId) {
        UserSync userInfo = userSyncRepository.findByTgId(tgId);
        if (userInfo == null) {
            userInfo = new UserSync();
        }
        User user = new User();
        user.setUsername(userInfo.getUsername());
        user.setPassword(userInfo.getPassword());
        return user;
    }

    public ArrayList<User> getUsers() {
        return requestService.getAll();
    }

    public String getUserPassword(String username) {
        User user = getUserByUsername(username);
        return user.getPassword();
    }

    public String getUserUsername(String tgId) {
        User user = getUser(tgId);
        return user.getUsername();
    }

    public ArrayList<String> getAllUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        List<User> users = getUsers();
        for (User userSync : users) {
            usernames.add(userSync.getUsername());
        }

        return usernames;
    }

    public boolean existsUserByUsername(String username) {
        List<String> usernames = getAllUsernames();
        return usernames.contains(username);
    }

    public void saveUser(User user, String tgId) {
        userSyncRepository.save(new UserSync(user, tgId));
    }

    public void updateUser(User user, String tgId) {
        UserSync userInfo = userSyncRepository.findByTgId(tgId);
        if (userInfo == null) {
            return;
        }
        userSyncRepository.deleteByTgId(tgId);

        userInfo.setUsername(user.getUsername());
        userInfo.setPassword(user.getPassword());

        userSyncRepository.save(userInfo);
    }

    public boolean existsUser(String tgId) {
        UserSync userInfo = userSyncRepository.findByTgId(tgId);
        return userInfo != null;
    }

    public void deleteUser(String tgId) {
        UserSync userInfo = userSyncRepository.findByTgId(tgId);
        if (userInfo == null) {
            return;
        }
        userSyncRepository.delete(userInfo);
    }
}

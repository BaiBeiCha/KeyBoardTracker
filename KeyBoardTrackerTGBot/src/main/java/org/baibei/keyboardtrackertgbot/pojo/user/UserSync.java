package org.baibei.keyboardtrackertgbot.pojo.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("UsersSync")
public class UserSync {

    @Id
    private String id;

    @Field("username")
    private String username;

    @Field("password")
    private String password;

    @Field("tgId")
    private String tgId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTgId() {
        return tgId;
    }

    public void setTgId(String tgId) {
        this.tgId = tgId;
    }

    public UserSync(String username, String password, String tgId) {
        this.username = username;
        this.password = password;
        this.tgId = tgId;
    }

    public UserSync() {}

    public UserSync(User user, String tgId) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.tgId = tgId;
    }

    public UserSync(UserResponse userResponse, String tgId) {
        this.username = userResponse.getUsername();
        this.password = userResponse.getPassword();
        this.tgId = tgId;
    }
}

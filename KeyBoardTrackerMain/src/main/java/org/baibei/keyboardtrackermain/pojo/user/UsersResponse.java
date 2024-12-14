package org.baibei.keyboardtrackermain.pojo.user;

import java.util.ArrayList;

public class UsersResponse {

    private ArrayList<UserResponse> users;

    public UsersResponse() {}

    public UsersResponse(ArrayList<UserResponse> users) {
        this.users = users;
    }

    public ArrayList<UserResponse> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserResponse> users) {
        this.users = users;
    }
}

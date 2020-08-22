package com.behemoth.repeat.login;

public class User {

    private String userId;
    /**
     *  0 ->> social
     */
    private int type;

    public User(String id, int type){
        this.userId = id;
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

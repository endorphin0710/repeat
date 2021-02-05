package com.behemoth.repeat.model;

public class User {

    private String userId;
    /**
     *  0 ->> social
     */
    private int type;

    private String uid;

    public User(String id, int type, String uid){
        this.userId = id;
        this.type = type;
        this.uid = uid;
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

    public String getUid() { return uid; }

    public void setUid(String uid) { this.uid = uid; }
}

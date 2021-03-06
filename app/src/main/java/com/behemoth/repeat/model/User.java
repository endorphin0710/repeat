package com.behemoth.repeat.model;

import java.util.List;

public class User {

    private String userId;
    /**
     *  0 ->> social
     */
    private int type;

    private String uid;

    private String nickName;

    private List<Mark> recentMarks;

    public User(String id, int type, String uid, String nickName){
        this.userId = id;
        this.type = type;
        this.uid = uid;
        this.nickName = nickName;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() { return type; }
    public void setType(int type) {
        this.type = type;
    }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getNickName() { return nickName; }
    public void setNickName(String nickName) { this.nickName = nickName; }

    public List<Mark> getRecentMarks() { return recentMarks; }
    public void setRecentMarks(List<Mark> recentMarks) { this.recentMarks = recentMarks; }

}

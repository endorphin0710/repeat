package com.behemoth.repeat.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Mark implements Parcelable {

    private String id;
    private int chatper;
    private int repeat;
    private int score;
    private int problemCnt;
    private long createdDate;

    public Mark(){}

    public Mark(String id, int chapter, int repeat, int score, int problemCnt, long createdDate){
        this.id = id;
        this.chatper = chapter;
        this.repeat = repeat;
        this.score = score;
        this.problemCnt = problemCnt;
        this.createdDate = createdDate;
    }

    protected Mark(Parcel in) {
        id = in.readString();
        chatper = in.readInt();
        repeat = in.readInt();
        score = in.readInt();
        problemCnt = in.readInt();
        createdDate = in.readLong();
    }

    public static final Creator<Mark> CREATOR = new Creator<Mark>() {
        @Override
        public Mark createFromParcel(Parcel in) {
            return new Mark(in);
        }

        @Override
        public Mark[] newArray(int size) {
            return new Mark[size];
        }
    };

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getChatper() { return chatper; }
    public void setChatper(int chatper) { this.chatper = chatper; }

    public int getRepeat() { return repeat; }
    public void setRepeat(int repeat) { this.repeat = repeat; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getProblemCnt() { return problemCnt; }
    public void setProblemCnt(int problemCnt) { this.problemCnt = problemCnt; }

    public long getCreatedDate() { return createdDate; }
    public void setCreatedDate(long createdDate) { this.createdDate = createdDate; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(chatper);
        dest.writeInt(repeat);
        dest.writeInt(score);
        dest.writeInt(problemCnt);
        dest.writeLong(createdDate);
    }
}

package com.behemoth.repeat.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Mark implements Parcelable {

    private String id;
    private String title;
    private int chatper;
    private int repeat;
    private int score;
    private int problemCnt;
    private long createdDate;
    private int isUsingThumbNail;
    private String thumbnail;
    private String imageName;

    public Mark(){}

    public Mark(String id, String title, int chapter, int repeat, int score, int problemCnt,
                long createdDate, int isUsingThumbNail, String thumbnail, String imageName){
        this.id = id;
        this.title = title;
        this.chatper = chapter;
        this.repeat = repeat;
        this.score = score;
        this.problemCnt = problemCnt;
        this.createdDate = createdDate;
        this.isUsingThumbNail = isUsingThumbNail;
        this.thumbnail = thumbnail;
        this.imageName = imageName;
    }

    protected Mark(Parcel in) {
        id = in.readString();
        title = in.readString();
        chatper = in.readInt();
        repeat = in.readInt();
        score = in.readInt();
        problemCnt = in.readInt();
        createdDate = in.readLong();
        isUsingThumbNail = in.readInt();
        thumbnail = in.readString();
        imageName = in.readString();
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

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

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

    public int getIsUsingThumbNail() { return isUsingThumbNail; }
    public void setIsUsingThumbNail(int isUsingThumbNail) { this.isUsingThumbNail = isUsingThumbNail; }

    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeInt(chatper);
        dest.writeInt(repeat);
        dest.writeInt(score);
        dest.writeInt(problemCnt);
        dest.writeLong(createdDate);
        dest.writeInt(isUsingThumbNail);
        dest.writeString(thumbnail);
        dest.writeString(imageName);
    }
}

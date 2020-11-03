package com.behemoth.repeat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Chapter implements Parcelable {

    private int chapterNumber;
    private int problemCount;
    private int repeatCount;
    private List<Repeat> repeat;

    public Chapter(int i){
        this.chapterNumber = i;
        this.problemCount = 0;
        this.repeatCount = 1;
    }

    protected Chapter(Parcel in) {
        chapterNumber = in.readInt();
        problemCount = in.readInt();
        repeatCount = in.readInt();
        repeat = new ArrayList<>();
        in.readTypedList(repeat, Repeat.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(chapterNumber);
        dest.writeInt(problemCount);
        dest.writeInt(repeatCount);
        dest.writeList(repeat);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Chapter> CREATOR = new Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel in) {
            return new Chapter(in);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };

    public int getChapterNumber() {
        return chapterNumber;
    }
    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public int getProblemCount() {
        return problemCount;
    }
    public void setProblemCount(int problemCount) {
        this.problemCount = problemCount;
    }

    public int getRepeatCount() { return repeatCount; }
    public void setRepeatCount(int repeatCount) { this.repeatCount = repeatCount; }

    public List<Repeat> getRepeat() { return repeat; }
    public void setRepeat(List<Repeat> repeat) { this.repeat = repeat; }

}

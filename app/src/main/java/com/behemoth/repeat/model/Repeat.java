package com.behemoth.repeat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Repeat implements Parcelable {

    private int problemCount;
    /**
     * -1 > 미채점
     * 0 -> 오답
     * 1 -> 정답
     */
    private List<Integer> mark;
    private boolean finished;
    private int score;
    private int percent;
    private int repeatNumber;

    public Repeat(){}

    public Repeat(int problemCount, List<Integer> mark, boolean finished){
        this.problemCount = problemCount;
        this.mark = mark;
        this.finished = finished;
    }

    public Repeat(int cnt){
        problemCount = cnt;
        mark = new ArrayList<>();
        for(int i = 0; i < cnt; i++){
            mark.add(-1);
        }
        finished = false;
    }

    protected Repeat(Parcel in) {
        problemCount = in.readInt();
        mark = new ArrayList<>();
        in.readList(mark, ClassLoader.getSystemClassLoader());
        finished = in.readByte() != 0;
        percent = in.readInt();
        repeatNumber = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(problemCount);
        parcel.writeList(mark);
        parcel.writeByte((byte) (finished ? 1 : 0));
        parcel.writeInt(percent);
        parcel.writeInt(repeatNumber);
    }

    public static final Creator<Repeat> CREATOR = new Creator<Repeat>() {
        @Override
        public Repeat createFromParcel(Parcel in) {
            return new Repeat(in);
        }

        @Override
        public Repeat[] newArray(int size) {
            return new Repeat[size];
        }
    };

    public int getProblemCount() { return problemCount; }
    public void setProblemCount(int repeatCount) { this.problemCount = repeatCount; }

    public List<Integer> getMark() { return mark; }
    public void setMark(List<Integer> mark) { this.mark = mark; }

    public boolean isFinished() { return finished; }
    public void setFinished(boolean finished) { this.finished = finished; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getPercent() { return percent; }
    public void setPercent(int percent) { this.percent = percent; }

    public int getRepeatNumber() { return repeatNumber; }
    public void setRepeatNumber(int repeatNumber) { this.repeatNumber = repeatNumber; }

    @Override
    public int describeContents() {
        return 0;
    }

}

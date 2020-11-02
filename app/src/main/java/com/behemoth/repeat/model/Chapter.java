package com.behemoth.repeat.model;

public class Chapter {

    private int chapterNumber;
    private int problemCount;

    public Chapter(int i){
        this.chapterNumber = i;
        this.problemCount = 0;
    }

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

}

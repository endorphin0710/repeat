package com.behemoth.repeat.model;

public class Problem {

    private int problemNumber;
    private int state;

    public Problem(){}

    public Problem(int problemNumber, int state){
        this.problemNumber = problemNumber;
        this.state = state;
    }

    public int getProblemNumber() {
        return problemNumber;
    }
    public void setProblemNumber(int problemNumber) {
        this.problemNumber = problemNumber;
    }

    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }

}

package com.behemoth.repeat.model;

public class Problem implements Comparable<Problem>{

    private int problemNumber;
    /**
     * -1 -> unmarked
     * 0 -> wrong
     * 1 -> correct
     */
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

    @Override
    public int compareTo(Problem o) {
        return 0;
    }

}

package com.behemoth.repeat.model;

import java.util.ArrayList;
import java.util.List;

public class FAQ {

    private String question;
    private String answer;
    private List<FAQ> subAnswers;
    private boolean expanded;

    public FAQ(){}

    public FAQ(String q, String a){
        this.question = q;
        this.answer = a;
        this.subAnswers = new ArrayList<>();
        this.expanded = false;
    }

    public FAQ(String q, String a, ArrayList<FAQ> s){
        this.question = q;
        this.answer = a;
        this.subAnswers = s;
        this.expanded = false;
    }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public List<FAQ> getSubAnswers() { return subAnswers; }
    public void setSubAnswers(List<FAQ> subAnswers) { this.subAnswers = subAnswers; }

    public boolean isExpanded() { return expanded; }

    public void setExpanded(boolean expanded) { this.expanded = expanded; }
}

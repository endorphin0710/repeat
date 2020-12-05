package com.behemoth.repeat.mark.chapter;

public class MarkChapterModel implements MarkChapterContract.Model{

    private final MarkChapterContract.Presenter presenter;

    public MarkChapterModel(MarkChapterContract.Presenter p){
        this.presenter = p;
    }

}

package com.behemoth.repeat.stats.chapter;

public class ChapterStatsModel implements ChapterStatsContract.Model {

    private final ChapterStatsContract.Presenter presenter;

    public ChapterStatsModel(ChapterStatsContract.Presenter p) {
        this.presenter = p;
    }

}
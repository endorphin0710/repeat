package com.behemoth.repeat.stats.chapter;

import android.content.Context;

public class ChapterStatsPresenter implements ChapterStatsContract.Presenter{

    private final Context viewContext;
    private final ChapterStatsContract.View view;
    private final ChapterStatsContract.Model model;

    public ChapterStatsPresenter(ChapterStatsContract.View view){
        this.view = view;
        this.viewContext = view.getContext();
        this.model = new ChapterStatsModel(this);
    }


}

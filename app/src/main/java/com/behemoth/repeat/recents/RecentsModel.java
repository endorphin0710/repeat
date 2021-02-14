package com.behemoth.repeat.recents;

public class RecentsModel implements RecentsContract.Model{

    private final RecentsContract.Presenter presenter;

    public RecentsModel(RecentsContract.Presenter p){
        this.presenter = p;
    }


}

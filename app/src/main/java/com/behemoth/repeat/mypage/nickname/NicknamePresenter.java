package com.behemoth.repeat.mypage.nickname;

import android.content.Context;

public class NicknamePresenter implements NicknameContract.Presenter{

    private final Context viewContext;
    private final NicknameContract.View view;
    private final NicknameContract.Model model;

    public NicknamePresenter(NicknameContract.View view){
        this.view = view;
        this.viewContext = view.getContext();
        this.model = new NicknameModel(this);
    }

    @Override
    public void changeNickname(String s) {
        model.changeNickname(s);
    }

    @Override
    public void onSuccess() {
        view.onSuccess();
    }

    @Override
    public void onFailure() {
        view.onFailure();
    }
}

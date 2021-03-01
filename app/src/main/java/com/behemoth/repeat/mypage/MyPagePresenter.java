package com.behemoth.repeat.mypage;

import android.content.Context;

public class MyPagePresenter implements MyPageContract.Presenter{

    private final Context viewContext;
    private final MyPageContract.View view;
    private final MyPageContract.Model model;

    public MyPagePresenter(MyPageContract.View view){
        this.view = view;
        this.viewContext = view.getContext();
        this.model = new MyPageModel(this);
    }

    @Override
    public void getData() {
        model.getData();
    }

    @Override
    public void onRetrieveData(String nickName, String versionName) {
        view.onRetrieveData(nickName, versionName);
    }
}

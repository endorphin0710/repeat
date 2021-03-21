package com.behemoth.repeat.mypage;

import android.content.Context;

public interface MyPageContract {

    interface View{
        Context getContext();
        void onRetrieveData(String nickName, String versionName);
        void showProgressBar();
        void hideProgressBar();
        void onDeleteSuccess();
        void onDeleteFailure();
    }

    interface Presenter{
        void getData();
        void onRetrieveData(String nickName, String versionName);
        void initialize();
        void onDeleteSuccess();
        void onDeleteFailure();
    }

    interface Model{
        void getData();
        void initialize();
    }

}

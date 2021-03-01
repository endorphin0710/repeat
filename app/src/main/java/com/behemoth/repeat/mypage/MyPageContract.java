package com.behemoth.repeat.mypage;

import android.content.Context;

public interface MyPageContract {

    interface View{
        Context getContext();
        void onRetrieveData(String nickName, String versionName);
    }

    interface Presenter{
        void getData();
        void onRetrieveData(String nickName, String versionName);
    }

    interface Model{
        void getData();
    }

}

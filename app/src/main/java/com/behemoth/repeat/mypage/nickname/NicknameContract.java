package com.behemoth.repeat.mypage.nickname;

import android.content.Context;

public interface NicknameContract {

    interface View{
        Context getContext();
        void onSuccess();
        void onFailure();
    }

    interface Presenter{
        void changeNickname(String s);
        void onSuccess();
        void onFailure();
    }

    interface Model{
        void changeNickname(String s);
    }

}

package com.behemoth.repeat.mypage.nickname;

import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.SharedPreference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NicknameModel implements NicknameContract.Model {

    private final NicknameContract.Presenter presenter;

    public NicknameModel(NicknameContract.Presenter p) {
        this.presenter = p;
    }

    @Override
    public void changeNickname(String s) {
        String userId = SharedPreference.getInstance().getString(Constants.USER_ID, "");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("user").child(userId).child("nickName");

        ref.setValue(s).addOnSuccessListener(sl -> {
            presenter.onSuccess();
        }).addOnFailureListener(fl -> {
            presenter.onFailure();
        });
    }

}
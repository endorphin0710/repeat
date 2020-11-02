package com.behemoth.repeat.addBook.problem;

public class AddProblemPresenter implements AddProblemContract.Presenter{

    private static final String TAG = "AddProblemPresenter";

    private AddProblemContract.View view;

    public AddProblemPresenter(AddProblemContract.View view){
        this.view = view;
    }

}

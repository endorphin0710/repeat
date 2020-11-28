package com.behemoth.repeat.addBook.chapter;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.util.LogUtil;

public class AddChapterPresenter implements AddChapterContract.Presenter{

    private static final String TAG = "AddChapterPresenter";

    private final AddChapterContract.View view;

    public AddChapterPresenter(AddChapterContract.View view){
        this.view = view;
    }

    @Override
    public void validateInput(String chapter, Book newBook) {
        try{
            int i = Integer.parseInt(chapter);
            if(chapter.length() > 0){
                newBook.setChapterCount(i);
                view.nextStep(newBook);
            }else{
                view.showToast(view.getContext().getString(R.string.add_chapter_validate_fail));
            }
        }catch(NumberFormatException e){
            LogUtil.d(TAG, e.getMessage());
        }
    }
}

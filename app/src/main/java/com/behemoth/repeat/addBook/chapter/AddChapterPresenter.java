package com.behemoth.repeat.addBook.chapter;

import android.util.Log;

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
            if(chapter.length() > 0){
                int i = Integer.parseInt(chapter);
                if(i <= 0) view.showToast(view.getContext().getString(R.string.add_chapter_validate_fail_number_format));
                else if(i > 50) view.showToast(view.getContext().getString(R.string.add_chapter_validate_fail_number_exceed));
                else{
                    newBook.setChapterCount(i);
                    view.nextStep(newBook);
                }
            }else{
                view.showToast(view.getContext().getString(R.string.add_chapter_validate_fail_empty));
            }
        }catch(NumberFormatException e){
            view.showToast(view.getContext().getString(R.string.add_chapter_validate_fail_number_format));
            LogUtil.d(TAG, e.getMessage());
        }
    }
}

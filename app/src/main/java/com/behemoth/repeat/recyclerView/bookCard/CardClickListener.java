package com.behemoth.repeat.recyclerView.bookCard;

import com.behemoth.repeat.model.Book;

public interface CardClickListener {
    void onClick(int position);
    void onMenuClick(int position, Book book);
}

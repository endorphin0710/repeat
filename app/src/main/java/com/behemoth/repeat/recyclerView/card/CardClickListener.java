package com.behemoth.repeat.recyclerView.card;

import com.behemoth.repeat.model.Book;

public interface CardClickListener {
    void onClick(int position);
    void onMenuClick(int position, Book book);
}

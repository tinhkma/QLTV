package com.example.qltv.ui;

import android.widget.ImageView;

import com.example.qltv.model.BookModel;

public interface BookItemClickListener {

    void onBookClick(BookModel books, ImageView imgBook);

}

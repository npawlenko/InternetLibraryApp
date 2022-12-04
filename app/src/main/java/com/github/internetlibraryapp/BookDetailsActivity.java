package com.github.internetlibraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.github.internetlibraryapp.entity.Book;
import com.github.internetlibraryapp.service.BookService;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailsActivity extends AppCompatActivity {

    private Book book;

    private TextView bookTitleTextView;
    private TextView bookSubtitleTextView;
    private TextView bookWeightTextView;
    private TextView bookPublishersTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        bookTitleTextView = findViewById(R.id.book_details_title);
        bookSubtitleTextView = findViewById(R.id.book_details_subtitle);
        bookWeightTextView = findViewById(R.id.book_details_weight);
        bookPublishersTextView = findViewById(R.id.book_details_publishers);
;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String isbn = extras.getString(MainActivity.EXTRA_BOOK_ISBN);
            fetchBookData(isbn);
        }
    }


    private void fetchBookData(String isbn) {
        BookService bookService = RetrofitInstance.getRetrofitInstance().create(BookService.class);

        Call<Book> booksApiCall = bookService.findBook(isbn);

        booksApiCall.enqueue(new Callback<Book>() {

            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if(response.body() != null) {
                    setupBookView(response.body());
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Snackbar.make(findViewById(R.id.main_view), "Something went wrong... Please try again later!",
                        BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });
    }

    private void setupBookView(Book book) {
        this.book = book;
        bookTitleTextView.setText(book.getTitle());
        bookSubtitleTextView.setText(book.getSubtitle());
        bookWeightTextView.setText(book.getWeight());
        bookPublishersTextView.setText(TextUtils.join(", ", book.getPublishers()));
    }
}